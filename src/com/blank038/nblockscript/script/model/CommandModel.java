package com.blank038.nblockscript.script.model;

import cn.nukkit.Player;
import com.blank038.nblockscript.NBlockScript;

public class CommandModel extends MainModel {
    private String[] commands;

    public CommandModel(String script) {
        if (script.contains("@+")) {
            commands = script.split("@\\+");
        } else {
            commands = new String[]{script};
        }
    }

    @Override
    public void perform(Player player) {
        for (String command : commands) {
            NBlockScript.getInstance().getServer().dispatchCommand(player, command.replace("%player%", player.getName()));
        }
    }
}