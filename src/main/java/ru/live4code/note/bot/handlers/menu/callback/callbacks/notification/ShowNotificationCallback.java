package ru.live4code.note.bot.handlers.menu.callback.callbacks.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.constant.InlineKeyboardTemplates;
import ru.live4code.note.bot.constant.MessageTemplates;
import ru.live4code.note.bot.dao.NotificationDao;
import ru.live4code.note.bot.handlers.menu.callback.Callback;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.model.MenuType;
import ru.live4code.note.bot.model.Notification;
import ru.live4code.note.bot.service.MessageSenderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShowNotificationCallback implements Callback {

    private final NotificationDao notificationDao;
    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        LocalDateTime now = LocalDateTime.now();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        List<Notification> userNotifications = notificationDao.getActualUserNotifications(chatId, now);
        String formattedNotifications = userNotifications.stream().map(Notification::toString).collect(Collectors.joining("\n"));
        String message = String.format("%s\n%s", MessageTemplates.NOTIFICATIONS_TEMPLATE, formattedNotifications);
        messageSenderService.editDefaultImageKeyboard(
                chatId,
                messageId,
                userNotifications.isEmpty() ? "You didn't create notifications \uD83D\uDE31" : message,
                InlineKeyboardTemplates.getReturnMenu(MenuType.NOTIFICATIONS)
        );
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.SHOW_NOTIFICATION;
    }

}
