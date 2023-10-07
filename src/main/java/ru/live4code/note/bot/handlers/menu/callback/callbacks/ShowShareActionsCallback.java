package ru.live4code.note.bot.handlers.menu.callback.callbacks;

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
public class ShowShareActionsCallback implements Callback {

    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        messageSenderService.editDefaultImageKeyboard(chatId, messageId, InlineKeyboardTemplates.getShareActions());
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.SHOW_SHARE_ACTIONS;
    }

}
