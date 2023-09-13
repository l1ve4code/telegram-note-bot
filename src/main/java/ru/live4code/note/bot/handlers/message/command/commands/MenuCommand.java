package ru.live4code.note.bot.handlers.message.command.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.live4code.note.bot.handlers.callback.CallbackType;
import ru.live4code.note.bot.handlers.message.command.Command;
import ru.live4code.note.bot.handlers.message.command.CommandName;
import ru.live4code.note.bot.service.MessageSenderService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MenuCommand implements Command {

    private final MessageSenderService messageSenderService;

    @Override
    public void execute(Update update) {

        Long chatId = update.getMessage().getChatId();

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        var keyboardButtons = List.of(
                makeButton("Create note ✍️", CallbackType.CREATE_NOTE.getCallbackType()),
                makeButton("Delete note \uD83D\uDDD1", CallbackType.DELETE_NOTE.getCallbackType()),
                makeButton("Show notes 👀", CallbackType.SHOW_NOTE.getCallbackType())
        );

        keyboardMarkup.setKeyboard(List.of(keyboardButtons));

        messageSenderService.sendDefaultImageKeyboard(chatId, keyboardMarkup);

    }

    private InlineKeyboardButton makeButton(String text, String callbackData) {

        var button = new InlineKeyboardButton();

        button.setText(text);
        button.setCallbackData(callbackData);

        return button;
    }

    @Override
    public CommandName getCommand() {
        return CommandName.MENU;
    }
}
