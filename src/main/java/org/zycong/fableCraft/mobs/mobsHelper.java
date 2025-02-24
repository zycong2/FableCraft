package org.zycong.fableCraft.mobs;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.zycong.fableCraft.FableCraft;
import org.zycong.fableCraft.playerStats.stats;

import java.util.List;

import static org.zycong.fableCraft.yamlManager.*;

public class mobsHelper {
    public static LivingEntity getEntity(String name, Location p){
        EntityType entityType = EntityType.valueOf((String) mobDB.get(name + ".type"));
        if (!entityType.isSpawnable()) {
            String var42 = String.valueOf(mobDB.get(name + ".itemType"));
            Bukkit.getLogger().severe("Could not find entity type " + var42 + " " + name);
            return null;
        } else {
            Entity entity=p.getWorld().spawnEntity(p, entityType);


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

            if (mobDB.get(name + ".lootTable") != null) { stats.setEntityPDC("lootTable", LE, (String) mobDB.get(name + ".lootTable")); }



            stats.setEntityPDC("type", LE, name);
            FableCraft.customMobs.add(LE);
            return LE;
        }
    }
    public static void reloadSpawns(){
        List<String> mobs = getMobNodes("");
        for (String s : mobs){
            if(mobDB.get(s + ".randomSpawns.frequency") != null){
                for (int i = 0; i < (int)mobDB.get(s + ".randomSpawns.frequency") * 100; i++) { FableCraft.spawns.add(s); }
            }
        }
    }
}
