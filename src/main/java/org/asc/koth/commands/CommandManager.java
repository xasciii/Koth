package org.asc.koth.commands;

import org.asc.koth.KOTHPlugin;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

public class CommandManager {
    private final KOTHPlugin plugin;

    private final HashSet<Command> pluginCommands;

    public CommandManager(KOTHPlugin plugin) {
        this.plugin = plugin;
        this.pluginCommands = new HashSet<>();
        register("kothadmin", "Manage KOTH.", new ArrayList<>(), "/kothadmin <forcestart/forceend/reload>", "koth.command.kothadmin", new AdminCommands(plugin), new AdminCommands(plugin));
        plugin.getLogger().log(Level.INFO, "Commands registered!");
    }

    public HashSet<Command> getPluginCommands() {
        return this.pluginCommands;
    }

    public void register(String commandName, String description, List<String> aliases, String usage, String permission, CommandExecutor executor, TabCompleter tabCompleter) {
        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(new Class[] { String.class, Plugin.class });
            constructor.setAccessible(true);
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            CommandMap commandMap = (CommandMap)commandMapField.get(Bukkit.getServer());
            PluginCommand command = constructor.newInstance(new Object[] { commandName, this.plugin });
            commandMap.register(this.plugin.getName(), (Command)command);
            this.plugin.getCommand(commandName).setDescription(description);
            this.plugin.getCommand(commandName).setUsage(usage);
            this.plugin.getCommand(commandName).setPermission(permission);
            this.plugin.getCommand(commandName).setExecutor(executor);
            this.plugin.getCommand(commandName).setTabCompleter(tabCompleter);
            this.pluginCommands.add(this.plugin.getCommand(commandName));
            for (String aliasName : aliases) {
                PluginCommand alias = constructor.newInstance(new Object[] { aliasName, this.plugin });
                commandMap.register(this.plugin.getName(), (Command)alias);
                this.plugin.getCommand(aliasName).setDescription(description);
                this.plugin.getCommand(aliasName).setUsage(usage);
                this.plugin.getCommand(aliasName).setPermission(permission);
                this.plugin.getCommand(aliasName).setExecutor(executor);
                this.plugin.getCommand(aliasName).setTabCompleter(tabCompleter);
                this.pluginCommands.add(this.plugin.getCommand(commandName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
