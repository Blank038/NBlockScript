package com.blank038.nblockscript;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import com.blank038.nblockscript.api.NBlockScriptAPI;
import com.blank038.nblockscript.command.NBSCommand;
import com.blank038.nblockscript.data.TempLocationData;
import com.blank038.nblockscript.listener.PlayerListener;
import com.blank038.nblockscript.script.ScriptManager;
import com.blank038.nblockscript.task.LocationTask;

import java.util.Map;
import java.util.UUID;

/**
 * @author Blank038
 */
public class NBlockScript extends PluginBase {
    private static ScriptManager scriptManager;
    private static NBlockScript main;
    private static NBlockScriptAPI api;
    private LocationTask locationTask;

    public static NBlockScript getInstance() {
        return main;
    }

    public static NBlockScriptAPI getApi() {
        return api;
    }

    public static ScriptManager getScriptManager() {
        return scriptManager;
    }

    @Override
    public void onEnable() {
        main = this;
        scriptManager = new ScriptManager(this);
        api = new NBlockScriptAPI(this);
        loadConfig();
        // 创建线程
        locationTask = new LocationTask();
        getServer().getCommandMap().register("NBlockScript", new NBSCommand(this));
        getServer().getScheduler().scheduleDelayedRepeatingTask(this, locationTask, 20 * 60, 20 * 60);
        getServer().getScheduler().scheduleDelayedRepeatingTask(this, () -> {
            for (Map.Entry<UUID, Player> entry : NBlockScript.getInstance().getServer().getOnlinePlayers().entrySet()) {
                TempLocationData data = getApi().getTempLocationData(entry.getValue());
                if (data != null && !data.isLock()) {
                    getApi().checkPlayer(entry.getValue(), data.getLocation());
                }
            }
        }, 6, 6);
        getServer().getScheduler().scheduleDelayedRepeatingTask(this, () -> scriptManager.saveAll(),
                20 * getConfig().getInt("save-time"), 20 * getConfig().getInt("save-time"));
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        scriptManager.saveAll();
    }

    // 获取线程
    public LocationTask getLocationTask() {
        return locationTask;
    }

    // 载入配置
    public long loadConfig() {
        long start = System.currentTimeMillis();
        if (getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        saveDefaultConfig();
        reloadConfig();
        // 开始载入脚本
        scriptManager.init();
        long end = System.currentTimeMillis();
        return end - start;
    }
}