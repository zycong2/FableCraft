package org.zycong.fableCraft.playerStats;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zycong.fableCraft.FableCraft;
import org.zycong.fableCraft.yamlManager;

public class stats {
    public stats() {
    }

    public static void setPlayerPDC(String keyString, Player p, String data) {
        NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), keyString);
        p.getPersistentDataContainer().set(key, PersistentDataType.STRING, data);
    }

    public static String getPlayerPDC(String keyString, Player p) {
        NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), keyString);
        PersistentDataContainer container = p.getPersistentDataContainer();
        return container.get(key, PersistentDataType.STRING);
    }


    public static ItemStack setItemPDC(String keyString, ItemStack i, Object data) {
        NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), keyString);
        ItemMeta meta = i.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, data.toString());
        i.setItemMeta(meta);
        return i;
    }

    public static String getItemPDC(String keyString, ItemStack i) {
        NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), keyString);
        PersistentDataContainer container = i.getItemMeta().getPersistentDataContainer();
        return container.get(key, PersistentDataType.STRING);
    }



    public static void checkCurrentStats(Player p){
        if (p.getMetadata("currentHealth").getFirst().asDouble() > Double.parseDouble(getPlayerPDC("Health", p))){
            p.setMetadata("currentHealth", new FixedMetadataValue(FableCraft.getPlugin(), Double.parseDouble(getPlayerPDC("Health", p))));
        } if (p.getMetadata("currentMana").getFirst().asDouble() > Double.parseDouble(getPlayerPDC("Mana", p))){
            p.setMetadata("currentMana", new FixedMetadataValue(FableCraft.getPlugin(), Double.parseDouble(getPlayerPDC("Mana", p))));
        }
    }
}
