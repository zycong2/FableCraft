package org.zycong.fableCraft.playerStats;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class resetStatsTC implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<Player> players = (List<Player>) Bukkit.getOnlinePlayers();
        List<String> args = new java.util.ArrayList<>(List.of());
        for (Player p : players){
            args.add(p.getName());
        }
        return args;
    }
}
