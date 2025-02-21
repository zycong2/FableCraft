package org.zycong.fableCraft.lootTables;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.zycong.fableCraft.yamlManager;

import java.util.List;
import java.util.Random;

public class lootTable {
    public Inventory getLootTable(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27);

        return inv;
    }


    public static List<ItemStack> getLootTable(String lootTable){
        List<ItemStack> items = new java.util.ArrayList<>(List.of());
        int maxItems = (int)yamlManager.getLootTableData(lootTable + ".maxItems");
        int minItems = (int) yamlManager.getLootTableData(lootTable + ".minItems");
        int itemCount = new Random().nextInt(maxItems - minItems + 1) + minItems;

        List<String> itemList = (List<String>) yamlManager.getLootTableData(lootTable + ".items");
        for (String s : itemList){
            String[] values = s.split(":");
            if (Material.getMaterial(values[0]) != null){
                for (int i = 0; i < Integer.valueOf(values[3]); i++) { items.add(ItemStack.of(Material.getMaterial(values[0]))); }
            } else {
                if (yamlManager.getItemNodes("").contains(values[0])){
                    for (int i = 0; i < Integer.valueOf(values[3]); i++) { items.add(yamlManager.getItem(values[0])); }
                } else{
                    Bukkit.getLogger().severe("Material " + values[0] + " could not be found!");
                }
            }
        }
        List<ItemStack> finalItems = new java.util.ArrayList<>(List.of());
        if (!items.isEmpty()) {
            for (int i = 0; i < itemCount; i++) {
                int ItemId = new Random().nextInt(items.size());
                finalItems.add(items.get(ItemId));
            }
        }
        return finalItems;
    }
}
