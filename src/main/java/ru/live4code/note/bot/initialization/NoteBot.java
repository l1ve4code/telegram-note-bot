package ru.live4code.note.bot.initialization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.handlers.callback.CallbackHandler;
import ru.live4code.note.bot.handlers.message.MessageHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class NoteBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.name}")
    private String botName;

    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;

    @Autowired
    public NoteBot(
            @Value("${telegram.bot.token}") String botToken,
            MessageHandler messageHandler,
            CallbackHandler callbackHandler
    ) {
        super(botToken);
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasCallbackQuery()) {
            callbackHandler.performCallback(update);
        }
        else if (update.hasMessage()) {
            messageHandler.performMessage(update);
        }

    }

    @Override
    public String getBotUsername() {
        return botName;
    }

}
