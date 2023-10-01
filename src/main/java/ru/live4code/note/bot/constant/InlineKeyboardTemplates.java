package ru.live4code.note.bot.constant;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardTemplates {

    public static InlineKeyboardMarkup getMenu() {
        return InlineKeyboardMarkup.builder()
                .keyboard(
                        List.of(
                                makeListButton("Create note \uD83D\uDD8B", CallbackType.CREATE_NOTE.getCallbackType()),
                                makeListButton("Create notification note \uD83D\uDCA1", CallbackType.SHOW_NOTIFICATION_DATE.getCallbackType()),
                                makeListButton("Delete note \uD83D\uDDD1", CallbackType.DELETE_NOTE.getCallbackType()),
                                makeListButton("Show my notes \uD83D\uDC40", CallbackType.SHOW_NOTE.getCallbackType()),
                                makeListButton("Show user notes \uD83D\uDC68\u200D\uD83D\uDC68\u200D\uD83D\uDC66\u200D\uD83D\uDC66", CallbackType.SHOW_USER_NOTES.getCallbackType()),
                                makeListButton("Share notes \uD83D\uDC65", CallbackType.SHARE_NOTES.getCallbackType()),
                                makeListButton("Stop share notes \uD83D\uDED1", CallbackType.STOP_SHARE_NOTES.getCallbackType())
                        )
                )
                .build();
    }

    public static InlineKeyboardMarkup getDateSelectMenu(LocalDate date) {
        var dateButtons = makeDateButtons(date);
        var dateTitle = String.format("%s %s", date.getMonth(), date.getYear());
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        makeUnknownButton(dateTitle)
                ))
                .keyboardRow(List.of(
                        makeUnknownButton("Mon"), makeUnknownButton("Tue"), makeUnknownButton("Wed"),
                        makeUnknownButton("Thu"), makeUnknownButton("Fri"), makeUnknownButton("Sat"),
                        makeUnknownButton("Sun")
                ))
                .keyboard(dateButtons)
                .keyboardRow(List.of(
                        makeButton("←", formatDate(date.minusMonths(1L), CallbackType.NEW_NOTIFICATION_DATE)),
                        makeUnknownButton(" "),
                        makeButton("→", formatDate(date.plusMonths(1L), CallbackType.NEW_NOTIFICATION_DATE))
                ))
                .build();
    }

    public static InlineKeyboardMarkup getTimeSelectMenu(LocalTime time) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        makeButton("↑", formatTime(time.plusHours(1L), CallbackType.NEW_NOTIFICATION_TIME)),
                        makeButton("↑", formatTime(time.plusMinutes(1L), CallbackType.NEW_NOTIFICATION_TIME))
                ))
                .keyboardRow(List.of(
                        makeUnknownButton(String.valueOf(time.getHour())),
                        makeUnknownButton(String.valueOf(time.getMinute()))
                ))
                .keyboardRow(List.of(
                        makeButton("↓", formatTime(time.minusHours(1L), CallbackType.NEW_NOTIFICATION_TIME)),
                        makeButton("↓", formatTime(time.minusMinutes(1L), CallbackType.NEW_NOTIFICATION_TIME))
                ))
                .keyboardRow(List.of(
                        makeButton("OK", formatTime(time, CallbackType.SELECT_NOTIFICATION_TIME))
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

    private static List<List<InlineKeyboardButton>> makeDateButtons(LocalDate date) {

        var keyboardButtons = new ArrayList<List<InlineKeyboardButton>>();

        var month = date.getMonth();
        int days = month.length(date.isLeapYear());
        int firstWeekDay = date.getDayOfWeek().getValue() - 1;

        int rows = ((days + firstWeekDay) % 7 > 0 ? 1 : 0) + (days + firstWeekDay) / 7;

        LocalDate dateToModify = date;
        for (int i = 0; i < rows; i++) {

            var keyboardRow = new ArrayList<InlineKeyboardButton>();
            int dayOfMonth = dateToModify.getDayOfMonth();

            for (int j = 0; j < firstWeekDay; j++) {
                keyboardRow.add(makeUnknownButton(" "));
            }

            for (int j = firstWeekDay; j < 7; j++) {
                if (dayOfMonth <= days && dateToModify.getMonth().equals(date.getMonth())) {
                    keyboardRow.add(
                            makeButton(String.valueOf(dateToModify.getDayOfMonth()), formatDate(dateToModify, CallbackType.SELECT_NOTIFICATION_DATE))
                    );
                    dateToModify = dateToModify.plusDays(1);
                } else {
                    keyboardRow.add(makeUnknownButton(" "));
                }
            }

            keyboardButtons.add(keyboardRow);
            firstWeekDay = 0;
        }

        return keyboardButtons;
    }

    private static String formatDate(LocalDate date, CallbackType callbackType) {
        return String.format("%s:%s", callbackType.getCallbackType(), date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    private static String formatTime(LocalTime time, CallbackType callbackType) {
        return String.format("%s:%s", callbackType.getCallbackType(), time.format(DateTimeFormatter.ofPattern("HH mm")));
    }

    private static InlineKeyboardButton makeUnknownButton(String text) {
        return makeButton(text, CallbackType.UNKNOWN.getCallbackType());
    }

    private static List<InlineKeyboardButton> makeListButton(String text, String callbackData) {
        return List.of(makeButton(text, callbackData));
    }

    private static InlineKeyboardButton makeButton(String text, String callbackData) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackData)
                .build();
    }

}
