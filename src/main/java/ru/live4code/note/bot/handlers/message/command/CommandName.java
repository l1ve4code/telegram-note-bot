package ru.live4code.note.bot.handlers.message.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommandName {

    START("/start"),
    NOTE("/note"),
    NOTES("/notes"),
    MENU("/menu"),
    HELP("/help"),
    UNKNOWN("/unknown"),
    STOP("/stop");

    private final String commandName;

    public static CommandName fromString(String text) {
        for (CommandName s : CommandName.values()) {
            if (s.commandName.equalsIgnoreCase(text)) {
                return s;
            }
        }
        return CommandName.UNKNOWN;
    }

}
