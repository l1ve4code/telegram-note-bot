package ru.live4code.note.bot.handlers.menu.callback.callbacks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.constant.InlineKeyboardTemplates;
import ru.live4code.note.bot.constant.MessageTemplates;
import ru.live4code.note.bot.dao.NoteDao;
import ru.live4code.note.bot.handlers.menu.callback.Callback;
import ru.live4code.note.bot.handlers.menu.callback.CallbackType;
import ru.live4code.note.bot.model.MenuType;
import ru.live4code.note.bot.model.Note;
import ru.live4code.note.bot.service.MessageSenderService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShowNoteCallback implements Callback {

    private final NoteDao noteDao;
    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        List<Note> userNote = noteDao.getNotes(chatId);
        String formattedNotes = userNote.stream().map(Note::toString).collect(Collectors.joining("\n"));
        String message = String.format("%s\n%s", MessageTemplates.NOTES_TEMPLATE, formattedNotes);
        messageSenderService.editDefaultImageKeyboard(
                chatId,
                messageId,
                userNote.isEmpty() ? "It's empty \uD83D\uDE31" : message,
                InlineKeyboardTemplates.getReturnMenu(MenuType.NOTES)
        );
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.SHOW_NOTE;
    }

}
