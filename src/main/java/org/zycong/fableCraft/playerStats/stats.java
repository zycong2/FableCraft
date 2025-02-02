
package org.zycong.fableCraft.playerStats;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.zycong.fableCraft.FableCraft;

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
        return (String)container.get(key, PersistentDataType.STRING);
    }

    public static void setItemPDC(String keyString, ItemStack i, String data) {
        NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), keyString);
        i.getItemMeta().getPersistentDataContainer().set(key, PersistentDataType.STRING, data);
    }

    public static String getItemPDC(String keyString, ItemStack i) {
        NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), keyString);
        PersistentDataContainer container = i.getItemMeta().getPersistentDataContainer();
        return (String)container.get(key, PersistentDataType.STRING);
    }
}
