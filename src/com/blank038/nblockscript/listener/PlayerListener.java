package com.blank038.nblockscript.listener;

import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import com.blank038.nblockscript.NBlockScript;
import com.blank038.nblockscript.enums.EnumType;

public class PlayerListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        Block block = event.getBlock();
        NBlockScript.getApi().checkPlayer(event.getPlayer(), block.getLocation(), EnumType.INTERACT);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getTo().getLevelBlock().equals(event.getFrom().getLevelBlock())) {
            return;
        }
        NBlockScript.getInstance().getLocationTask().update(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        long start = System.currentTimeMillis();
        Block block = event.getBlock();
        if (NBlockScript.getApi().checkPlayer(event.getPlayer(), block.getLocation(), EnumType.BREAK)) {
            event.setCancelled(true);
        }
        long end = System.currentTimeMillis();
    }
}
