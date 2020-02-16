package com.blank038.nblockscript.script.model;

import cn.nukkit.Player;

public class MessageModel extends MainModel {
    private String text;

    public MessageModel(String text) {
        this.text = text.replace("&", "§");
    }

    @Override
    public void perform(Player player) {
        player.sendMessage(text.replace("%player%", player.getName()));
    }
}