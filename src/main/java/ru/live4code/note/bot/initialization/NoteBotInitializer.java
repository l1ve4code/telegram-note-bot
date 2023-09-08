package ru.live4code.note.bot.initialization;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class NoteBotInitializer {

    private final NoteBot noteBot;

    @EventListener({ContextRefreshedEvent.class})
    private void initialize() {
        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(noteBot);
        } catch (TelegramApiException exception) {
            log.error("Error while telegram bot initialization: %s", exception);
        }
    }

}
