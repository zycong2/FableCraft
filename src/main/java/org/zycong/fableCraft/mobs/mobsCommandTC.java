package org.zycong.fableCraft.mobs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zycong.fableCraft.yamlManager;

import java.util.List;

public class mobsCommandTC implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1){
            return List.of("spawn", "killAll", "reload");
        } if (args.length == 2 && args[0].equals("spawn")){
            return yamlManager.getMobNodes("");
        }

        return List.of();
    }
}
