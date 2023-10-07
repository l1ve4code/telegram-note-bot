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
import ru.live4code.note.bot.model.SharedNote;
import ru.live4code.note.bot.service.MessageSenderService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShowUserNotesCallback implements Callback {

    private final NoteDao noteDao;
    private final MessageSenderService messageSenderService;

    @Override
    public void processCallback(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getMessage().getChatId();
        String userName = callbackQuery.getFrom().getUserName();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        List<SharedNote> sharedNotes = noteDao.getSharedNotes(userName);
        String formattedNotes = sharedNotes.stream().map(SharedNote::toString).collect(Collectors.joining("\n"));
        String message = String.format("%s\n%s", MessageTemplates.SHARED_NOTES_TEMPLATE, formattedNotes);
        messageSenderService.editDefaultImageKeyboard(
                chatId,
                messageId,
                sharedNotes.isEmpty() ? "No one shares notes with you \uD83D\uDE31" : message,
                InlineKeyboardTemplates.getReturnMenu(MenuType.SHARES)
        );
    }

    @Override
    public CallbackType getCallbackType() {
        return CallbackType.SHOW_USER_NOTES;
    }

}
