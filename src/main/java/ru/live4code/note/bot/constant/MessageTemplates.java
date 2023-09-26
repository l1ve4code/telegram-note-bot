package ru.live4code.note.bot.constant;

public class MessageTemplates {

    public final static String HELP_TEMPLATE = """
            Hi! I know this commands 😲
            
            <i>/start -> to run me</i> ☺️
            <i>/note 'your-text' -> to store your note</i> ✍️
            <i>/notes -> to get your recent added notes</i> 📔
            <i>/menu -> to open menu</i> 🗂
            <i>/help -> to get available commands</i> 🆘
            """;

    public final static String NOTE_TEMPLATE = """
            What is your note 🤔
            
            Try to use <b>/note 'here-your-text'</b>.
            
            Good luck! ☘️
            """;

    public final static String NOTES_TEMPLATE = """
            Here your recent added notes 👀
            """;

    public final static String SHARED_NOTES_TEMPLATE = """
            Here users shared notes 👀
            """;

    public final static String START_TEMPLATE = """
            Hello ✋
            I'm note bot! 🤖
            I can store your notes and share it with other people.
            
            Use <b>/help</b> to get main commands.
            """;

    public final static String UNKNOWN_TEMPLATE = """
            🤔I don't know this command 🤔
            Try use <b>/help</b> to get main commands.
            """;

}
