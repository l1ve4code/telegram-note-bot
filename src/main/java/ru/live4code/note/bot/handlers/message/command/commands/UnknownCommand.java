package ru.live4code.note.bot.handlers.message.command.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.handlers.message.command.Command;
import ru.live4code.note.bot.handlers.message.command.CommandName;
import ru.live4code.note.bot.service.MessageSenderService;

@Component
@RequiredArgsConstructor
public class UnknownCommand implements Command {

    private final static String DESCRIPTION = """
            🤔I don't know this command 🤔
            Try use <b>/help</b> to get main commands.
            """;

    private final MessageSenderService messageSenderService;

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        messageSenderService.sendMessage(chatId, DESCRIPTION);
    }

    @Override
    public CommandName getCommand() {
        return CommandName.UNKNOWN;
    }

}
