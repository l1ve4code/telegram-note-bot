package ru.live4code.note.bot.constant;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class ReplyKeyboardTemplates {

    public static ReplyKeyboardMarkup makeKeyboardFromList(List<String> values) {
        return ReplyKeyboardMarkup.builder()
                .keyboard(values.stream().map(value -> {
                    var row = new KeyboardRow();
                    row.add(value);
                    return row;
                }).toList())
                .oneTimeKeyboard(true)
                .build();
    }

}
