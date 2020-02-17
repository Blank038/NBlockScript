package com.blank038.nblockscript.script.condition;

import cn.nukkit.Player;
import com.blank038.nblockscript.bridge.EconomyBridge;
import com.blank038.nblockscript.enums.ConditionType;

public class TakeMoneyCondition extends MainCondition {
    private double amount;

    public TakeMoneyCondition(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean allow(Player player) {
        return EconomyBridge.balance(player) >= amount;
    }

    @Override
    public void execute(Player player) {
        EconomyBridge.take(player, amount);
    }

    @Override
    public ConditionType getConditionType() {
        return ConditionType.TAKEMONEY;
    }
}
