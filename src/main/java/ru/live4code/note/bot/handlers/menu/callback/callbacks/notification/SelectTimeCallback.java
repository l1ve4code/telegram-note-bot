package ru.live4code.note.bot.handlers.menu.callback.callbacks.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.dao.StateDao;
import ru.live4code.note.bot.handlers.menu.callback.Callback;
import ru.live4code.note.bot.handlers.menu.callback.CallbackAnswer;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.model.State;
import ru.live4code.note.bot.service.MessageSenderService;

@Component
@RequiredArgsConstructor
public class SelectTimeCallback implements Callback, CallbackAnswer {

    private final StateDao stateDao;
    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();

        String params = callbackQuery.getData().split(":")[1];
        params = params.replace(" ", ":");

        stateDao.setUserState(chatId, State.NOTIFICATION_NOTE_WAITING);

        messageSenderService.sendMessage(chatId, String.format("You selected '%s' time \uD83D\uDD51", params));
        messageSenderService.sendMessage(chatId, "Write your notification note below \uD83D\uDCD3:");
    }

    @Override
    public void processCallbackAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();

        stateDao.deleteUserState(chatId);
        messageSenderService.sendMessage(chatId, String.format("Notification with text '%s', was created! \uD83C\uDF8A", message));
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.SELECT_NOTIFICATION_TIME;
    }

    @Override
    public State getState() {
        return State.NOTIFICATION_NOTE_WAITING;
    }
}
