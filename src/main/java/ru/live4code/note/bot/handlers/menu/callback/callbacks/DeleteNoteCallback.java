package ru.live4code.note.bot.handlers.menu.callback.callbacks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.constant.ReplyKeyboardTemplates;
import ru.live4code.note.bot.dao.NoteDao;
import ru.live4code.note.bot.dao.StateDao;
import ru.live4code.note.bot.handlers.menu.callback.Callback;
import ru.live4code.note.bot.handlers.menu.callback.CallbackAnswer;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.model.State;
import ru.live4code.note.bot.service.MessageSenderService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeleteNoteCallback implements Callback, CallbackAnswer {

    private final NoteDao noteDao;
    private final StateDao stateDao;
    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        List<String> noteIds = noteDao.getNotes(chatId).stream().map(item -> String.valueOf(item.id())).toList();

        stateDao.setUserState(chatId, State.DELETE_NOTE_WAITING);
        messageSenderService.sendMessageWithReplyKeyboard(chatId, "Select note-id:", ReplyKeyboardTemplates.makeKeyboardFromList(noteIds));
    }

    @Override
    public void processCallbackAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();

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

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.DELETE_NOTE;
    }

    @Override
    public State getState() {
        return State.DELETE_NOTE_WAITING;
    }

}
