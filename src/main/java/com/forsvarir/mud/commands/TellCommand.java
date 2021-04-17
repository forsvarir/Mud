package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.SessionManager;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class TellCommand implements MudCommand {
    private final SessionManager sessionManager;
    private final MessageSender messageSender;

    public TellCommand(MessageSender messageSender, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.messageSender = messageSender;
    }

    @Override
    public String getCommand() {
        return "tell";
    }

    public void processCommand(String arguments, Player sender) {
        var playerName = arguments.substring(0, arguments.indexOf(" "));
        var targetPlayer = sessionManager.findPlayer(playerName);

        var message = arguments.substring(arguments.indexOf(" ") + 1);
        messageSender.sendToUser(sender.getName() + " tells you \"" + message + "\"", targetPlayer.getPrincipal(), targetPlayer.getSessionId());
        messageSender.sendToUser("You tell " + targetPlayer.getName() + " \"" + message + "\"", sender.getPrincipal(), sender.getSessionId());
    }
}
