package org.zycong.fableCraft.buildHelpers;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class buildHelperTC implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("perlinCyl", "randomItems");
        } else if (args[0].equals("perlinCyl") && args.length == 4){
            List<String> materials = new ArrayList<String>(List.of());
            for (Material mat : Material.values()){
                materials.add(mat.toString());
            }
            return materials;
        }
        return List.of();
    }
}
