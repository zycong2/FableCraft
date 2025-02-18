package org.zycong.fableCraft.mobs;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.zycong.fableCraft.FableCraft;
import org.zycong.fableCraft.playerStats.stats;
import org.zycong.fableCraft.yamlManager;

import static org.zycong.fableCraft.yamlManager.mobDB;

public class mobListeners implements Listener {
    @EventHandler
    void damage(EntityDamageEvent event){
        if (stats.getEntityPDC("type", event.getEntity()) != null){
            event.getEntity().setCustomName(yamlManager.setPlaceholders((String)mobDB.get(stats.getEntityPDC("type", event.getEntity()) + ".customName.name"), true, event.getEntity()));
        }
    }
}
