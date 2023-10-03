package ru.live4code.note.bot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum State {

    NOTE_WAITING("NOTE_WAITING"),
    DELETE_NOTE_WAITING("DELETE_NOTE_WAITING"),
    SHARE_USERNAME_WAITING("SHARE_USERNAME_WAITING"),
    STOP_SHARE_USERNAME_WAITING("STOP_SHARE_USERNAME_WAITING"),
    NOTIFICATION_NOTE_WAITING("NOTIFICATION_NOTE_WAITING"),
    DELETE_NOTIFICATION_WAITING("DELETE_NOTIFICATION_WAITING");

    private final String stateName;

    public static State fromString(String text) {
        for (State s : State.values()) {
            if (s.stateName.equalsIgnoreCase(text)) {
                return s;
            }
        }
        return null;
    }

}
