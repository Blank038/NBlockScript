package com.blank038.nblockscript.data;

import cn.nukkit.Player;
import cn.nukkit.level.Location;

/**
 * @author Blank038
 */
public class TempLocationData {
    private Location location;
    private boolean lock;

    public TempLocationData(Player player) {
        location = player.getLocation();
    }

    public Location getLocation() {
        return location;
    }

    public void update(Player player) {
        Location temp = player.getLocation();
        if (!location.getLevelBlock().equals(temp.getLevelBlock())) {
            location = temp.clone();
            lock = false;
        }
    }

    public void lock() {
        lock = true;
    }

    public boolean isLock() {
        return lock;
    }
}