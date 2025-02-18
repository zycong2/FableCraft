package org.zycong.fableCraft.mobs;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
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

import static org.zycong.fableCraft.yamlManager.mobDB;
import static org.zycong.fableCraft.yamlManager.setPlaceholders;

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

        if (args[0].equals("spawn")) { getEntity(args[1], p); }




        return true;
    }

    public LivingEntity getEntity(String name, Player p){
        EntityType entityType = EntityType.valueOf((String) mobDB.get(name + ".type"));
        if (!entityType.isSpawnable()) {
            String var42 = String.valueOf(mobDB.get(name + ".itemType"));
            Bukkit.getLogger().severe("Could not spawn entity type " + var42 + " " + name);
            return null;
        } else {
            Entity entity=p.getWorld().spawnEntity(p.getLocation(), entityType);


            if (mobDB.get(name + ".glowing") != null) { entity.setGlowing((Boolean) mobDB.get(name + ".glowing")); }
            if (mobDB.get(name + ".invulnerable") != null) { entity.setInvulnerable((boolean) mobDB.get(name + ".invulnerable")); }
            LivingEntity LE = (LivingEntity) entity;

            if (mobDB.get(name + ".health") != null) {
                LE.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(Double.valueOf((int) mobDB.get(name + ".health")));
                LE.setHealth(Double.valueOf((int) mobDB.get(name + ".health")));
            }
            if (mobDB.get(name + ".damage") != null) { LE.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(Double.valueOf((int) mobDB.get(name + ".damage")));}
            if (mobDB.get(name + ".speed") != null) { LE.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(Double.valueOf((int) mobDB.get(name + ".speed")));}


            if (mobDB.get(name + ".customName.name") != null) { entity.customName(Component.text(setPlaceholders((String) mobDB.get(name + ".customName.name"), true, entity))); }
            if (mobDB.get(name + ".customName.visible").equals(true)) { entity.setCustomNameVisible(true); }
            else { entity.setCustomNameVisible(false); }

            stats.setEntityPDC("type", LE, name);
            return LE;
        }
    }
}