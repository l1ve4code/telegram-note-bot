package ru.live4code.note.bot.handlers.menu.callback.callbacks.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.constant.ReplyKeyboardTemplates;
import ru.live4code.note.bot.dao.NotificationDao;
import ru.live4code.note.bot.dao.StateDao;
import ru.live4code.note.bot.handlers.menu.callback.Callback;
import ru.live4code.note.bot.handlers.menu.callback.CallbackAnswer;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.model.State;
import ru.live4code.note.bot.service.MessageSenderService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DeleteNotificationCallback implements Callback, CallbackAnswer {

    private final StateDao stateDao;
    private final NotificationDao notificationDao;
    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        LocalDateTime now = LocalDateTime.now();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        List<String> notificationIds = notificationDao.getActualUserNotifications(chatId, now).stream()
                .map(item -> String.valueOf(item.id()))
                .toList();

        if (notificationIds.isEmpty()) {
            messageSenderService.sendMessage(chatId, "You don't have notifications \uD83E\uDD7A");
            return;
        }

        stateDao.setUserState(chatId, State.DELETE_NOTIFICATION_WAITING);
        messageSenderService.sendMessageWithReplyKeyboard(chatId, "Select notification-id:", ReplyKeyboardTemplates.makeKeyboardFromList(notificationIds));
    }

    @Override
    public void processCallbackAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();

        long notificationId;
        try {
            notificationId = Long.parseLong(message);
        } catch (NumberFormatException exception) {
            messageSenderService.sendMessage(chatId, "Please use only number \uD83E\uDD7A");
            return;
        }

        if (!notificationDao.isNotificationExists(chatId, notificationId)) {
            messageSenderService.sendMessage(chatId, "I don't know this notification \uD83E\uDD7A");
            return;
        }

        notificationDao.deleteNotification(chatId, notificationId);
        stateDao.deleteUserState(chatId);
        messageSenderService.sendDeleteReplyMessage(chatId, String.format("Notification with id: '%s', was deleted \uD83C\uDF8A", message));
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.DELETE_NOTIFICATION;
    }

    @Override
    public State getState() {
        return State.DELETE_NOTIFICATION_WAITING;
    }
}
