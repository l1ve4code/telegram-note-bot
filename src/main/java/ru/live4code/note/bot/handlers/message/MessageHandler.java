package ru.live4code.note.bot.handlers.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.handlers.message.command.Command;
import ru.live4code.note.bot.handlers.message.command.CommandName;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessageHandler {

    private final static String COMMAND_PREFIX = "/";

    private final Map<CommandName, Command> commands;

    public void performMessage(Update update) {
        String message = update.getMessage().getText();

        if (message.startsWith(COMMAND_PREFIX)) {
            String passedCommand = message.split(" ")[0].toLowerCase();
            CommandName command = CommandName.fromString(passedCommand);
            commands.get(command).execute(update);
        }
    }

}
