package org.zycong.fableCraft.buildHelpers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import org.joml.SimplexNoise;
import org.yaml.snakeyaml.Yaml;
import org.zycong.fableCraft.FableCraft;
import org.zycong.fableCraft.yamlManager;

import static org.joml.SimplexNoise.noise;

public class buildHelper implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player p = (Player) commandSender;
        if (!p.hasPermission("FableCraft.build")) {
            p.sendMessage((String) yamlManager.getConfig("messages.error.noPermission", p, true));
            return true;
        }if (args.length == 0){
            p.sendMessage(yamlManager.getConfig("messages.error.noValidArgument", null, true).toString());
            return true;
        }

        if (args[0].equals("randomItems")){
            if (!p.hasMetadata("randomItems")){
                p.setMetadata("randomItems", new FixedMetadataValue(FableCraft.getPlugin(), true));
                p.sendMessage((String)yamlManager.getConfig("messages.info.randomItems.enabled", p, true));
            } else {
                p.removeMetadata("randomItems", FableCraft.getPlugin());
                p.sendMessage((String)yamlManager.getConfig("messages.info.randomItems.disabled", p, true));
            }
            return true;
        }

        if (args[0].equals("perlinCyl")){
            if (args.length == 3){
                int size = Integer.parseInt(args[1]);
                int baseY = p.getLocation().getBlockY();
                World world = p.getWorld();
                Material blockMat = Material.getMaterial(args[2]);
                if (blockMat == null) {
                    p.sendMessage(yamlManager.getConfig("messages.error.noValidArgument", null, true).toString());
                    return true;
                }

                for (int x = -size/2; x/2 <= size; x++){
                    for (int z = -size/2; z/2 <= size; z++){
                        int worldX = x + p.getLocation().getBlockX();
                        int worldZ = z +p.getLocation().getBlockZ();
                        Location loc = new Location(world, worldX, noise(worldX, worldZ) * 2 + baseY, worldZ);
                        world.getBlockAt(loc).setType(blockMat);
                    }
                }
                p.sendMessage(yamlManager.getConfig("messages.info.perlinCylSuccess", p, true).toString());
            } else {
                p.sendMessage(yamlManager.getConfig("messages.error.noValidArgument", null, true).toString());
                return true;
            }
            return true;
        }

        p.sendMessage(yamlManager.getConfig("messages.error.noValidArgument", null, true).toString());
        return true;
    }

}
