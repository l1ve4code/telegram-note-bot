package ru.live4code.note.bot.handlers.menu.callback;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CallbackType {

    CREATE_NOTE("=create-note"),
    DELETE_NOTE("=delete-note"),
    RETURN_TO_MENU("=return-to-menu"),
    SHOW_NOTE("=show-note"),
    SHOW_USER_NOTES("=show-user-notes"),
    SHARE_NOTES("=share-notes"),
    STOP_SHARE_NOTES("=stop-share-notes"),
    NEW_NOTIFICATION_TIME("=new-notification-time"),
    NEW_NOTIFICATION_DATE("=new-notification-date"),
    SELECT_NOTIFICATION_TIME("=select-notification-time"),
    SELECT_NOTIFICATION_DATE("=select-notification-date"),
    SHOW_NOTIFICATION_DATE("=show-notification-date"),
    UNKNOWN("=unknown");

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
