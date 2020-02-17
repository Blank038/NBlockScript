package com.blank038.nblockscript.api;

import cn.nukkit.Player;
import cn.nukkit.level.Location;
import com.blank038.nblockscript.NBlockScript;
import com.blank038.nblockscript.data.TempLocationData;
import com.blank038.nblockscript.enums.EnumType;

/**
 * @author Blank038
 */
public class NBlockScriptAPI {
    private NBlockScript main;

    public NBlockScriptAPI(NBlockScript main) {
        this.main = main;
    }

    public boolean checkPlayer(Player player, Location location, EnumType enumType) {
        return main.getScriptManager().check(player, location, enumType);
    }

    public TempLocationData getTempLocationData(Player player) {
        return main.getLocationTask().getPlayerData(player);
    }
}