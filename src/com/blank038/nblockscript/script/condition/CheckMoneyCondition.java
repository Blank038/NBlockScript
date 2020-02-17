package com.blank038.nblockscript.script.condition;

import cn.nukkit.Player;
import com.blank038.nblockscript.bridge.EconomyBridge;
import com.blank038.nblockscript.enums.ConditionType;

public class CheckMoneyCondition extends MainCondition {
    private double amount;

    public CheckMoneyCondition(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean allow(Player player) {
        return EconomyBridge.balance(player) >= amount;
    }

    @Override
    public void execute(Player player) {
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.CHECKMONEY;
    }
}
