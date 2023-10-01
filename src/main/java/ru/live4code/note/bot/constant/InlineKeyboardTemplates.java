package ru.live4code.note.bot.constant;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;

import java.util.List;

public class InlineKeyboardTemplates {

    public static InlineKeyboardMarkup getMenu() {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        makeButton("Create note \uD83D\uDD8B", CallbackType.CREATE_NOTE.getCallbackType())
                ))
//                .keyboardRow(List.of(
//                        makeButton("Create notification note \uD83D\uDCA1", CallbackType.CREATE_NOTIFICATION.getCallbackType())
//                ))
                .keyboardRow(List.of(
                        makeButton("Delete note \uD83D\uDDD1", CallbackType.DELETE_NOTE.getCallbackType())
                ))
                .keyboardRow(List.of(
                        makeButton("Show my notes \uD83D\uDC40", CallbackType.SHOW_NOTE.getCallbackType())
                ))
                .keyboardRow(List.of(
                        makeButton("Show user notes \uD83D\uDC68\u200D\uD83D\uDC68\u200D\uD83D\uDC66\u200D\uD83D\uDC66", CallbackType.SHOW_USER_NOTES.getCallbackType())
                ))
                .keyboardRow(List.of(
                        makeButton("Share notes \uD83D\uDC65", CallbackType.SHARE_NOTES.getCallbackType())
                ))
                .keyboardRow(List.of(
                        makeButton("Stop share notes \uD83D\uDED1", CallbackType.STOP_SHARE_NOTES.getCallbackType())
                ))
                .build();
    }

    public static InlineKeyboardMarkup getTimeSelectMenu(int hour, int minute) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        makeButton("↑", formatTime(hour + 1, minute)),
                        makeButton("↑", formatTime(hour, minute + 1))
                ))
                .keyboardRow(List.of(
                        makeButton(String.valueOf(hour), CallbackType.UNKNOWN.getCallbackType()),
                        makeButton(String.valueOf(minute), CallbackType.UNKNOWN.getCallbackType())
                ))
                .keyboardRow(List.of(
                        makeButton("↓", formatTime(hour - 1, minute)),
                        makeButton("↓", formatTime(hour, minute - 1))
                ))
                .keyboardRow(List.of(
                        makeButton("OK", CallbackType.CREATE_NOTE.getCallbackType())
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

    private static String formatTime(int hour, int minute) {
        return String.format("%s:%sT%s", CallbackType.NEW_NOTIFICATION_TIME.getCallbackType(), hour, minute);
    }

    private static InlineKeyboardButton makeButton(String text, String callbackData) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackData)
                .build();
    }

}
