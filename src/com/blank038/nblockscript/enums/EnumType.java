package com.blank038.nblockscript.enums;

public enum EnumType {
    WALK,
    INTERACT;

    public static EnumType get(String type) {
        if (type.startsWith("WALK")) {
            return WALK;
        } else {
            return INTERACT;
        }
    }
}