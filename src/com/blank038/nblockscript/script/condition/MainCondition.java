package com.blank038.nblockscript.script.condition;

import cn.nukkit.Player;
import com.blank038.nblockscript.enums.ConditionType;

public abstract class MainCondition {

    public abstract boolean allow(Player player);

    public abstract void execute(Player player);

    public abstract ConditionType getConditionType();
}