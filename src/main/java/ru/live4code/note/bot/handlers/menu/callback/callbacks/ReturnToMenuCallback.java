package ru.live4code.note.bot.handlers.menu.callback.callbacks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.constant.InlineKeyboardTemplates;
import ru.live4code.note.bot.handlers.menu.callback.Callback;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.model.MenuType;
import ru.live4code.note.bot.service.MessageSenderService;

@Component
@RequiredArgsConstructor
public class ReturnToMenuCallback implements Callback {

    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        String params = callbackQuery.getData().split(":")[1];
        MenuType menuType = MenuType.fromString(params);

        if (menuType == null) {
            messageSenderService.sendMessage(chatId, "Hmmm.... I don't know this action \uD83E\uDD14");
            return;
        }

        var menuKeyboard = switch (menuType) {
            case MAIN -> InlineKeyboardTemplates.getMenu();
            case NOTES -> InlineKeyboardTemplates.getNoteActions();
            case NOTIFICATIONS -> InlineKeyboardTemplates.getNotificationActions();
            case SHARES -> InlineKeyboardTemplates.getShareActions();
        };

        messageSenderService.editDefaultImageKeyboard(chatId, messageId, menuKeyboard);
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.RETURN_TO_MENU;
    }

}
