package ru.live4code.note.bot.handlers.message.command.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.handlers.message.command.Command;
import ru.live4code.note.bot.handlers.message.command.CommandName;
import ru.live4code.note.bot.model.Note;
import ru.live4code.note.bot.service.MessageSenderService;
import ru.live4code.note.bot.dao.NoteDao;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotesCommand implements Command {

    private final static String DESCRIPTION = """
            Here your recent added notes 👀
            """;

    private final MessageSenderService messageSenderService;
    private final NoteDao noteDao;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        List<Note> userNote = noteDao.getNotes(chatId);
        String formattedNotes = userNote.stream().map(Note::toString).collect(Collectors.joining("\n"));
        String message = String.format("%s\n%s", DESCRIPTION, formattedNotes);
        messageSenderService.sendMessage(chatId.toString(), message);
    }

    @Override
    public CommandName getCommand() {
        return CommandName.NOTES;
    }

}
