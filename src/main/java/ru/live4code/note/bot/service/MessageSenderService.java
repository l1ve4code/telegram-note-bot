package ru.live4code.note.bot.service;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Slf4j
@Component
public class MessageSenderService extends DefaultAbsSender {

    private final static String DEFAULT_IMAGE = "images/keyboard-title.png";

    protected MessageSenderService(@Value("${telegram.bot.token}") String botToken) {
        super(new DefaultBotOptions(), botToken);
    }

    public void editDefaultImageKeyboard(Long chatId, Integer messageId, String text, InlineKeyboardMarkup keyboard) {
        sendEditMenu(chatId, messageId, text, keyboard);
    }

    public void editDefaultImageKeyboard(Long chatId, Integer messageId, InlineKeyboardMarkup keyboard) {
        sendEditMenu(chatId, messageId, null, keyboard);
    }

    public void sendImageKeyboard(Long chatId, ReplyKeyboard keyboard) {

        SendPhoto message = new SendPhoto();
        Resource resource = new ClassPathResource(DEFAULT_IMAGE);

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

    public void sendMessage(Long chatId, String text) {

        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.enableHtml(true);

        try {
            execute(message);
        } catch (TelegramApiException exception){
            log.error("Error while sending message to user: %s", exception);
        }

    }

    private void sendEditMenu(Long chatId, Integer messageId, @Nullable String text, InlineKeyboardMarkup keyboard) {

        var caption = new EditMessageCaption();
        caption.setChatId(chatId);
        caption.setMessageId(messageId);

        if (text != null) {
            caption.setCaption(text);
        }

        var markup = EditMessageReplyMarkup.builder()
                .chatId(chatId)
                .messageId(messageId)
                .replyMarkup(keyboard)
                .build();

        try {
            execute(caption);
            execute(markup);
        } catch (TelegramApiException exception){
            log.error("Error while editing message to user: %s", exception);
        }

    }

}
