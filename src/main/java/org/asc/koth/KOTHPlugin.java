package org.asc.koth;

import org.asc.koth.commands.CommandManager;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

public class KOTHPlugin extends JavaPlugin {
    private WorldGuardManager worldGuardManager;

    private KOTHManager kothManager;

    private CommandManager commandManager;

    public void onEnable() {
        saveDefaultConfig();
        this.worldGuardManager = new WorldGuardManager(this);
        this.kothManager = new KOTHManager(this);
        this.commandManager = new CommandManager(this);
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
            (new PlaceholderModule(this)).register();
        getLogger().log(Level.INFO, "Plugin enabled!");
    }

    public void onDisable() {
        if (this.kothManager != null)
            this.kothManager.saveLastKoth();
        getLogger().log(Level.INFO, "Plugin disabled!");
    }

    public WorldGuardManager getWorldGuardManager() {
        return this.worldGuardManager;
    }

    public KOTHManager getKOTHManager() {
        return this.kothManager;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }
}
