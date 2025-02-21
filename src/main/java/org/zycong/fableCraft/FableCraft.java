package org.zycong.fableCraft;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zycong.fableCraft.buildHelpers.buildHelper;
import org.zycong.fableCraft.buildHelpers.buildHelperTC;
import org.zycong.fableCraft.buildHelpers.buildListeners;
import org.zycong.fableCraft.commands.itemDBCommand;
import org.zycong.fableCraft.mobs.mobListeners;
import org.zycong.fableCraft.mobs.mobsCommand;
import org.zycong.fableCraft.mobs.mobsCommandTC;
import org.zycong.fableCraft.playerStats.resetStats;
import org.zycong.fableCraft.playerStats.resetStatsTC;
import org.zycong.fableCraft.playerStats.stats;

public final class FableCraft extends JavaPlugin {
    public static List<String> itemStats = List.of("Damage", "Health", "Mana", "Defence");
    public FableCraft() {
    }

    public static Plugin getPlugin() { return Bukkit.getServer().getPluginManager().getPlugin("FableCraft"); }

    public void onEnable() {

        this.getCommand("itemDB").setExecutor(new itemDBCommand());
        this.getCommand("resetStats").setExecutor(new resetStats());
        this.getCommand("resetStats").setTabCompleter(new resetStatsTC());
        this.getCommand("buildHelper").setExecutor(new buildHelper());
        this.getCommand("buildHelper").setTabCompleter(new buildHelperTC());
        this.getCommand("mobs").setExecutor(new mobsCommand());
        this.getCommand("mobs").setTabCompleter(new mobsCommandTC());

        Bukkit.getPluginManager().registerEvents(new listeners(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new buildListeners(), getPlugin());
        Bukkit.getPluginManager().registerEvents(new mobListeners(), getPlugin());

        BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendActionBar(String.valueOf(yamlManager.getConfig("actionbar.message", p, true)));
                double maxPlayerHealth = Double.parseDouble(stats.getPlayerPDC("Health", p));
                double maxPlayerMana = Double.parseDouble(stats.getPlayerPDC("Mana", p));
                double currentHealth = p.getMetadata("currentHealth").getFirst().asDouble();
                double currentMana = p.getMetadata("currentMana").getFirst().asDouble();
                if (currentHealth < maxPlayerHealth) {
                    double amount = Double.parseDouble(stats.getPlayerPDC("Regeneration", p));
                    currentHealth += (double)20.0F / maxPlayerHealth * amount;
                    p.setMetadata("currentHealth", new FixedMetadataValue(getPlugin(), currentHealth));
                    p.setHealth((double)20.0F / maxPlayerHealth * currentHealth);
                } else if (currentHealth > maxPlayerHealth) { p.setMetadata("currentHealth", new FixedMetadataValue(getPlugin(), maxPlayerHealth)); }
                if (currentMana < maxPlayerMana) {
                    double amount = Double.parseDouble(stats.getPlayerPDC("ManaRegeneration", p));
                    currentMana += (double)20.0F / maxPlayerMana * amount;
                    p.setMetadata("currentMana", new FixedMetadataValue(getPlugin(), currentMana));
                } else if (currentMana > maxPlayerMana) { p.setMetadata("currentMana", new FixedMetadataValue(getPlugin(), maxPlayerMana)); }
            }

        }, 20L, 20L);
        if (!yamlManager.loadData()) {
            Bukkit.getLogger().severe("Failed to load data!");
        }

        if (yamlManager.getConfig("items.removeDefaultRecipes", null, false).equals(true)) {Bukkit.clearRecipes();} else {Bukkit.resetRecipes();}
        yamlManager.getCustomItems();

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
