package ru.live4code.note.bot.model;

public record NotificationToSend(long id, long chatId, String notification) {
}
