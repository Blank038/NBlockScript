package com.blank038.nblockscript.task;

import cn.nukkit.Player;
import com.blank038.nblockscript.NBlockScript;
import com.blank038.nblockscript.data.TempLocationData;

import java.util.*;

/**
 * @author Blank038
 */
public class LocationTask implements Runnable {
    private HashMap<String, TempLocationData> locationMap = new HashMap<>();

    public LocationTask() {
    }

    @Override
    public void run() {
        clearOfflineData();
    }

    public void update(Player player) {
        if (locationMap.containsKey(player.getName())) {
            locationMap.get(player.getName()).update(player);
        } else {
            locationMap.put(player.getName(), new TempLocationData(player));
        }
    }

    private void clearOfflineData() {
        Set<String> clears = new HashSet<>();
        for (Map.Entry<String, TempLocationData> entry : new HashMap<>(locationMap).entrySet()) {
            Player player = NBlockScript.getInstance().getServer().getPlayer(entry.getKey());
            if (player == null || !player.isOnline()) {
                clears.add(entry.getKey());
            }
        }
        clears.forEach((name) -> locationMap.remove(name));
    }

    public TempLocationData getPlayerData(Player player) {
        return locationMap.get(player.getName());
    }
}