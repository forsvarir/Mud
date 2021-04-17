package com.forsvarir.mud;

import com.forsvarir.mud.commands.MudCommand;
import com.forsvarir.mud.commands.UnknownCommand;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommandProcessor {
    private final SessionManager sessionManager;
    private final MessageSender messageSender;
    private final CommandTokenizer commandTokenizer;
    private final UnknownCommand unknownCommand;
    private final Map<String, MudCommand> commands;

    public CommandProcessor(SessionManager sessionManager,
                            MessageSender messageSender,
                            CommandTokenizer commandTokenizer,
                            UnknownCommand unknownCommand,
                            Map<String, MudCommand> commands) {
        this.sessionManager = sessionManager;
        this.messageSender = messageSender;
        this.commandTokenizer = commandTokenizer;
        this.unknownCommand = unknownCommand;
        this.commands = commands;
    }

    public void processCommand(String command, String principalName, String sessionId) {
        var tokens = commandTokenizer.extractTokens(command);
        var commandProcessor = commands.getOrDefault(tokens.getCommand() + "Command", unknownCommand);
        var sender = sessionManager.findPlayer(principalName, sessionId);
        commandProcessor.processCommand(tokens.getArguments(), sender);
    }
}
