package com.blank038.nblockscript.script.model;

import cn.nukkit.Player;

public class TitleModel extends MainModel {
    private String main;
    private String sub;
    private int fadeIn;
    private int fadeOut;
    private int stay;

    public TitleModel(String script) {
        String[] split = script.split("//");
        main = split[0];
        sub = split[1];
        fadeIn = split.length > 2 ? Integer.parseInt(split[2]) : 0;
        stay = split.length > 3 ? Integer.parseInt(split[3]) : 20;
        fadeOut = split.length > 4 ? Integer.parseInt(split[4]) : 0;
    }

    @Override
    public void perform(Player player) {
        player.sendTitle(main, sub, fadeIn, stay, fadeOut);
    }
}