package ru.live4code.note.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Slf4j
@Component
public class MessageSenderService extends DefaultAbsSender {

    protected MessageSenderService(@Value("${telegram.bot.token}") String botToken) {
        super(new DefaultBotOptions(), botToken);
    }

    public void sendDefaultImageKeyboard(Long chatId, ReplyKeyboard keyboard) {
        SendPhoto message = new SendPhoto();
        Resource resource = new ClassPathResource("images/keyboard-title.png");

        try {
            message.setPhoto(new InputFile(resource.getInputStream(), "keyboard-title.png"));
        } catch (IOException exception) {
            log.error("Error while getting image: %s", exception);
        }

        message.setChatId(chatId);
        message.setReplyMarkup(keyboard);

        try {
            execute(message);
        } catch (TelegramApiException exception){
            log.error("Error while sending message to user: %s", exception);
        }

    }

    public void sendDeleteMessage(Long chatId, Integer messageId) {
        DeleteMessage message = new DeleteMessage();
        message.setChatId(chatId);
        message.setMessageId(messageId);

        try {
            execute(message);
        } catch (TelegramApiException exception){
            log.error("Error while sending message to user: %s", exception);
        }

    }

    public void sendMessage(Long chatId, String text) {
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
