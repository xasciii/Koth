package org.asc.koth;

import org.asc.koth.utilities.TimeUtility;
import java.time.Duration;
import java.time.LocalDateTime;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderModule extends PlaceholderExpansion {
    private final KOTHPlugin plugin;

    public PlaceholderModule(KOTHPlugin plugin) {
        this.plugin = plugin;
    }

    @NotNull
    public String getIdentifier() {
        return this.plugin.getPluginMeta().getName();
    }

    @NotNull
    public String getAuthor() {
        return this.plugin.getPluginMeta().getAuthors().get(0);
    }

    @NotNull
    public String getVersion() {
        return this.plugin.getPluginMeta().getVersion();
    }

    public boolean persist() {
        return true;
    }

    public String onRequest(OfflinePlayer player, String params) {
        switch (params) {
            case "countdown": {
                long frequency = this.plugin.getConfig().getLong("frequency");
                long countdown = frequency * 60L * 60L;
                if (!this.plugin.getKOTHManager().isKothActive())
                    countdown = frequency * 60L * 60L - Duration.between(this.plugin.getKOTHManager().getLastKoth(), LocalDateTime.now()).toSeconds();
                return TimeUtility.formatSeconds(countdown);
            }
            case "isactive":
                return String.valueOf(this.plugin.getKOTHManager().isKothActive());
            case "end": {
                long capTime = this.plugin.getConfig().getLong("cap-time");
                long kothEnd = capTime * 60L;
                if (this.plugin.getKOTHManager().getKothCapper() != null)
                    kothEnd = capTime * 60L - Duration.between(this.plugin.getKOTHManager().getCapperTime(), LocalDateTime.now()).toSeconds();
                return TimeUtility.formatSeconds(kothEnd);
            }
            case "capper": {
                Player kothCapper = this.plugin.getKOTHManager().getKothCapper();
                return kothCapper != null ? kothCapper.getName() : "None";
            }
            case "hologram": {
                if (this.plugin.getKOTHManager().isKothActive()) {
                    Player capper = this.plugin.getKOTHManager().getKothCapper();
                    String capperName = capper != null ? capper.getName() : "None";
                    return "<white>Capper</white> <color:#ff3333>" + capperName + "</color>";
                }
                long frequency = this.plugin.getConfig().getLong("frequency");
                long countdown = frequency * 60L * 60L - Duration.between(this.plugin.getKOTHManager().getLastKoth(), LocalDateTime.now()).toSeconds();
                return "<white>Starting in</white> <color:#ff3333>" + TimeUtility.formatSeconds(countdown) + "</color>";
            }
        }
        return null;
    }
}
