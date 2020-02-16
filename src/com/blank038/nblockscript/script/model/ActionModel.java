package com.blank038.nblockscript.script.model;

import cn.nukkit.Player;

public class ActionModel extends MainModel {
    private String text;
    private int fadeIn;
    private int fadeOut;
    private int duration;

    public ActionModel(String script) {
        String[] split = script.split("//");
        text = split[0].replace("&", "§");
        fadeIn = split.length > 1 ? Integer.parseInt(split[1]) : 1;
        duration = split.length > 2 ? Integer.parseInt(split[2]) : 0;
        fadeOut = split.length > 3 ? Integer.parseInt(split[3]) : 1;
    }

    @Override
    public void perform(Player player) {
        player.sendActionBar(text.replace("%player%", player.getName()), fadeIn, duration, fadeOut);
    }
}