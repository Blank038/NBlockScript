package com.blank038.nblockscript.script;

import cn.nukkit.Player;
import cn.nukkit.level.Location;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import com.blank038.nblockscript.NBlockScript;
import com.blank038.nblockscript.data.BindData;
import com.blank038.nblockscript.data.TempLocationData;
import com.blank038.nblockscript.enums.EnumType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Blank038
 */
public class ScriptManager {
    private HashMap<String, ScriptContainer> scriptMap = new HashMap<>();
    private NBlockScript main;
    private HashMap<String, BindData> bindDataMap = new HashMap<>();

    public ScriptManager(NBlockScript nBlockScript) {
        this.main = nBlockScript;
    }

    public void init() {
        File file = new File(main.getDataFolder(), "script.yml");
        if (!file.exists()) {
            createFile(file);
        }
        File bind = new File(main.getDataFolder(), "bind.yml");
        if (!bind.exists()) {
            createFile(bind);
        }
        scriptMap.clear();
        Config scriptConfig = new Config(file);
        for (String key : scriptConfig.getKeys()) {
            if (!key.contains(".")) {
                scriptMap.put(key, new ScriptContainer(key, scriptConfig.getSection(key)));
            }
        }
        bindDataMap.clear();
        Config bindConfig = new Config(bind);
        for (String key : bindConfig.getKeys()) {
            if (!key.contains(".")) {
                bindDataMap.put(key, new BindData(key, bindConfig.getSection(key)));
            }
        }
    }

    public boolean addScript(String script) {
        if (scriptMap.containsKey(script)) {
            return false;
        }
        ConfigSection section = new ConfigSection();
        section.set("scripts", new ArrayList<>());
        scriptMap.put(script, new ScriptContainer(script, section));
        return true;
    }

    public boolean hasScript(String script) {
        return scriptMap.containsKey(script);
    }

    public boolean check(Player player, Location location, EnumType enumType) {
        boolean check = false;
        for (Map.Entry<String, BindData> entry : bindDataMap.entrySet()) {
            if (entry.getValue().check(player, location, enumType)) {
                check = true;
            }
        }
        return check;
    }

    public void addBind(String script, Location location, EnumType enumType) {
        String key = String.valueOf(System.currentTimeMillis());
        bindDataMap.put(key, new BindData(key, location, enumType, script));
    }

    public void trigger(Player player, String script) {
        TempLocationData data = NBlockScript.getApi().getTempLocationData(player);
        if (data != null && !data.isLock() && scriptMap.containsKey(script)) {
            NBlockScript.getApi().getTempLocationData(player).lock();
            scriptMap.get(script).perform(player);
        }
    }

    public void ignoreTrigger(Player player, String script) {
        if (scriptMap.containsKey(script)) {
            scriptMap.get(script).perform(player);
        }
    }

    public void removeScript(String script) {
        scriptMap.remove(script);
    }

    public void addModel(String script, String model) {
        if (scriptMap.containsKey(script)) {
            scriptMap.get(script).addScriptModel(model);
        }
    }

    public void removeModel(String script, int index) {
        if (scriptMap.containsKey(script)) {
            scriptMap.get(script).removeScriptModel(index);
        }
    }

    public boolean addCondition(String script, String condition) {
        if (scriptMap.containsKey(script)) {
            return scriptMap.get(script).addCondition(condition);
        }
        return false;
    }

    public void removeCondition(String script, int index) {
        if (scriptMap.containsKey(script)) {
            scriptMap.get(script).removeCondition(index);
        }
    }

    public List<String> getScriptInfo(String script) {
        return scriptMap.containsKey(script) ? scriptMap.get(script).getScripts() : new ArrayList<>();
    }

    public List<String> getScriptCondition(String script) {
        return scriptMap.containsKey(script) ? scriptMap.get(script).getCondition() : new ArrayList<>();
    }

    public void saveAll() {
        File script = new File(main.getDataFolder(), "script.yml");
        File bind = new File(main.getDataFolder(), "bind.yml");
        // 遍历设置 script.yml 内容
        Config config = new Config();
        for (Map.Entry<String, ScriptContainer> entry : scriptMap.entrySet()) {
            entry.getValue().setConfig(config);
        }
        // 存储 script.yml 文件
        config.save(script);
        // 遍历设置 bind.yml 内容
        Config bindConfig = new Config();
        for (Map.Entry<String, BindData> entry : bindDataMap.entrySet()) {
            entry.getValue().setConfig(bindConfig);
        }
        // 存储 script.yml 文件
        bindConfig.save(bind);
    }

    private void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}