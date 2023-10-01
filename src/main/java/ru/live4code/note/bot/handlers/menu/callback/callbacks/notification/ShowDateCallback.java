package ru.live4code.note.bot.handlers.menu.callback.callbacks.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.constant.InlineKeyboardTemplates;
import ru.live4code.note.bot.handlers.menu.callback.Callback;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.service.MessageSenderService;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ShowDateCallback implements Callback {

    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();

        messageSenderService.sendImageKeyboard(chatId, InlineKeyboardTemplates.getDateSelectMenu(LocalDate.now()));
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.SHOW_NOTIFICATION_DATE;
    }

}
