package ru.live4code.note.bot.handlers.menu.callback.callbacks.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.constant.InlineKeyboardTemplates;
import ru.live4code.note.bot.dao.NotificationDao;
import ru.live4code.note.bot.handlers.menu.callback.Callback;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.service.MessageSenderService;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class SelectDateCallback implements Callback {

    private final NotificationDao notificationDao;
    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        Long notificationId = notificationDao.getIdLastNotFilledNotification(chatId);

        String params = callbackQuery.getData().split(":")[1];
        LocalDate date = LocalDate.parse(params);

        notificationDao.fillNotificationDate(notificationId, date);

        messageSenderService.editDefaultImageKeyboard(chatId, messageId, InlineKeyboardTemplates.getTimeSelectMenu(LocalTime.now()));
        messageSenderService.sendMessage(chatId, String.format("You selected '%s' date \uD83D\uDCC5", params));
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.SELECT_NOTIFICATION_DATE;
    }

}
