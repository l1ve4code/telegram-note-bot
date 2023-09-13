package ru.live4code.note.bot.handlers.callback;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CallbackType {

    CREATE_NOTE("=create-note"),
    DELETE_NOTE("=delete-note"),
    SHOW_NOTE("=show-note");

    private final String callbackType;

    public static CallbackType fromString(String text) {
        for (CallbackType s : CallbackType.values()) {
            if (s.callbackType.equalsIgnoreCase(text)) {
                return s;
            }
        }
        return null;
    }

}