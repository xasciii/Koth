package org.asc.koth.utilities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

public class MsgUtility {
    public static Component format(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(format(message));
    }

    public static void broadcast(Server server, String message) {
        server.getOnlinePlayers().forEach(player -> send(player, message));
    }
}
