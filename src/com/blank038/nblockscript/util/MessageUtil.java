package com.blank038.nblockscript.util;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import com.blank038.nblockscript.NBlockScript;

/**
 * @author Blank038
 */
public class MessageUtil {

    public static String formatMessage(String message, boolean p) {
        String prefix = p ? NBlockScript.getInstance().getConfig().getString("message.prefix") : "";
        return TextFormat.colorize('&', prefix + message);
    }

    public static String getConfigString(String index, boolean p) {
        String prefix = p ? NBlockScript.getInstance().getConfig().getString("message.prefix") : "";
        String message = NBlockScript.getInstance().getConfig().getString(index);
        return TextFormat.colorize('&', prefix + (message == null ? "" : message));
    }

    public static String[] formatMessage(boolean p, final String... messages) {
        String prefix = p ? NBlockScript.getInstance().getConfig().getString("message.prefix") : "";
        String[] results = new String[messages.length];
        for (int i = 0; i < messages.length; i++) {
            results[i] = TextFormat.colorize('&', prefix + messages[i]);
        }
        return results;
    }

    public static void sendMessage(CommandSender sender, String[] message) {
        for (int i = 0; i < message.length; i++) {
            sender.sendMessage(message[i]);
        }
    }

    public static void sendMessage(CommandSender sender, String message, boolean p) {
        String prefix = p ? NBlockScript.getInstance().getConfig().getString("message.prefix") : "";
        sender.sendMessage(TextFormat.colorize('&', (p ? prefix : "") + message));
    }
}