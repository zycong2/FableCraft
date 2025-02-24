package org.zycong.fableCraft.mobs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.zycong.fableCraft.FableCraft;
import org.zycong.fableCraft.playerStats.stats;
import org.zycong.fableCraft.yamlManager;
import org.zycong.fableCraft.lootTables.lootTable;

import java.util.List;
import java.util.Random;

import static org.zycong.fableCraft.FableCraft.customMobs;
import static org.zycong.fableCraft.yamlManager.getConfig;
import static org.zycong.fableCraft.yamlManager.mobDB;

public class mobListeners implements Listener {
    @EventHandler
    void damage(EntityDamageEvent event){
        if (stats.getEntityPDC("type", event.getEntity()) != null){
            event.getEntity().setCustomName(yamlManager.setPlaceholders((String)mobDB.get(stats.getEntityPDC("type", event.getEntity()) + ".customName.name"), true, event.getEntity()));
        }
    }
    @EventHandler
    void onDeath(EntityDeathEvent event){
        if (stats.getEntityPDC("lootTable", event.getEntity()) != null){
            event.getDrops().clear();
            event.getDrops().addAll(lootTable.getLootTable(stats.getEntityPDC("lootTable", event.getEntity())));
        }
        if (customMobs.contains(event.getEntity())) {customMobs.remove(event.getEntity()); }
    }
    @EventHandler
    void onSpawn(CreatureSpawnEvent event){
        if(event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
            if ((Boolean) getConfig("mobs.removeAllVanillaSpawning", null, true)) {
                event.setCancelled(true);
            }
            randomSpawn(event);
        }
    }
    public void randomSpawn(CreatureSpawnEvent event){
        int randomInt = new Random().nextInt(100) + 1;
        try {
            boolean spawned = false;
            boolean conditions = false;
            if (mobDB.get(FableCraft.spawns.get(randomInt) + ".randomSpawns.options.spawnOn") != null) {
                for (String s : (List<String>) mobDB.get(FableCraft.spawns.get(randomInt) + ".randomSpawns.options.spawnOn")) {
                    if (event.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.valueOf(s))) {
                        mobsHelper.getEntity(FableCraft.spawns.get(randomInt), event.getLocation());
                        spawned = true;
                        conditions = true;
                        break;
                    }
                }
            }
            if (mobDB.get(FableCraft.spawns.get(randomInt) + ".randomSpawns.options.biomes") != null) {
                for (String s : (List<String>)mobDB.get(FableCraft.spawns.get(randomInt) + ".randomSpawns.options.biomes")){
                    if (event.getLocation().getWorld().getBiome(event.getLocation()).equals(Biome.valueOf(s))){
                        mobsHelper.getEntity(FableCraft.spawns.get(randomInt), event.getLocation());
                        spawned = true;
                        conditions = true;
                        break;
                    }
                }
            }

            if (!conditions){
                mobsHelper.getEntity(FableCraft.spawns.get(randomInt), event.getLocation());
            } else if (!spawned){
                randomSpawn(event);
            }

            event.setCancelled(true);
        } catch (IndexOutOfBoundsException ignored) { }
    }
}