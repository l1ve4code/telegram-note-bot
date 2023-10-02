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
public class ShareNotesCallback implements Callback, CallbackAnswer {

    private final UserDao userDao;
    private final ShareDao shareDao;
    private final StateDao stateDao;
    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        String userName = callbackQuery.getFrom().getUserName();
        List<String> userNames = shareDao.getUsersToShare(userName);

        if (userNames.isEmpty()) {
            messageSenderService.sendMessage(chatId, "No one to share notes with \uD83E\uDD14");
            return;
        }

        stateDao.setUserState(chatId, State.SHARE_USERNAME_WAITING);
        messageSenderService.sendMessageWithReplyKeyboard(chatId, "Select username:", ReplyKeyboardTemplates.makeKeyboardFromList(userNames));
    }

    @Override
    public void processCallbackAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();
        String userName = update.getMessage().getFrom().getUserName();

        stateDao.deleteUserState(chatId);

        if (!userDao.checkUserExists(message)) {
            messageSenderService.sendDeleteReplyMessage(chatId, String.format("I don't know this user: '%s' \uD83E\uDD14", message));
            return;
        }

        shareDao.insertShare(userName, message);
        messageSenderService.sendDeleteReplyMessage(chatId, String.format("Notes was successfully shared with user: '%s' \uD83E\uDD19", message));
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.SHARE_NOTES;
    }

    @Override
    public State getState() {
        return State.SHARE_USERNAME_WAITING;
    }

}
