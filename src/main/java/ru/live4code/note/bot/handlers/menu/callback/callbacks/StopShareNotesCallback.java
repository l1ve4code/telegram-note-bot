package ru.live4code.note.bot.handlers.menu.callback.callbacks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.constant.ReplyKeyboardTemplates;
import ru.live4code.note.bot.dao.ShareDao;
import ru.live4code.note.bot.dao.StateDao;
import ru.live4code.note.bot.dao.UserDao;
import ru.live4code.note.bot.handlers.menu.callback.Callback;
import ru.live4code.note.bot.handlers.menu.callback.CallbackAnswer;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.model.State;
import ru.live4code.note.bot.service.MessageSenderService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StopShareNotesCallback implements Callback, CallbackAnswer {

    private final UserDao userDao;
    private final StateDao stateDao;
    private final ShareDao shareDao;
    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        String userName = callbackQuery.getFrom().getUserName();
        List<String> myShares = shareDao.getMyShares(userName);

        stateDao.setUserState(chatId, State.STOP_SHARE_USERNAME_WAITING);
        messageSenderService.sendMessageWithReplyKeyboard(chatId, "Select username:", ReplyKeyboardTemplates.makeKeyboardFromList(myShares));
    }

    @Override
    public void processCallbackAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();
        String userName = update.getMessage().getFrom().getUserName();

        stateDao.deleteUserState(chatId);

        if (!userDao.checkUserExists(message)) {
            messageSenderService.sendMessage(chatId, String.format("I don't know this user: '%s' \uD83E\uDD14", message));
            return;
        }

        if (!shareDao.checkShareExists(userName, message)) {
            messageSenderService.sendMessage(chatId, String.format("You not sharing anything to this user: '%s' \uD83D\uDE05", message));
            return;
        }

        shareDao.deleteShare(userName, message);
        messageSenderService.sendMessage(chatId, String.format("Share was successfully disabled with user: '%s' \uD83E\uDD19", message));
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.STOP_SHARE_NOTES;
    }

    @Override
    public State getState() {
        return State.STOP_SHARE_USERNAME_WAITING;
    }

}
