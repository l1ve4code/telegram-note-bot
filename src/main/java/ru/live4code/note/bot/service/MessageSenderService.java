package ru.live4code.note.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class MessageSenderService extends DefaultAbsSender {

    protected MessageSenderService(@Value("${telegram.bot.token}") String botToken) {
        super(new DefaultBotOptions(), botToken);
    }

    public void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableHtml(true);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException exception){
            log.error("Error while sending message to user: %s", exception);
        }

    }

}
