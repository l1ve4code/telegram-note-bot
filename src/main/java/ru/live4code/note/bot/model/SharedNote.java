package ru.live4code.note.bot.model;

public record SharedNote(String userName, String note) {

    @Override
    public String toString() {
        return String.format("[%s] %s", userName, note);
    }

}
