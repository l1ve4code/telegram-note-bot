package ru.live4code.note.bot.handlers.message.command.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.constant.InlineKeyboardTemplates;
import ru.live4code.note.bot.handlers.message.command.Command;
import ru.live4code.note.bot.handlers.message.command.CommandName;
import ru.live4code.note.bot.service.MessageSenderService;

@Component
@RequiredArgsConstructor
public class MenuCommand implements Command {

    private final MessageSenderService messageSenderService;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        messageSenderService.sendImageKeyboard(chatId, InlineKeyboardTemplates.getMenu());
    }

    @Override
    public CommandName getCommand() {
        return CommandName.MENU;
    }
}
