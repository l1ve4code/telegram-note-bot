package ru.live4code.note.bot.handlers.callback;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.dao.NoteDao;
import ru.live4code.note.bot.dao.ShareDao;
import ru.live4code.note.bot.dao.StateDao;
import ru.live4code.note.bot.dao.UserDao;
import ru.live4code.note.bot.model.State;
import ru.live4code.note.bot.service.MessageSenderService;

@Component
@RequiredArgsConstructor
public class CallbackAnswerHandler {

    private final MessageSenderService messageSenderService;
    private final ShareDao shareDao;
    private final StateDao stateDao;
    private final NoteDao noteDao;
    private final UserDao userDao;

    public void performCallbackAnswer(Update update) {

        Long chatId = update.getMessage().getChatId();
        String message = update.getMessage().getText();
        String userName = update.getMessage().getFrom().getUserName();

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
        else if (State.SHARE_USERNAME_WAITING.equals(userState)) {

            stateDao.deleteUserState(chatId);

            if (!userDao.checkUserExists(message)) {
                messageSenderService.sendMessage(chatId, String.format("I don't know this user: '%s' \uD83E\uDD14", message));
                return;
            }

            shareDao.insertShare(userName, message);
            messageSenderService.sendMessage(chatId, String.format("Notes was successfully shared with user: '%s' \uD83E\uDD19", message));
        }
        else if (State.STOP_SHARE_USERNAME_WAITING.equals(userState)) {

            stateDao.deleteUserState(chatId);

            if (!userDao.checkUserExists(message)) {
                messageSenderService.sendMessage(chatId, String.format("I don't know this user: '%s' \uD83E\uDD14", message));
                return;
            }

            if (!shareDao.checkShareExists(userName, message)) {
                messageSenderService.sendMessage(chatId, String.format("You not sharing anything to this user: '%s' \uD83D\uDE05", message));
                return;
            }

            shareDao.deleteShare(userName, message);
            messageSenderService.sendMessage(chatId, String.format("Share was successfully disabled with user: '%s' \uD83E\uDD19", message));
        }

    }

}
