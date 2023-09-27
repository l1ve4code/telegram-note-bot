package ru.live4code.note.bot.initialization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.dao.StateDao;
import ru.live4code.note.bot.handlers.menu.CallbackHandler;
import ru.live4code.note.bot.handlers.message.MessageHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoteBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.name}")
    private String botName;

    private final StateDao stateDao;
    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;

    @Autowired
    public NoteBot(
            @Value("${telegram.bot.token}") String botToken,
            StateDao stateDao,
            MessageHandler messageHandler,
            CallbackHandler callbackHandler
    ) {
        super(botToken);
        this.stateDao = stateDao;
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()) {
            callbackHandler.performCallback(update);
        }

        if (update.hasMessage()) {

            Long chatId = update.getMessage().getChatId();

            if (stateDao.isUserWithState(chatId)) {
                callbackHandler.performCallbackAnswer(update);
            } else {
                messageHandler.performMessage(update);
            }

        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

}
