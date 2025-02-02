package org.zycong.fableCraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.zycong.fableCraft.listeners;
import org.zycong.fableCraft.yamlManager;

public class itemDBCommand implements CommandExecutor {
    public itemDBCommand() {
    }

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player p = (Player)commandSender;
        if (!p.hasPermission("FableCraft.itemDB")) {
            p.sendMessage((String)yamlManager.getConfig("messages.error.noPermission", p, true));
            return true;
        } else {
            listeners.itemDBMenu(p);
            p.openInventory(listeners.itemDB);
            return true;
        }
    }
}
