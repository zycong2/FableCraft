
package org.zycong.fableCraft;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.zycong.fableCraft.commands.itemDBCommand;
import org.zycong.fableCraft.playerStats.stats;

public final class FableCraft extends JavaPlugin {
    public FableCraft() {
    }

    public static Plugin getPlugin() {
        Plugin p = Bukkit.getServer().getPluginManager().getPlugin("FableCraft");
        return p;
    }

    public void onEnable() {
        this.getCommand("itemDB").setExecutor(new itemDBCommand());
        Bukkit.getPluginManager().registerEvents(new listeners(), getPlugin());
        BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendActionBar(String.valueOf(yamlManager.getConfig("actionbar.message", p, true)));
                double maxPlayerHealth = Double.parseDouble(stats.getPlayerPDC("Health", p));
                double currentHealth = ((MetadataValue)p.getMetadata("currentHealth").getFirst()).asDouble();
                if (currentHealth < maxPlayerHealth) {
                    double amount = Double.parseDouble(stats.getPlayerPDC("Regeneration", p));
                    currentHealth += (double)20.0F / maxPlayerHealth * amount;
                    p.setMetadata("currentHealth", new FixedMetadataValue(getPlugin(), currentHealth));
                    p.setHealth((double)20.0F / maxPlayerHealth * currentHealth);
                }
            }

        }, 20L, 20L);
        if (!yamlManager.loadData()) {
            Bukkit.getLogger().severe("Failed to load data!");
        }

        if (yamlManager.getConfig("items.removeDefaultRecipes", (Player)null, false).equals(true)) {
            Bukkit.clearRecipes();
        } else {
            Bukkit.resetRecipes();
        }

        List<ItemStack> items = yamlManager.getCustomItems();
    }

    public void onDisable() {
        if (!yamlManager.saveData()) {
            Bukkit.getLogger().severe("Failed to save data!");
        }

    }

    public static ItemStack createGuiItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createGuiHead(Player p, String name, String... lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        SkullMeta skullMeta = (SkullMeta)item.getItemMeta();
        skullMeta.setPlayerProfile(p.getPlayerProfile());
        item.setItemMeta(skullMeta);
        return item;
    }
}
