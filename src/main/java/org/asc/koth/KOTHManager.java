package org.asc.koth;

import org.asc.koth.utilities.MsgUtility;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitScheduler;

public class KOTHManager {
    private static final DateTimeFormatter LAST_KOTH_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private final KOTHPlugin plugin;

    private final File stateFile;

    private LocalDateTime lastKoth;

    private boolean kothActive;

    private LocalDateTime capperTime;

    private Player kothCapper;

    public KOTHManager(KOTHPlugin plugin) {
        this.plugin = plugin;
        this.stateFile = new File(plugin.getDataFolder(), "koth-state.yml");
        this.lastKoth = loadLastKoth();
        saveLastKoth();
        this.kothActive = false;
        this.capperTime = null;
        this.kothCapper = null;
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.runTaskTimer(plugin, () -> {
            if (this.kothActive) {
                if (this.kothCapper == null) {
                    for (Player player : plugin.getServer().getOnlinePlayers()) {
                        if (plugin.getWorldGuardManager().isInRegion(player, plugin.getConfig().getString("cap-region"))) {
                            setCapper(player);
                            break;
                        }
                    }
                } else if (this.kothCapper.isOnline() && !this.kothCapper.isDead() && plugin.getWorldGuardManager().isInRegion(this.kothCapper, plugin.getConfig().getString("cap-region"))) {
                    long capperTimeDifference = Duration.between(this.capperTime, LocalDateTime.now()).toMinutes();
                    if (capperTimeDifference >= plugin.getConfig().getInt("cap-time"))
                        endKOTH();
                } else {
                    resetCapper();
                }
            } else {
                long lastKOTHDifference = Duration.between(this.lastKoth, LocalDateTime.now()).toHours();
                if (lastKOTHDifference >= plugin.getConfig().getInt("frequency"))
                    startKOTH();
            }
        }, plugin.getConfig().getLong("tick-rate"), plugin.getConfig().getLong("tick-rate"));
        plugin.getLogger().log(Level.INFO, "KOTH initialized!");
    }

    public void startKOTH() {
        this.kothActive = true;
        MsgUtility.broadcast(this.plugin.getServer(), "<newline><color:#ff3333><bold>KOTH</bold></color><newline><gray><dark_gray>» <white>KOTH has been started!</white></gray><newline> ");
        this.plugin.getLogger().log(Level.INFO, "KOTH has been started.");
    }

    public void endKOTH() {
        this.lastKoth = LocalDateTime.now();
        saveLastKoth();
        this.kothActive = false;
        if (this.kothCapper != null) {
            MsgUtility.broadcast(this.plugin.getServer(), "<newline><color:#ff3333><bold>KOTH</bold></color><newline><gray><dark_gray>» <white><color:#ff3333>" + this.kothCapper.getName() + "</color> is the winner of KOTH!</white></gray><newline>");
            this.plugin.getLogger().log(Level.INFO, "KOTH was won by " + this.kothCapper.getName() + ".");
            String rewardCommand = this.plugin.getConfig().getString("reward-command");
            if (rewardCommand != null)
                this.plugin.getServer().dispatchCommand(this.plugin.getServer().getConsoleSender(), rewardCommand.replace("{player}", this.kothCapper.getName()));
        } else {
            MsgUtility.broadcast(this.plugin.getServer(), "<newline><color:#ff3333><bold>KOTH</bold></color><newline><gray><white>KOTH has been ended with no winner.</white></gray><newline>");
            this.plugin.getLogger().log(Level.INFO, "KOTH has been ended with no winner.");
        }
        this.capperTime = null;
        this.kothCapper = null;
    }

    public void setCapper(Player player) {
        this.capperTime = LocalDateTime.now();
        this.kothCapper = player;
    }

    public void resetCapper() {
        this.capperTime = null;
        this.kothCapper = null;
    }

    public void saveLastKoth() {
        if (!this.plugin.getDataFolder().exists() && !this.plugin.getDataFolder().mkdirs()) {
            this.plugin.getLogger().log(Level.WARNING, "Unable to create KOTH data folder.");
            return;
        }

        FileConfiguration state = new YamlConfiguration();
        state.set("last-koth", LAST_KOTH_FORMATTER.format(this.lastKoth));

        try {
            state.save(this.stateFile);
        } catch (IOException exception) {
            this.plugin.getLogger().log(Level.WARNING, "Unable to save KOTH state.", exception);
        }
    }

    private LocalDateTime loadLastKoth() {
        if (!this.stateFile.exists())
            return LocalDateTime.now();

        FileConfiguration state = YamlConfiguration.loadConfiguration(this.stateFile);
        String storedLastKoth = state.getString("last-koth");
        if (storedLastKoth == null || storedLastKoth.isBlank())
            return LocalDateTime.now();

        try {
            return LocalDateTime.parse(storedLastKoth, LAST_KOTH_FORMATTER);
        } catch (DateTimeParseException exception) {
            this.plugin.getLogger().log(Level.WARNING, "Invalid last-koth value in KOTH state file. Resetting timer.", exception);
            return LocalDateTime.now();
        }
    }

    public LocalDateTime getLastKoth() {
        return this.lastKoth;
    }

    public boolean isKothActive() {
        return this.kothActive;
    }

    public LocalDateTime getCapperTime() {
        return this.capperTime;
    }

    public Player getKothCapper() {
        return this.kothCapper;
    }
}
