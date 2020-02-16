package com.blank038.nblockscript.script.model;

import cn.nukkit.Player;
import com.blank038.nblockscript.NBlockScript;

public class ConsoleModel extends MainModel {
    private String[] commands;

    public ConsoleModel(String script) {
        if (script.contains("@+")) {
            commands = script.split("@\\+");
        } else {
            commands = new String[]{script};
        }
    }

    @Override
    public void perform(Player player) {
        for (String command : commands) {
            NBlockScript.getInstance().getServer().dispatchCommand(NBlockScript.getInstance().getServer().getConsoleSender(),
                    command.replace("%player%", player.getName()));
        }
    }
}