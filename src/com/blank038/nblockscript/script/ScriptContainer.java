package com.blank038.nblockscript.script;

import cn.nukkit.Player;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Blank038
 */
public class ScriptContainer {
    private List<ScriptModel> scriptModels = new ArrayList<>();
    private String key;
    private List<String> scripts;

    public ScriptContainer(String key, ConfigSection section) {
        this.key = key;
        scripts = section.getStringList("scripts");
        for (String script : scripts) {
            scriptModels.add(new ScriptModel(script));
        }
    }

    public List<String> getScripts() {
        return scripts;
    }

    public void perform(Player player) {
        scriptModels.forEach((model) -> model.perform(player));
    }

    public void setConfig(Config config) {
        ConfigSection section = new ConfigSection();
        section.set("scripts", new ArrayList<>(scripts));
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
}