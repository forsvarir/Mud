package com.forsvarir.mud;

import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class CommandProcessor {
    private final SessionManager sessionManager;
    private final MessageSender messageSender;
    private final CommandTokenizer commandTokenizer;

    public CommandProcessor(SessionManager sessionManager, MessageSender messageSender, CommandTokenizer commandTokenizer) {
        this.sessionManager = sessionManager;
        this.messageSender = messageSender;
        this.commandTokenizer = commandTokenizer;
    }

    public void processCommand(String command, String principalName, String sessionId) {
        var tokens = commandTokenizer.extractTokens(command);
        if (tokens.getCommand().startsWith("shout")) {
            processShoutCommand(tokens, sessionManager.findPlayer(principalName, sessionId));
        } else if (tokens.getCommand().startsWith("tell")) {
            processTellCommand(tokens, sessionManager.findPlayer(principalName, sessionId));
        } else {
            messageSender.sendToUser("Huh?", principalName, sessionId);
        }
    }

    private void processShoutCommand(CommandTokens commandTokens, Player sendingPlayer) {
        var message = commandTokens.getArguments();
        messageSender.sendToAll(sendingPlayer.getName() + " shouts \"" + message + "\"");
    }

    private void processTellCommand(CommandTokens command, Player sender) {
        var playerName = command.getArguments().substring(0, command.getArguments().indexOf(" "));
        var targetPlayer = sessionManager.findPlayer(playerName);

        var message = command.getArguments().substring(command.getArguments().indexOf(" ") + 1);
        messageSender.sendToUser(sender.getName() + " tells you \"" + message + "\"", targetPlayer.getPrincipal(), targetPlayer.getSessionId());
        messageSender.sendToUser("You tell " + targetPlayer.getName() + " \"" + message + "\"", sender.getPrincipal(), sender.getSessionId());
    }
}
