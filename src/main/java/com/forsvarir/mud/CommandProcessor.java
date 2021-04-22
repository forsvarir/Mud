package com.forsvarir.mud;

import com.forsvarir.mud.commands.MudCommand;
import com.forsvarir.mud.commands.UnknownCommand;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommandProcessor {
    private final CommandTokenizer commandTokenizer;
    private final UnknownCommand unknownCommand;
    private final Map<String, MudCommand> commands;

    public CommandProcessor(CommandTokenizer commandTokenizer,
                            UnknownCommand unknownCommand,
                            Map<String, MudCommand> commands) {
        this.commandTokenizer = commandTokenizer;
        this.unknownCommand = unknownCommand;
        this.commands = commands;
    }

    public void processCommand(String command, Player sender) {
        var tokens = commandTokenizer.extractTokens(command);
        var commandProcessor = commands.getOrDefault(calculateCommandBeanName(tokens.getCommand()),
                unknownCommand);
        commandProcessor.processCommand(tokens.getArguments(), sender);
    }

    private String calculateCommandBeanName(String command) {
        return command + "Command";
    }
}
