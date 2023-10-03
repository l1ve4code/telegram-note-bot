package ru.live4code.note.bot.model;

import java.time.LocalDateTime;

public record Notification(long id, LocalDateTime dateTime, String notification) {
    @Override
    public String toString() {
        return String.format("[%s][%s] %s", id, dateTime, notification);
    }
}
