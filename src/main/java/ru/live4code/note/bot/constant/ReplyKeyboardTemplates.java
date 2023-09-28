package ru.live4code.note.bot.constant;

import org.apache.commons.collections4.ListUtils;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class ReplyKeyboardTemplates {

    public static ReplyKeyboardMarkup makeKeyboardFromList(List<String> values) {
        var partitionedValues = ListUtils.partition(values, 3);
        var keyboardRows = partitionedValues.stream().map(items -> {
            var row = new KeyboardRow();
            items.forEach(row::add);
            return row;
        }).toList();
        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .oneTimeKeyboard(true)
                .build();
    }

}
