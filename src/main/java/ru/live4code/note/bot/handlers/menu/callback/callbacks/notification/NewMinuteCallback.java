package ru.live4code.note.bot.handlers.menu.callback.callbacks.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.constant.InlineKeyboardTemplates;
import ru.live4code.note.bot.handlers.menu.callback.Callback;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.service.MessageSenderService;

@Component
@RequiredArgsConstructor
public class NewMinuteCallback implements Callback {

    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        Long chatId = callbackQuery.getMessage().getChatId();

        String params = callbackQuery.getData().split(":")[1];
        int hour = Integer.parseInt(params.split("T")[0]);
        int minute = Integer.parseInt(params.split("T")[1]);

        messageSenderService.editDefaultImageKeyboard(chatId, messageId, InlineKeyboardTemplates.getTimeSelectMenu(hour, minute));
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.NEW_NOTIFICATION_TIME;
    }

}
