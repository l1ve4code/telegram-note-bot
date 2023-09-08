package ru.live4code.note.bot.handlers.message.command.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.handlers.message.command.Command;
import ru.live4code.note.bot.handlers.message.command.CommandName;
import ru.live4code.note.bot.service.MessageSenderService;
import ru.live4code.note.bot.dao.NoteDao;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoteCommand implements Command {

    private final static String DESCRIPTION = """
            What is your note 🤔
            
            Try to use <b>/note 'here-your-text'</b>.
            
            Good luck! ☘️
            """;

    private final MessageSenderService messageSenderService;
    private final NoteDao noteDao;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        String message = update.getMessage().getText();
        String note = message.replace(getCommand().getCommandName(), "").trim();

        if (note.isEmpty()) {
            messageSenderService.sendMessage(chatId.toString(), DESCRIPTION);
            return;
        }

        noteDao.addNote(chatId, note);
        messageSenderService.sendMessage(chatId.toString(), String.format("Note: '%s' was added \uD83C\uDF8A", note));
    }

    @Override
    public CommandName getCommand() {
        return CommandName.NOTE;
    }

}
