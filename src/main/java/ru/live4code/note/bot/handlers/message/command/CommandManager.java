package ru.live4code.note.bot.handlers.message.command;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandManager {

    @Resource
    private final List<Command> existingCommands;

    @Bean
    public Map<CommandName, Command> initializeCommands() {
        var commandMap = new HashMap<CommandName, Command>();
        existingCommands.forEach(item -> {
            commandMap.put(item.getCommand(), item);
        });
        return commandMap;
    }

}