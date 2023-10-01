package ru.live4code.note.bot.handlers.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.dao.StateDao;
import ru.live4code.note.bot.handlers.menu.callback.Callback;
import ru.live4code.note.bot.handlers.menu.callback.CallbackAnswer;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.model.State;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CallbackHandler {

    private final StateDao stateDao;
    private final Map<CallbackType, Callback> callbacks;
    private final Map<State, CallbackAnswer> callbackAnswers;

    public void performCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String callback = callbackQuery.getData();
        String callbackMessage = callback.split(":")[0];
        CallbackType callbackType = CallbackType.fromString(callbackMessage);
        callbacks.get(callbackType).processCallback(update);
    }

    public void performCallbackAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        State state = stateDao.getUserState(chatId);
        callbackAnswers.get(state).processCallbackAnswer(update);
    }

}
