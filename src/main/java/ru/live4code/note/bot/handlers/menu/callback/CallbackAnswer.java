package ru.live4code.note.bot.handlers.menu.callback;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.model.State;

public interface CallbackAnswer {
    void processCallbackAnswer(Update update);
    State getState();
}
