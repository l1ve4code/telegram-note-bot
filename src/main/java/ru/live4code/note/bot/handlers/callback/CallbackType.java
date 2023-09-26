package ru.live4code.note.bot.handlers.callback;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CallbackType {

    CREATE_NOTE("=create-note"),
    DELETE_NOTE("=delete-note"),
    RETURN_TO_MENU("=return-to-menu"),
    SHOW_NOTE("=show-note"),
    SHARE_NOTES("=share-notes"),
    STOP_SHARE_NOTES("=stop-share-notes");

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
