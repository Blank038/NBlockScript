package com.blank038.nblockscript.script.model;

import cn.nukkit.Player;
import com.blank038.nblockscript.NBlockScript;

public class BypassModel extends MainModel {
    private String[] commands;

    public BypassModel(String script) {
        if (script.contains("@+")) {
            commands = script.split("@+");
        } else {
            commands = new String[]{script};
        }
    }

    @Override
    public void perform(Player player) {
        boolean isOp = player.isOp();
        try {
            player.setOp(true);
            for (String command : commands) {
                NBlockScript.getInstance().getServer().dispatchCommand(player, command.replace("%player%", player.getName()));
            }
        } catch (Exception e) {
        } finally {
            player.setOp(isOp);
        }
    }
}