package com.blank038.nblockscript.listener;

import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import com.blank038.nblockscript.NBlockScript;

public class PlayerListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        Block block = event.getBlock();
        NBlockScript.getApi().checkPlayer(event.getPlayer(), block.getLocation());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getTo().getLevelBlock().equals(event.getFrom().getLevelBlock())){
            return;
        }
        NBlockScript.getInstance().getLocationTask().update(event.getPlayer());
    }
}
