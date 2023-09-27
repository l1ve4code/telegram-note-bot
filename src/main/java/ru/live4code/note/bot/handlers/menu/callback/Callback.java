package ru.live4code.note.bot.handlers.menu.callback;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Callback {
    void processCallback(Update update);
    CallbackType getCallbackType();
}
