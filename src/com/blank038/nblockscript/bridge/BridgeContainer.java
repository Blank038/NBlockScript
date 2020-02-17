package com.blank038.nblockscript.bridge;

import cn.nukkit.plugin.Plugin;
import com.blank038.nblockscript.NBlockScript;

public class BridgeContainer {

    public BridgeContainer(NBlockScript main) {
        Plugin plugin = main.getServer().getPluginManager().getPlugin("EconomyAPI");
        if (main.getServer().getPluginManager().isPluginEnabled(plugin)) {
            EconomyBridge.init();
        }
    }
}
