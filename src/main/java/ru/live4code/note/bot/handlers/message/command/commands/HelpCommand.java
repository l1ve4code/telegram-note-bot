package ru.live4code.note.bot.handlers.message.command.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.handlers.message.command.Command;
import ru.live4code.note.bot.handlers.message.command.CommandName;
import ru.live4code.note.bot.service.MessageSenderService;

@Component
@RequiredArgsConstructor
public class HelpCommand implements Command {

    private final static String DESCRIPTION = """
            Hi! I know this commands 😲
            
            <i>/start -> to run me</i> ☺️
            <i>/note 'your-text' -> to store your note</i> ✍️
            <i>/notes -> to get your recent added notes</i> 📔
            <i>/help -> to get available commands</i> 🆘
            """;

    private final MessageSenderService messageSenderService;

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        messageSenderService.sendMessage(chatId, DESCRIPTION);
    }

    @Override
    public CommandName getCommand() {
        return CommandName.HELP;
    }

}
