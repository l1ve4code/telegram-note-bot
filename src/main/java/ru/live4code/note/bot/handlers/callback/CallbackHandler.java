package ru.live4code.note.bot.handlers.callback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.constant.InlineKeyboardTemplates;
import ru.live4code.note.bot.constant.MessageTemplates;
import ru.live4code.note.bot.dao.NoteDao;
import ru.live4code.note.bot.dao.StateDao;
import ru.live4code.note.bot.model.Note;
import ru.live4code.note.bot.model.State;
import ru.live4code.note.bot.service.MessageSenderService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CallbackHandler {

    private final NoteDao noteDao;
    private final StateDao stateDao;
    private final MessageSenderService messageSenderService;

    public void performCallback(Update update) {

        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        CallbackType callback = CallbackType.fromString(callbackQuery.getData());

        if (callback == null) {
            messageSenderService.sendMessage(chatId, "Wow! I don't know this action 🤔");
        }

        if (CallbackType.CREATE_NOTE.equals(callback)) {
            stateDao.setUserState(chatId, State.NOTE_WAITING);
            messageSenderService.sendMessage(chatId, "Write your note below:");
        }
        else if (CallbackType.DELETE_NOTE.equals(callback)) {
            stateDao.setUserState(chatId, State.DELETE_NOTE_WAITING);
            messageSenderService.sendMessage(chatId, "Write note id below:");
        }
        else if (CallbackType.SHOW_NOTE.equals(callback)) {
            List<Note> userNote = noteDao.getNotes(chatId);
            String formattedNotes = userNote.stream().map(Note::toString).collect(Collectors.joining("\n"));
            String message = String.format("%s\n%s", MessageTemplates.NOTES_TEMPLATE, formattedNotes);
            messageSenderService.editDefaultImageKeyboard(
                    chatId, messageId, message, InlineKeyboardTemplates.getReturnMenu()
            );
        }
        else if (CallbackType.RETURN_TO_MENU.equals(callback)) {
            messageSenderService.editDefaultImageKeyboard(chatId, messageId, InlineKeyboardTemplates.getMenu());
        }

    }

}
