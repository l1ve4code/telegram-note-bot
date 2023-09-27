package ru.live4code.note.bot.handlers.menu.callback.callbacks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.dao.NoteDao;
import ru.live4code.note.bot.dao.StateDao;
import ru.live4code.note.bot.handlers.menu.callback.Callback;
import ru.live4code.note.bot.handlers.menu.callback.CallbackAnswer;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.model.State;
import ru.live4code.note.bot.service.MessageSenderService;

@Component
@RequiredArgsConstructor
public class CreateNoteCallback implements Callback, CallbackAnswer {

    private final StateDao stateDao;
    private final NoteDao noteDao;
    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();

        stateDao.setUserState(chatId, State.NOTE_WAITING);
        messageSenderService.sendMessage(chatId, "Write your note below:");
    }

    @Override
    public void processCallbackAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();

        noteDao.addNote(chatId, message);
        stateDao.deleteUserState(chatId);
        messageSenderService.sendMessage(chatId, String.format("Note: '%s' was added \uD83C\uDF8A", message));
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.CREATE_NOTE;
    }

    @Override
    public State getState() {
        return State.NOTE_WAITING;
    }
}
