package org.zycong.fableCraft.playerStats;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.zycong.fableCraft.yamlManager;

public class resetStats implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender p, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!p.hasPermission("FableCraft.itemDB")) {
            p.sendMessage((String)yamlManager.getConfig("messages.error.noPermission", (Player) p, true));
            return true;
        }if (args.length == 0 || Bukkit.getPlayer(args[0]) == null){
            p.sendMessage(yamlManager.getConfig("messages.error.noValidArgument", null, true).toString());
            return true;
        }

        String[] skills = yamlManager.getConfigNodes("stats").toArray(new String[0]);
        for(String skill : skills) {
            stats.setPlayerPDC(skill, Bukkit.getPlayer(args[0]), String.valueOf(yamlManager.getConfig("stats." + skill + ".default", Bukkit.getPlayer(args[0]), true)));
        }
        stats.checkCurrentStats(Bukkit.getPlayer(args[0]));
        p.sendMessage((String)yamlManager.getConfig("messages.info.resetSuccess", Bukkit.getPlayer(args[0]), true));
        return true;
    }
}