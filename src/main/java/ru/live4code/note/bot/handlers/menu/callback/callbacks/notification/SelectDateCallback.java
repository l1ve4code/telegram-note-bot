package ru.live4code.note.bot.handlers.menu.callback.callbacks.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.constant.InlineKeyboardTemplates;
import ru.live4code.note.bot.handlers.menu.callback.Callback;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.service.MessageSenderService;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class SelectDateCallback implements Callback {

    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();

        String params = callbackQuery.getData().split(":")[1];

        messageSenderService.sendMessage(chatId, String.format("You selected '%s' date \uD83D\uDCC5", params));
        messageSenderService.sendImageKeyboard(chatId, InlineKeyboardTemplates.getTimeSelectMenu(LocalTime.now()));
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.SELECT_NOTIFICATION_DATE;
    }

}
