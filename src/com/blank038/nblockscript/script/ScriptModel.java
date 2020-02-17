package com.blank038.nblockscript.script;

import cn.nukkit.Player;
import com.blank038.nblockscript.script.model.*;

/**
 * @author Blank038
 */
public class ScriptModel {
    private MainModel mainModel;

    public ScriptModel(String script) {
        String[] split = script.split("@@");
        switch (split[0].toLowerCase()) {
            case "message":
                mainModel = new MessageModel(split[1]);
                break;
            case "bypass":
                mainModel = new BypassModel(split[1]);
                break;
            case "command":
                mainModel = new CommandModel(split[1]);
                break;
            case "title":
                mainModel = new TitleModel(split[1]);
                break;
            case "action":
                mainModel = new ActionModel(split[1]);
                break;
            case "console":
                mainModel = new ConsoleModel(split[1]);
                break;
            default:
                break;
        }
    }

    public void perform(Player player) {
        if (mainModel != null) mainModel.perform(player);
    }
}