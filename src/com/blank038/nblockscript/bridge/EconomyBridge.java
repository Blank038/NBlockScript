package com.blank038.nblockscript.bridge;

import cn.nukkit.Player;
import me.onebone.economyapi.EconomyAPI;

public class EconomyBridge {
    private static EconomyAPI economyAPI;

    public static void init() {
        economyAPI = EconomyAPI.getInstance();
    }

    public static boolean hasEconomy() {
        return economyAPI != null;
    }

    public static double balance(Player player) {
        return hasEconomy() ? economyAPI.myMoney(player.getUniqueId()) : 0;
    }

    public static void take(Player player, double amount) {
        if (hasEconomy()) {
            economyAPI.reduceMoney(player.getUniqueId(), amount);
        }
    }

    public static void give(Player player, double amount) {
        if (hasEconomy()) {
            economyAPI.addMoney(player.getUniqueId(), amount);
        }
    }
}