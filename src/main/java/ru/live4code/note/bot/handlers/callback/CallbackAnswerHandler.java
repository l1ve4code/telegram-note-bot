package ru.live4code.note.bot.handlers.callback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.dao.NoteDao;
import ru.live4code.note.bot.dao.StateDao;
import ru.live4code.note.bot.model.State;
import ru.live4code.note.bot.service.MessageSenderService;

@Component
@RequiredArgsConstructor
public class CallbackAnswerHandler {

    private final MessageSenderService messageSenderService;
    private final StateDao stateDao;
    private final NoteDao noteDao;

    public void performCallbackAnswer(Update update) {

        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();

        State userState = stateDao.getUserState(chatId);

        if (State.NOTE_WAITING.equals(userState)) {
            noteDao.addNote(chatId, message);
            stateDao.deleteUserState(chatId);
            messageSenderService.sendMessage(chatId, String.format("Note: '%s' was added \uD83C\uDF8A", message));
        }
        else if (State.DELETE_NOTE_WAITING.equals(userState)) {
            long noteId;
            try {
                noteId = Long.parseLong(message);
            } catch (NumberFormatException exception) {
                messageSenderService.sendMessage(chatId, "Please write only number \uD83E\uDD7A");
                return;
            }
            noteDao.deleteNote(chatId, noteId);
            stateDao.deleteUserState(chatId);
            messageSenderService.sendMessage(chatId, String.format("Note with id: '%s', was deleted \uD83C\uDF8A", message));
        }

    }

}
