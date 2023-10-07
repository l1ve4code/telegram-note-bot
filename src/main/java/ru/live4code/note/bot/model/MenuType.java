package ru.live4code.note.bot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuType {

    MAIN("main"),
    NOTES("notes"),
    NOTIFICATIONS("notifications"),
    SHARES("shares");

    private final String menuType;

    public static MenuType fromString(String text) {
        for (MenuType m : MenuType.values()) {
            if (m.menuType.equalsIgnoreCase(text)) {
                return m;
            }
        }
        return null;
    }

}
