package org.asc.koth.commands;

import org.asc.koth.KOTHPlugin;
import org.asc.koth.utilities.MsgUtility;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AdminCommands implements CommandExecutor, TabCompleter {
    private final KOTHPlugin plugin;

    public AdminCommands(KOTHPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length >= 1) {
            switch (args[0].toLowerCase()) {
                case "forcestart":
                    if (!this.plugin.getKOTHManager().isKothActive()) {
                        this.plugin.getKOTHManager().startKOTH();
                        MsgUtility.send(sender, "<color:#33ff78><white>KOTH has been started forcibly.</white>");
                    } else {
                        MsgUtility.send(sender, "<color:#ff3333><white>KOTH is already active!</white>");
                    }
                    return true;
                case "forceend":
                    if (this.plugin.getKOTHManager().isKothActive()) {
                        this.plugin.getKOTHManager().endKOTH();
                        MsgUtility.send(sender, "<color:#33ff78><white>KOTH has been ended forcibly.</white>");
                    } else {
                        MsgUtility.send(sender, "<color:#ff3333><white>KOTH is already not active!</white>");
                    }
                    return true;
                case "reload":
                    this.plugin.reloadConfig();
                    this.plugin.saveDefaultConfig();
                    this.plugin.getConfig().options().copyDefaults(true);
                    this.plugin.saveConfig();
                    MsgUtility.send(sender, "<color:#33ff78><white>KOTH's config has been reloaded.</white>");
                    return true;
            }
            MsgUtility.send(sender, "<color:#ff3333><white>Usage: <color:#ff3333>/kothadmin <forcestart/forceend/reload></color></white>");
        } else {
            MsgUtility.send(sender, "<color:#ff3333><white>Usage: <color:#ff3333>/kothadmin <forcestart/forceend/reload></color></white>");
        }
        return true;
    }

    @Nullable
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> arguments = new ArrayList<>();
        if (args.length == 1) {
            arguments.add("forcestart");
            arguments.add("forceend");
            arguments.add("reload");
        }
        return arguments;
    }
}
