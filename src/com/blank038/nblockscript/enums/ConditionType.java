package com.blank038.nblockscript.enums;

public enum ConditionType {
    PERMISSION("permission"),
    TAKEMONEY("take-money"),
    CHECKMONEY("check-money");


    private String key;

    ConditionType(String a) {
        this.key = a;
    }

    public String getKey() {
        return key;
    }
}