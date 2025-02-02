package org.zycong.fableCraft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.persistence.PersistentDataType;
import org.zycong.fableCraft.playerStats.stats;

public class yamlManager {
    static FileConfiguration config;
    static File cfile;
    static FileConfiguration data;
    static File dfile;
    static FileConfiguration itemDB;
    static File ifile;

    public yamlManager() {
    }

    public static boolean defaultConfig() {
        dfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "data.yml");
        cfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "config.yml");
        ifile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "itemDB.yml");
        cfile.getParentFile().mkdirs();
        config = FableCraft.getPlugin().getConfig();
        data = new YamlConfiguration();
        itemDB = new YamlConfiguration();
        if (dfile.exists() && cfile.exists()) {
            return true;
        } else {
            try {
                dfile.createNewFile();
                cfile.createNewFile();
                ifile.createNewFile();
                setDefaults();
                return true;
            } catch (IOException var1) {
                return false;
            }
        }
    }

    public static boolean saveData() {
        dfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "data.yml");
        cfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "config.yml");
        ifile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "itemDB.yml");

        try {
            data.save(dfile);
            config.save(cfile);
            itemDB.save(ifile);
            return true;
        } catch (IOException var1) {
            return false;
        }
    }

    public static boolean loadData() {
        dfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "data.yml");
        cfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "config.yml");
        ifile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "itemDB.yml");
        if (cfile.exists() && dfile.exists()) {
            data = YamlConfiguration.loadConfiguration(dfile);
            config = YamlConfiguration.loadConfiguration(cfile);
            itemDB = YamlConfiguration.loadConfiguration(ifile);
            return true;
        } else {
            return defaultConfig();
        }
    }

    public static void setDefaults() {
        config.addDefault("messages.joinMessage", "&6#target# &ajoined the game!");
        config.addDefault("messages.firstJoinMessage", "&6#target# &ajoined the server for the first time!");
        config.addDefault("messages.quitMessage", "&6#target#&a left!");
        config.addDefault("messages.error.noPermission", "&cYou don't have permission to execute this command!");
        config.addDefault("items.unbreakable.enabled", true);
        config.addDefault("items.removeDefaultRecipes", true);
        config.addDefault("items.display.rarity.common", "&f&lCOMMON");
        config.addDefault("items.display.rarity.uncommon", "&a&lUNCOMMON");
        config.addDefault("items.display.rarity.rare", "&9&lRARE");
        config.addDefault("items.display.rarity.epic", "&5&lEPIC");
        config.addDefault("items.display.rarity.legendary", "&6&lEPIC");
        config.setInlineComments("items.display.rarity", List.of("You can add more rarity's if you want :)"));
        config.addDefault("items.lore.prefix", "&8-=-=-=-=-=-=-=-=-=-");
        config.addDefault("items.lore.suffix", "&8-=-=-=-=-=-=-=-=-=-");
        config.addDefault("stats.Health.default", 100);
        config.addDefault("stats.Health.char", "&c❤");
        config.addDefault("stats.Regeneration.default", 1);
        config.addDefault("stats.Regeneration.char", "&d\ud83d\udc9e");
        config.addDefault("stats.Defense.default", 0);
        config.addDefault("stats.Defence.char", "&7\ud83d\udee1️");
        config.addDefault("stats.Mana.default", 20);
        config.addDefault("stats.Mana.char", "&9ᛄ");
        config.addDefault("stats.Damage.default", 1);
        config.addDefault("stats.Damage.char", "&4⚔");
        config.addDefault("actionbar.message", "&c#currentHealth#/#maxHealth#❤");
        config.options().copyDefaults(true);
        itemDB.addDefault("woodenSword.itemType", "WOODEN_SWORD");
        itemDB.addDefault("woodenSword.name", "just a sword");
        itemDB.addDefault("woodenSword.lore", List.of("Just a sword"));
        itemDB.addDefault("woodenSword.customModelData", 1);
        itemDB.addDefault("woodenSword.enchantments", List.of("mending:1", "fire_aspect:10"));
        itemDB.addDefault("woodenSword.damage", 10);
        itemDB.addDefault("woodenSword.hide", List.of("ENCHANTS", "ATTRIBUTES", "DYE", "PLACED_ON", "DESTROYS", "ARMOR_TRIM"));
        itemDB.addDefault("woodenSword.group", "swords");
        itemDB.addDefault("woodenSword.rarity", "common");
        itemDB.addDefault("woodenSword.recipe.type", "shaped");
        itemDB.addDefault("woodenSword.recipe.shape", List.of("  W", " W ", "S  "));
        itemDB.addDefault("woodenSword.recipe.ingredients", List.of("W:OAK_PLANKS", "S:STICK"));
        itemDB.addDefault("leatherChestplate.itemType", "LEATHER_CHESTPLATE");
        itemDB.addDefault("leatherChestplate.health", 10);
        itemDB.addDefault("leatherChestplate.defense", 10);
        itemDB.addDefault("leatherChestplate.mana", 10);
        itemDB.addDefault("leatherChestplate.color", "10,10,10");
        itemDB.addDefault("leatherChestplate.recipe.type", "shapeless");
        itemDB.addDefault("leatherChestplate.recipe.ingredients", List.of("DIAMOND:5", "LEATHER:2", "BLACK_DYE:1"));
        itemDB.addDefault("customBook.itemType", "WRITTEN_BOOK");
        itemDB.addDefault("customBook.group", "books");
        itemDB.addDefault("customBook.title", "title");
        itemDB.addDefault("customBook.author", "author");
        itemDB.addDefault("customBook.pages", List.of("Page1", "Page2\nwith an enter"));
        itemDB.addDefault("customBook.itemType", "BREAD");
        itemDB.addDefault("customBook.group", "food");
        itemDB.addDefault("customBook.nutrition", 5);
        itemDB.options().copyDefaults(true);
        saveData();
    }

    public static List<ItemStack> getCustomItems() {
        List<ItemStack> items = new ArrayList(itemDB.getKeys(false).size());
        String[] itemNames = (String[])(new ArrayList(itemDB.getKeys(false))).toArray(new String[0]);

        for(String name : itemNames) {
            items.add(getItem(name));
        }

        return items;
    }

    public static ItemStack getItem(String name) {
        Material itemType = Material.getMaterial((String)itemDB.get(name + ".itemType"));
        if (itemType == null) {
            Logger var10000 = Bukkit.getLogger();
            String var42 = String.valueOf(itemDB.get(name + ".itemType"));
            var10000.severe("Could not find material " + var42 + " " + name);
            return null;
        } else {
            ItemStack item = ItemStack.of(itemType);
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList(List.of());
            int attributes = 0;
            if (isItemSet(name + ".hide")) {
                for(String hide : (List)itemDB.get(name + ".hide")) {
                    meta.addItemFlags(new ItemFlag[]{ItemFlag.valueOf("HIDE_" + hide)});
                }
            }

            if (isItemSet(name + ".damage")) {
                NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), name + ".damage");
                meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, (Integer)itemDB.get(name + ".damage"));
                String var10001 = String.valueOf(itemDB.get(name + ".damage"));
                lore.add("&8Damage: &f+" + var10001 + String.valueOf(getConfig("stats.Damage.char", (Player)null, true)));
                ++attributes;
            }

            if (isItemSet(name + ".health")) {
                NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), name + ".health");
                meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, (Integer)itemDB.get(name + ".health"));
                String var39 = String.valueOf(itemDB.get(name + ".health"));
                lore.add("&8Health: &f+" + var39 + String.valueOf(getConfig("stats.Health.char", (Player)null, true)));
                ++attributes;
            }

            if (isItemSet(name + ".mana")) {
                NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), name + ".mana");
                meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, (Integer)itemDB.get(name + ".mana"));
                String var40 = String.valueOf(itemDB.get(name + ".mana"));
                lore.add("&8Damage: &f+" + var40 + String.valueOf(getConfig("stats.Mana.char", (Player)null, true)));
                ++attributes;
            }

            if (isItemSet(name + ".defence")) {
                NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), name + ".defence");
                meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, (Integer)itemDB.get(name + ".defence"));
                String var41 = String.valueOf(itemDB.get(name + ".defence"));
                lore.add("&8Defence: &f+" + var41 + String.valueOf(getConfig("stats.Defence.char", (Player)null, true)));
                ++attributes;
            }

            if (attributes != 0) {
                lore.add("");
                lore.addFirst("");
            }

            if (isItemSet(name + ".name")) {
                meta.setItemName((String)itemDB.get(name + ".name"));
            }

            if (isItemSet(name + ".customModelData")) {
                meta.setCustomModelData((Integer)itemDB.get(name + ".customModelData"));
            }

            if (isItemSet(name + ".enchantments")) {
                for(String enchantmentString : (List)itemDB.get(name + ".enchantments")) {
                    String[] enchantString = enchantmentString.split(":");
                    Enchantment enchantment = Enchantment.getByName(enchantString[0]);
                    meta.addEnchant(enchantment, Integer.valueOf(enchantString[1]), true);
                }
            }

            if (isItemSet(name + ".lore")) {
                if (isConfigSet("items.lore.prefix")) {
                    lore.add((String)getConfig("items.lore.prefix", (Player)null, true));
                }

                lore.addAll((List)itemDB.get(name + ".lore"));
                if (isConfigSet("items.lore.suffix")) {
                    lore.add((String)getConfig("items.lore.suffix", (Player)null, true));
                }
            }

            if (isItemSet(name + ".rarity")) {
                lore.add("");
                lore.add((String)getConfig("items.display.rarity." + String.valueOf(itemDB.get(name + ".rarity")), (Player)null, true));
                lore.add("");
            }

            List<String> coloredLore = new ArrayList(List.of());

            for(String s : lore) {
                coloredLore.add(ChatColor.translateAlternateColorCodes('&', s));
            }

            meta.setLore(coloredLore);
            item.setItemMeta(meta);
            if (meta instanceof LeatherArmorMeta) {
                LeatherArmorMeta leatherMeta = (LeatherArmorMeta)meta;
                if (isItemSet(name + ".color")) {
                    String[] colors = String.valueOf(itemDB.get(name + ".color")).split(",");
                    Color color = Color.fromARGB(1, Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
                    leatherMeta.setColor(color);
                }

                item.setItemMeta(leatherMeta);
            } else if (meta instanceof BookMeta) {
                BookMeta bookMeta = (BookMeta)meta;
                if (isItemSet(name + ".title")) {
                    bookMeta.setTitle((String)itemDB.get(name + ".title"));
                }

                if (isItemSet(name + ".author")) {
                    bookMeta.setAuthor((String)itemDB.get(name + ".author"));
                }

                if (isItemSet(name + ".pages")) {
                    bookMeta.setPages((List)itemDB.get(name + ".pages"));
                }
            }

            if (Bukkit.getRecipesFor(item).isEmpty() && isItemSet(name + ".recipe.type")) {
                if (itemDB.get(name + ".recipe.type").toString().toLowerCase(Locale.ROOT).equals("shaped")) {
                    NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), name);
                    ShapedRecipe recipe = new ShapedRecipe(key, item);
                    List<String> shapeString = (List)itemDB.get(name + ".recipe.shape");
                    String[] shapes = (String[])shapeString.toArray(new String[shapeString.size()]);
                    recipe.shape(shapes);

                    for(String s : (List)itemDB.get(name + ".recipe.ingredients")) {
                        String[] splitIngredients = s.split(":", 2);
                        recipe.setIngredient(splitIngredients[0].charAt(0), Material.getMaterial(splitIngredients[1]));
                    }

                    Bukkit.getServer().addRecipe(recipe);
                } else {
                    NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), name);
                    ShapelessRecipe recipe = new ShapelessRecipe(key, item);

                    for(String s : (List)itemDB.get(name + ".recipe.ingredients")) {
                        String[] splitIngredients = s.split(":");
                        recipe.addIngredient(Integer.parseInt(splitIngredients[1]), Material.getMaterial(splitIngredients[0]));
                    }

                    Bukkit.getServer().addRecipe(recipe);
                }
            }

            return item;
        }
    }

    public static boolean isItemSet(String path) {
        return itemDB.get(path) != null;
    }

    public static boolean isConfigSet(String path) {
        return config.get(path) != null;
    }

    public static Object getConfig(String path, Player target, boolean round) {
        Object a = config.get(path);
        if (a == null) {
            return ChatColor.translateAlternateColorCodes('&', "&cOption not found");
        } else if (a instanceof String) {
            String s = (String)a;
            String[] msgs = s.split("#", 0);
            int count = 0;

            for(String m : msgs) {
                if (m.equals("target")) {
                    msgs[count] = msgs[count].replace(m, target.getName());
                } else if (m.equals("maxHealth")) {
                    if (!round) {
                        msgs[count] = msgs[count].replace(m, stats.getPlayerPDC("Health", target));
                    } else {
                        msgs[count] = msgs[count].replace(m, String.valueOf(Math.round(Double.parseDouble(stats.getPlayerPDC("Health", target)))));
                    }
                } else if (m.equals("currentHealth")) {
                    if (!round) {
                        msgs[count] = msgs[count].replace(m, ((MetadataValue)target.getMetadata("currentHealth").getFirst()).asString());
                    } else {
                        msgs[count] = msgs[count].replace(m, String.valueOf(Math.round(((MetadataValue)target.getMetadata("currentHealth").getFirst()).asFloat())));
                    }
                }

                ++count;
            }

            String finalMsg = Arrays.toString(msgs);
            finalMsg = finalMsg.replace("#", "");
            finalMsg = finalMsg.replace("[", "");
            finalMsg = finalMsg.replace("]", "");
            finalMsg = finalMsg.replace(",", "");
            finalMsg = finalMsg.replace("  ", " ");
            return ChatColor.translateAlternateColorCodes('&', finalMsg);
        } else {
            return a;
        }
    }

    public static List<String> getConfigNodes(String path) {
        try {
            Set<String> nodes = config.getConfigurationSection(path).getKeys(false);
            return new ArrayList(nodes);
        } catch (NullPointerException var2) {
            return List.of();
        }
    }
}
