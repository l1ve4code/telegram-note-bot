package ru.live4code.note.bot.constant;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.model.MenuType;

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
                                makeListButton("Note action's", CallbackType.SHOW_NOTE_ACTIONS),
                                makeListButton("Notification action's", CallbackType.SHOW_NOTIFICATION_ACTIONS),
                                makeListButton("Share action's", CallbackType.SHOW_SHARE_ACTIONS)
                        )
                )
                .build();
    }

    public static InlineKeyboardMarkup getNoteActions() {
        return InlineKeyboardMarkup.builder()
                .keyboard(
                        List.of(
                                makeListButton("Create note \uD83D\uDD8B", CallbackType.CREATE_NOTE),
                                makeListButton("Show my notes \uD83D\uDC40", CallbackType.SHOW_NOTE),
                                makeListButton("Delete note \uD83D\uDDD1", CallbackType.DELETE_NOTE),
                                makeListButton("<-", formatReturnMenu(MenuType.MAIN))
                        )
                )
                .build();
    }

    public static InlineKeyboardMarkup getNotificationActions() {
        return InlineKeyboardMarkup.builder()
                .keyboard(
                        List.of(
                                makeListButton("Create notification note \uD83D\uDCA1", CallbackType.SHOW_NOTIFICATION_DATE),
                                makeListButton("Show my notifications \uD83D\uDEA8", CallbackType.SHOW_NOTIFICATION),
                                makeListButton("Delete notification \uD83D\uDDD1", CallbackType.DELETE_NOTIFICATION),
                                makeListButton("<-", formatReturnMenu(MenuType.MAIN))
                        )
                )
                .build();
    }

    public static InlineKeyboardMarkup getShareActions() {
        return InlineKeyboardMarkup.builder()
                .keyboard(
                        List.of(
                                makeListButton("Share notes \uD83D\uDC65", CallbackType.SHARE_NOTES),
                                makeListButton("Show user notes \uD83D\uDC40", CallbackType.SHOW_USER_NOTES),
                                makeListButton("Stop share notes \uD83D\uDED1", CallbackType.STOP_SHARE_NOTES),
                                makeListButton("<-", formatReturnMenu(MenuType.MAIN))
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

    public static InlineKeyboardMarkup getReturnMenu(MenuType menuType) {
        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(
                        makeButton("<-", formatReturnMenu(menuType))
                ))
                .build();
    }

    private static List<List<InlineKeyboardButton>> makeDateButtons(LocalDate date) {

        var keyboardButtons = new ArrayList<List<InlineKeyboardButton>>();

        LocalDate dateToModify = date.withDayOfMonth(1);

        var month = date.getMonth();
        int days = month.length(date.isLeapYear());
        int firstWeekDay = date.getDayOfWeek().getValue() - 1;

        int rows = ((days + firstWeekDay) % 7 > 0 ? 1 : 0) + (days + firstWeekDay) / 7;

        for (int i = 0; i < rows; i++) {

            var keyboardRow = new ArrayList<InlineKeyboardButton>();
            int dayOfMonth = dateToModify.getDayOfMonth();

            for (int j = 0; j < firstWeekDay; j++) {
                keyboardRow.add(makeUnknownButton(" "));
            }

            for (int j = firstWeekDay; j < 7; j++) {
                if (dayOfMonth <= days && dateToModify.getMonth().equals(date.getMonth())) {
                    keyboardRow.add(
                            makeButton(
                                    String.valueOf(dateToModify.getDayOfMonth()),
                                    formatDate(dateToModify, CallbackType.SELECT_NOTIFICATION_DATE)
                            )
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

    private static String formatReturnMenu(MenuType menuType) {
        return String.format("%s:%s", CallbackType.RETURN_TO_MENU.getCallbackType(), menuType.getMenuType());
    }

    private static InlineKeyboardButton makeUnknownButton(String text) {
        return makeButton(text, CallbackType.UNKNOWN);
    }

    private static List<InlineKeyboardButton> makeListButton(String text, CallbackType callbackType) {
        return List.of(makeButton(text, callbackType));
    }

    private static List<InlineKeyboardButton> makeListButton(String text, String callbackType) {
        return List.of(makeButton(text, callbackType));
    }

    private static InlineKeyboardButton makeButton(String text, String callbackType) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackType)
                .build();
    }

    private static InlineKeyboardButton makeButton(String text, CallbackType callbackType) {
        return makeButton(text, callbackType.getCallbackType());
    }

}
