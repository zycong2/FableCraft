package org.zycong.fableCraft.buildHelpers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Random;

public class buildListeners implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if (event.getPlayer().hasMetadata("randomItems")){
            event.getPlayer().getInventory().setHeldItemSlot(new Random().nextInt(9));
        }
    }
}
