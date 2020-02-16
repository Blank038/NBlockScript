package com.blank038.nblockscript.data;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import com.blank038.nblockscript.NBlockScript;
import com.blank038.nblockscript.enums.EnumType;

public class BindData {
    private Location location;
    private String script, level;
    private EnumType type;
    private boolean error;
    private String key;
    private double x, y, z;

    public BindData(String key, ConfigSection section) {
        type = EnumType.get(section.getString("type"));
        script = section.getString("script");
        this.key = key;
        loadLocation(section);
    }

    public BindData(String key, Location loc, EnumType enumType, String script) {
        this.type = enumType;
        this.script = script;
        this.key = key;
        loadLocation(loc);
    }

    public void loadLocation(ConfigSection section) {
        Level level = NBlockScript.getInstance().getServer().getLevelByName(section.getString("location.world"));
        if (level == null) {
            error();
            return;
        }
        Location location = new Location(section.getDouble("location.x"), section.getDouble("location.y"),
                section.getDouble("location.z"), level);
        loadLocation(location);
    }

    public void loadLocation(Location location) {
        this.location = type.equals(EnumType.WALK) ? location.clone().add(0, 1, 0) : location.clone();
        this.level = location.getLevel().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public void check(Player player, Location location) {
        if (location == null) return;
        int distance = (int) location.distance(this.location);
        if (!error && distance == 0) {
            if (type.equals(EnumType.INTERACT)) NBlockScript.getScriptManager().ignoreTrigger(player, script);
            else NBlockScript.getScriptManager().trigger(player, script);
        }
    }

    public void error() {
        error = true;
        NBlockScript.getInstance().getLogger().info("载入 " + key + " 出现错误, 请前往 bind.yml 检查设置!");
    }

    public String getScriptName() {
        return script;
    }

    public void setConfig(Config config) {
        ConfigSection section = new ConfigSection();
        section.set("location.world", level);
        section.set("location.x", x);
        section.set("location.y", y);
        section.set("location.z", z);
        section.set("type", type.name());
        section.set("script", script);
        config.set(key, section);
    }
}