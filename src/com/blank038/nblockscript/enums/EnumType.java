package com.blank038.nblockscript.enums;

public enum EnumType {
    WALK,
    BREAK,
    INTERACT;

    public static EnumType get(String type) {
        for (EnumType enumType : values()) {
            if (type.startsWith(enumType.name())) {
                return enumType;
            }
        }
        return INTERACT;
    }
}