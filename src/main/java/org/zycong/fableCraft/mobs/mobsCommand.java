package org.zycong.fableCraft.mobs;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.zycong.fableCraft.FableCraft;
import org.zycong.fableCraft.playerStats.stats;
import org.zycong.fableCraft.yamlManager;

import java.util.List;

import static org.zycong.fableCraft.yamlManager.*;

public class mobsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player p = (Player) commandSender;
        if (!p.hasPermission("FableCraft.mobs")) {
            p.sendMessage((String) yamlManager.getConfig("messages.error.noPermission", p, true));
            return true;
        }if (args.length == 0){
            p.sendMessage(yamlManager.getConfig("messages.error.noValidArgument", null, true).toString());
            return true;
        }
        if (args[0].equals("spawn")) { mobsHelper.getEntity(args[1], p.getLocation()); }
        if (args[0].equals("killAll")){
            for (LivingEntity LE : FableCraft.customMobs){
                LE.setHealth(0);
            }
            FableCraft.customMobs.clear();
        }
        if (args[0].equals("reload")) { mobsHelper.reloadSpawns(); }
        return true;
    }
}