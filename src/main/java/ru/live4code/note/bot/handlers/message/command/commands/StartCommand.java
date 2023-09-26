package ru.live4code.note.bot.handlers.message.command.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.live4code.note.bot.constant.MessageTemplates;
import ru.live4code.note.bot.dao.UserDao;
import ru.live4code.note.bot.handlers.message.command.Command;
import ru.live4code.note.bot.handlers.message.command.CommandName;
import ru.live4code.note.bot.service.MessageSenderService;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final UserDao userDao;
    private final MessageSenderService messageSenderService;

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getFrom().getUserName();

        userDao.insertUser(userName, chatId);
        messageSenderService.sendMessage(chatId, MessageTemplates.START_TEMPLATE);
    }

    @Override
    public CommandName getCommand() {
        return CommandName.START;
    }

}
