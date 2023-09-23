package ru.live4code.note.bot.constant;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.live4code.note.bot.handlers.callback.CallbackType;

import java.util.List;

public class InlineKeyboardTemplates {

    public static InlineKeyboardMarkup getMenu() {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        makeButton("Create note ✍️", CallbackType.CREATE_NOTE.getCallbackType()),
                        makeButton("Delete note \uD83D\uDDD1", CallbackType.DELETE_NOTE.getCallbackType()),
                        makeButton("Show notes 👀", CallbackType.SHOW_NOTE.getCallbackType())
                ))
                .build();
    }

    public static InlineKeyboardMarkup getReturnMenu() {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        makeButton("<-", CallbackType.RETURN_TO_MENU.getCallbackType())
                ))
                .build();
    }

    private static InlineKeyboardButton makeButton(String text, String callbackData) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackData)
                .build();
    }

}
