package com.blank038.nblockscript.api;

import cn.nukkit.Player;
import cn.nukkit.level.Location;
import com.blank038.nblockscript.NBlockScript;
import com.blank038.nblockscript.data.TempLocationData;

/**
 * @author Blank038
 */
public class NBlockScriptAPI {
    private NBlockScript main;

    public NBlockScriptAPI(NBlockScript main) {
        this.main = main;
    }

    public void checkPlayer(Player player, Location location) {
        main.getScriptManager().check(player, location);
    }

    public TempLocationData getTempLocationData(Player player) {
        return main.getLocationTask().getPlayerData(player);
    }
}