package com.blank038.nblockscript.script.condition;

import cn.nukkit.Player;
import com.blank038.nblockscript.enums.ConditionType;

public class PermissionCondition extends MainCondition {
    private String permission;

    public PermissionCondition(String permission) {
        this.permission = permission;
    }

    @Override
    public boolean allow(Player player) {
        return player.hasPermission(permission);
    }

    @Override
    public void execute(Player player) {
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.PERMISSION;
    }
}
