package com.blank038.nblockscript.script;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import com.blank038.nblockscript.script.condition.CheckMoneyCondition;
import com.blank038.nblockscript.script.condition.TakeMoneyCondition;
import com.blank038.nblockscript.script.condition.MainCondition;
import com.blank038.nblockscript.script.condition.PermissionCondition;
import com.blank038.nblockscript.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Blank038
 */
public class ScriptContainer {
    private List<ScriptModel> scriptModels = new ArrayList<>();
    private String key;
    private List<String> scripts;
    private List<MainCondition> conditions = new ArrayList<>();
    private List<String> conditionTexts = new ArrayList<>();

    public ScriptContainer(String key, ConfigSection section) {
        this.key = key;
        scripts = section.getStringList("scripts");
        for (String script : scripts) {
            scriptModels.add(new ScriptModel(script));
        }
        List<String> tempConditions = section.getStringList("conditions");
        for (String condition : tempConditions) {
            MainCondition result = getCondition(condition);
            if (result != null) {
                conditions.add(result);
            }
        }
    }

    public List<String> getScripts() {
        return scripts;
    }

    public void perform(Player player) {
        if (allow(player)) {
            for (MainCondition condition : conditions) {
                condition.execute(player);
            }
            scriptModels.forEach((model) -> model.perform(player));
        }
    }

    public void setConfig(Config config) {
        ConfigSection section = new ConfigSection();
        section.set("scripts", new ArrayList<>(scripts));
        section.set("conditions", new ArrayList<>(conditionTexts));
        config.set(key, section);
    }

    public void addScriptModel(String script) {
        scriptModels.add(new ScriptModel(script));
        scripts.add(script);
    }

    public void removeScriptModel(int index) {
        if (index < scriptModels.size() && index >= 0) {
            scriptModels.remove(index);
            scripts.remove(index);
        }
    }

    public boolean addCondition(String condition) {
        MainCondition temp = getCondition(condition);
        if (temp != null) {
            conditions.add(temp);
            conditionTexts.add(condition);
            return true;
        }
        return false;
    }

    public void removeCondition(int index) {
        if (index < conditions.size() && index >= 0) {
            conditions.remove(index);
            conditionTexts.remove(index);
        }
    }

    public boolean allow(Player player) {
        boolean allow = true;
        for (MainCondition condition : conditions) {
            if (!condition.allow(player)) {
                allow = false;
                player.sendMessage(MessageUtil.getConfigString("message.condition." + condition.getConditionType().getKey(), true));
                break;
            }
        }
        return allow;
    }

    public List<String> getCondition() {
        return conditionTexts;
    }

    private MainCondition getCondition(String condition) {
        String[] split = condition.split("@@");
        switch (split[0].toLowerCase()) {
            case "checkmoney":
                return new CheckMoneyCondition(Double.parseDouble(split[1]));
            case "permission":
                return new PermissionCondition(split[1]);
            case "takemoney":
                return new TakeMoneyCondition(Double.parseDouble(split[1]));
            default:
                break;
        }
        return null;
    }
}