package ru.live4code.note.bot.handlers.message.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {

    void execute(Update update);
    CommandName getCommand();

}
