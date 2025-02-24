package org.zycong.fableCraft;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.yaml.snakeyaml.Yaml;
import org.zycong.fableCraft.playerStats.stats;

public class yamlManager {
    public static FileConfiguration config;
    public static File cfile;
    public static FileConfiguration data;
    public static File dfile;
    public static FileConfiguration itemDB;
    public static File ifile;
    public static FileConfiguration mobDB;
    public static File mfile;
    public static FileConfiguration lootTables;
    public static File lfile;

    public yamlManager() {
    }

    public static boolean defaultConfig() {
        dfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "data.yml");
        cfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "config.yml");
        ifile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "item.yml");
        mfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "mob.yml");
        lfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "LootTables.yml");
        cfile.getParentFile().mkdirs();
        config = FableCraft.getPlugin().getConfig();
        data = new YamlConfiguration();
        itemDB = new YamlConfiguration();
        mobDB = new YamlConfiguration();
        lootTables = new YamlConfiguration();
        if (dfile.exists() && cfile.exists() && ifile.exists() && mfile.exists() && lfile.exists()) {
            return true;
        } else {
            try {
                dfile.createNewFile();
                cfile.createNewFile();
                ifile.createNewFile();
                mfile.createNewFile();
                lfile.createNewFile();
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
        ifile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "item.yml");
        mfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "mob.yml");
        lfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "LootTables.yml");

        try {
            data.save(dfile);
            config.save(cfile);
            itemDB.save(ifile);
            mobDB.save(mfile);
            lootTables.save(lfile);
            return true;
        } catch (IOException var1) {
            return false;
        }
    }

    public static boolean loadData() {
        dfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "data.yml");
        cfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "config.yml");
        ifile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "item.yml");
        mfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "mob.yml");
        lfile = new File(FableCraft.getPlugin().getDataFolder().getAbsolutePath(), "LootTables.yml");
        if (dfile.exists() && cfile.exists() && ifile.exists() && mfile.exists() && lfile.exists()) {
            data = YamlConfiguration.loadConfiguration(dfile);
            config = YamlConfiguration.loadConfiguration(cfile);
            itemDB = YamlConfiguration.loadConfiguration(ifile);
            mobDB = YamlConfiguration.loadConfiguration(mfile);
            lootTables = YamlConfiguration.loadConfiguration(lfile);
            FableCraft.customMobs = (List<LivingEntity>) yamlManager.data.get("customMobs");
            return true;
        } else {
            return defaultConfig();
        }
    }

    public static void setDefaults() {
        config.addDefault("messages.joinMessage", "&6#target# &ajoined the game!");
        config.addDefault("messages.firstJoinMessage", "&6#target# &ajoined the server for the first time!");
        config.addDefault("messages.quitMessage", "&6#target#&a left!");
        config.addDefault("messages.error.noPermissionCraft", "&cYou don't have permission to make this item!");
        config.addDefault("messages.error.noPermission", "&cYou don't have permission to execute this command!");
        config.addDefault("messages.error.noValidArgument", "&cInvalid arguments!");
        config.addDefault("messages.info.resetSuccess", "&aSuccessfully reset the stats of #target#!");
        config.addDefault("messages.info.randomItems.enabled", "&aEnabled random items!");
        config.addDefault("messages.info.randomItems.disabled", "&aDisabled random items!");
        config.addDefault("messages.info.perlinCylSuccess", "&aSuccessfully made a perlin cylinder!");
        config.addDefault("food.removeHunger", true);
        config.addDefault("mobs.removeAllVanillaSpawning", true);
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
        config.addDefault("stats.Defence.default", 0);
        config.addDefault("stats.Defence.char", "&7\ud83d\udee1");
        config.addDefault("stats.Mana.default", 20);
        config.addDefault("stats.Mana.char", "&9ᛄ");
        config.addDefault("stats.ManaRegeneration.default", 1);
        config.addDefault("stats.ManaRegeneration.char", "&9\uD83C\uDF00");
        config.addDefault("stats.Damage.default", 1);
        config.addDefault("stats.Damage.char", "&4⚔");
        config.addDefault("actionbar.message", "&c#currentHealth#/#maxHealth#❤&r   &9#currentMana#/#maxMana#ᛄ");
        config.options().copyDefaults(true);



        itemDB.addDefault("woodenSword.itemType", "WOODEN_SWORD");
        itemDB.addDefault("woodenSword.name", "just a sword");
        itemDB.addDefault("woodenSword.lore", List.of("Just a sword"));
        itemDB.addDefault("woodenSword.customModelData", 1);
        itemDB.addDefault("woodenSword.enchantments", List.of("mending:1", "fire_aspect:10"));
        itemDB.addDefault("woodenSword.Damage", 10);
        itemDB.addDefault("woodenSword.hide", List.of("ENCHANTS", "ATTRIBUTES", "DYE", "PLACED_ON", "DESTROYS", "ARMOR_TRIM"));
        itemDB.addDefault("woodenSword.group", "swords");
        itemDB.addDefault("woodenSword.rarity", "common");
        itemDB.addDefault("woodenSword.recipe.type", "shaped");
        itemDB.addDefault("woodenSword.recipe.shape", List.of("  W", " W ", "S  "));
        itemDB.addDefault("woodenSword.recipe.ingredients", List.of("W:OAK_PLANKS", "S:STICK"));
        itemDB.addDefault("woodenSword.recipe.permission", "craft.wooden_sword");

        itemDB.addDefault("leatherChestplate.itemType", "LEATHER_CHESTPLATE");
        itemDB.addDefault("leatherChestplate.Health", 10);
        itemDB.addDefault("leatherChestplate.Defence", 10);
        itemDB.addDefault("leatherChestplate.Mana", 10);
        itemDB.addDefault("leatherChestplate.color", "10,10,10");
        itemDB.addDefault("leatherChestplate.recipe.type", "shapeless");
        itemDB.addDefault("leatherChestplate.recipe.ingredients", List.of("DIAMOND:5", "LEATHER:2", "BLACK_DYE:1"));
        itemDB.addDefault("customBook.itemType", "WRITTEN_BOOK");
        itemDB.addDefault("customBook.group", "books");
        itemDB.addDefault("customBook.title", "title");
        itemDB.addDefault("customBook.author", "author");
        itemDB.addDefault("customBook.pages", List.of("Page1", "Page2\nwith an enter"));
        itemDB.addDefault("customBread.itemType", "BREAD");
        itemDB.addDefault("customBread.group", "food");
        itemDB.addDefault("customBread.nutrition", 5);
        itemDB.options().copyDefaults(true);

        mobDB.addDefault("spider.type", "SPIDER");
        mobDB.addDefault("spider.customName.name", "&aSpider &c#currentHealth#/#maxHealth#");
        mobDB.addDefault("spider.customName.visible", true);
        mobDB.addDefault("spider.glowing", false);
        mobDB.addDefault("spider.invulnerable", false);
        mobDB.setInlineComments("spider.health", List.of("If you want a higher value then 2048 you need to change the max health in the spigot.yml file (option: settings.attribute.maxHealth)"));
        mobDB.addDefault("spider.health", 100);
        mobDB.setInlineComments("spider.damage", List.of("If you want a higher value then 2048 you need to change the max health in the spigot.yml file (option: settings.attribute.maxHealth)"));
        mobDB.addDefault("spider.damage", 10);
        mobDB.setInlineComments("spider.speed", List.of("If you want a higher value then 2048 you need to change the max health in the spigot.yml file (option: settings.attribute.maxHealth)"));
        mobDB.addDefault("spider.speed", 2);
        mobDB.addDefault("spider.lootTable", "spiderDrops");
        mobDB.addDefault("spider.randomSpawns.frequency", 1);
        mobDB.setInlineComments("spider.randomSpawns.frequency", List.of("0 is 0% of entities, 1 is 100%, 0.01 is 1% etc"));
        mobDB.addDefault("spider.randomSpawns.options.spawnOn", List.of("GRASS_BLOCK"));
        mobDB.addDefault("spider.randomSpawns.options.biomes", List.of("PLAINS", "FOREST"));
        mobDB.options().copyDefaults(true);


        lootTables.addDefault("spiderDrops.maxItems", 10);
        lootTables.addDefault("spiderDrops.minItems", 1);
        lootTables.addDefault("spiderDrops.items", List.of("STRING:1:5:9", "customBook:1:4:1"));
        lootTables.setInlineComments("spiderDrops.items", List.of("First number: minimal amount of item (default 1)", "Second number: maximal amount of item", "Third number: weight of the item (default 1)"));

        lootTables.options().copyDefaults(true);

        data.addDefault("customMobs", List.of());
        data.options().copyDefaults(true);

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
        Material itemType = Material.getMaterial((String) Objects.requireNonNull(itemDB.get(name + ".itemType")));
        if (itemType == null) {
            Logger var10000 = Bukkit.getLogger();
            String var42 = String.valueOf(itemDB.get(name + ".itemType"));
            var10000.severe("Could not find material " + var42 + " " + name);
            return null;
        } else {
            ItemStack item = ItemStack.of(itemType);
            ItemMeta meta = item.getItemMeta();
            List<String> lore = new ArrayList(List.of());
            List<String> PDC = new ArrayList(List.of());
            int attributes = 0;
            if (isItemSet(name + ".hide")) {
                for(Object hide : (List)itemDB.get(name + ".hide")) {
                    meta.addItemFlags(new ItemFlag[]{ItemFlag.valueOf("HIDE_" + hide)});
                }
            }

            for(String s : FableCraft.itemStats){
                if (isItemSet(name + "." + s)) {
                    String var41 = String.valueOf(itemDB.get(name + "." + s));
                    lore.add("&8" + s + ": &f+" + var41 + getConfig("stats." + s + ".char", null, true));
                    ++attributes;
                    PDC.add(s + ";" + itemDB.get(name + "." + s));
                    //item = stats.setItemPDC(s, item, itemDB.get(name + "." + s));
                }
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
                for(Object enchantmentString : (List) Objects.requireNonNull(itemDB.get(name + ".enchantments"))) {
                    String[] enchantString = enchantmentString.toString().split(":");
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

            if (itemDB.get(name + ".recipe.permission") != null){
                String permission = (String) itemDB.get(name + ".recipe.permission");
                PDC.add("craftPerms;" + permission);
            }
            if (Bukkit.getRecipesFor(item).isEmpty() && isItemSet(name + ".recipe.type")) {
                if (itemDB.get(name + ".recipe.type").toString().toLowerCase(Locale.ROOT).equals("shaped")) {
                    NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), name);
                    ShapedRecipe recipe = new ShapedRecipe(key, item);
                    List<String> shapeString = (List)itemDB.get(name + ".recipe.shape");
                    String[] shapes = shapeString.toArray(new String[shapeString.size()]);
                    recipe.shape(shapes);

                    for(Object s : (List) Objects.requireNonNull(itemDB.get(name + ".recipe.ingredients"))) {
                        String[] splitIngredients = s.toString().split(":", 2);
                        recipe.setIngredient(splitIngredients[0].charAt(0), Material.getMaterial(splitIngredients[1]));
                    }

                    Bukkit.getServer().addRecipe(recipe);
                } else {
                    NamespacedKey key = new NamespacedKey(FableCraft.getPlugin(), name);
                    ShapelessRecipe recipe = new ShapelessRecipe(key, item);

                    for(Object s : (List)itemDB.get(name + ".recipe.ingredients")) {
                        String[] splitIngredients = s.toString().split(":");
                        recipe.addIngredient(Integer.parseInt(splitIngredients[1]), Material.getMaterial(splitIngredients[0]));
                    }

                    Bukkit.getServer().addRecipe(recipe);
                }
            }

            for (String s : PDC){
                String[] values = s.split(";");
                stats.setItemPDC(values[0], item, values[1]);
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
        } else if (a instanceof String s) {
            return setPlaceholders(s, round, target);
        } else {
            return a;
        }
    }

    public static String setPlaceholders(String s, boolean round, Player target){
        String[] msgs = s.split("#", 0);
        int count = 0;

        for(String m : msgs) {
            if (m.equals("target")) {
                msgs[count] = msgs[count].replaceAll(m, target.getName());
                msgs[count] = msgs[count].replaceAll("\\s","");
            } else if (m.equals("maxHealth")) {
                if (!round) {
                    msgs[count] = msgs[count].replaceAll(m, stats.getPlayerPDC("Health", target));
                } else {
                    msgs[count] = msgs[count].replaceAll(m, String.valueOf(Math.round(Double.parseDouble(stats.getPlayerPDC("Health", target)))));
                }
                msgs[count] = msgs[count].replaceAll("\\s","");
            } else if (m.equals("currentHealth")) {
                if (!round) {
                    msgs[count] = msgs[count].replaceAll(m, target.getMetadata("currentHealth").getFirst().asString());
                } else {
                    msgs[count] = msgs[count].replaceAll(m, String.valueOf(Math.round(target.getMetadata("currentHealth").getFirst().asFloat())));
                }
                msgs[count] = msgs[count].replaceAll("\\s","");
            } else if (m.equals("maxMana")) {
                if (!round) {
                    msgs[count] = msgs[count].replaceAll(m, stats.getPlayerPDC("Mana", target));
                } else {
                    msgs[count] = msgs[count].replaceAll(m, String.valueOf(Math.round(Double.parseDouble(stats.getPlayerPDC("Mana", target)))));
                }
                msgs[count] = msgs[count].replaceAll("\\s","");
            } else if (m.equals("currentMana")) {
                if (!round) {
                    msgs[count] = msgs[count].replaceAll(m, target.getMetadata("currentMana").getFirst().asString());
                } else {
                    msgs[count] = msgs[count].replaceAll(m, String.valueOf(Math.round(target.getMetadata("currentMana").getFirst().asFloat())));
                }
                msgs[count] = msgs[count].replaceAll("\\s","");
            }

            ++count;
        }

        String finalMsg = String.join("", msgs);
        finalMsg = finalMsg.replaceAll("#", "");
        finalMsg = finalMsg.replaceAll(",", "");
        finalMsg = finalMsg.replace("[", "");
        finalMsg = finalMsg.replace("]", "");
        return ChatColor.translateAlternateColorCodes('&', finalMsg);
    } public static String setPlaceholders(String s, boolean round, Entity target){
        String[] msgs = s.split("#", 0);
        LivingEntity e = (LivingEntity) target;
        int count = 0;

        for(String m : msgs) {
            if (m.equals("target")) {
                msgs[count] = msgs[count].replaceAll(m, target.getName());
                msgs[count] = msgs[count].replaceAll("\\s","");
            } else if (m.equals("maxHealth")) {
                if (!round) {
                    msgs[count] = msgs[count].replaceAll(m, String.valueOf(e.getMaxHealth()));
                } else {
                    msgs[count] = msgs[count].replaceAll(m, String.valueOf(Math.round(e.getMaxHealth())));
                }
                msgs[count] = msgs[count].replaceAll("\\s","");
            } else if (m.equals("currentHealth")) {
                if (!round) {
                    msgs[count] = msgs[count].replaceAll(m, String.valueOf(e.getHealth()));
                } else {
                    msgs[count] = msgs[count].replaceAll(m, String.valueOf(Math.round(Float.parseFloat(String.valueOf(e.getHealth())))));
                }
                msgs[count] = msgs[count].replaceAll("\\s","");
            }

            ++count;
        }

        String finalMsg = String.join("", msgs);
        finalMsg = finalMsg.replaceAll("#", "");
        finalMsg = finalMsg.replaceAll(",", "");
        finalMsg = finalMsg.replace("[", "");
        finalMsg = finalMsg.replace("]", "");
        return ChatColor.translateAlternateColorCodes('&', finalMsg);
    }

    public static List<String> getConfigNodes(String path) {
        try {
            Set<String> nodes = config.getConfigurationSection(path).getKeys(false);
            return new ArrayList(nodes);
        } catch (NullPointerException var2) {
            return List.of();
        }
    } public static List<String> getDataNodes(String path) {
        try {
            Set<String> nodes = data.getConfigurationSection(path).getKeys(false);
            return new ArrayList(nodes);
        } catch (NullPointerException var2) {
            return List.of();
        }
    } public static Object getData(String path) {
        return data.get(path);
    }

    public static List<String> getMobNodes(String path) {
        try {
            Set<String> nodes = mobDB.getConfigurationSection(path).getKeys(false);
            return new ArrayList(nodes);
        } catch (NullPointerException var2) {
            return List.of();
        }
    }

    public static List<String> getLootTableNodes(String path) {
        try {
            Set<String> nodes = lootTables.getConfigurationSection(path).getKeys(false);
            return new ArrayList(nodes);
        } catch (NullPointerException var2) {
            return List.of();
        }
    }

    public static Object getLootTableData(String path) {
        Object a = lootTables.get(path);
        return Objects.requireNonNullElseGet(a, () -> ChatColor.translateAlternateColorCodes('&', "&cOption not found"));
    }

    public static List<String> getItemNodes(String path) {
        try {
            Set<String> nodes = itemDB.getConfigurationSection(path).getKeys(false);
            return new ArrayList(nodes);
        } catch (NullPointerException var2) {
            return List.of();
        }
    }
}
