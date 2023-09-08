package ru.live4code.note.bot.model;

public record Note(Long id, String note) {
    @Override
    public String toString() {
        return String.format("[%s] %s", id, note);
    }
}
