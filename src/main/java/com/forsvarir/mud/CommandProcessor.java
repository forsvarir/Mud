package com.forsvarir.mud;

import com.forsvarir.mud.communications.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandProcessor {
    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private MessageSender messageSender;

    public void processCommand(String command, String principalName, String sessionId) {
        if (command.startsWith("shout")) {
            processShoutCommand(command, sessionManager.findPlayer(principalName, sessionId));
        } else if (command.startsWith("tell")) {
            processTellCommand(command, sessionManager.findPlayer(principalName, sessionId));
        } else {
            messageSender.sendToUser("Huh?", principalName, sessionId);
        }
    }

    private void processShoutCommand(String command, Player sendingPlayer) {
        var message = command.substring(command.indexOf(" ") + 1);
        messageSender.sendToAll(sendingPlayer.getName() + " shouts \"" + message + "\"");
    }

    private void processTellCommand(String command, Player sender) {
        var inter = command.replace("tell ", "");
        var playerName = inter.substring(0, inter.indexOf(" "));
        var targetPlayer = sessionManager.findPlayer(playerName);

        var message = inter.substring(inter.indexOf(" ") + 1);
        messageSender.sendToUser(sender.getName() + " tells you \"" + message + "\"", targetPlayer.getPrincipal(), targetPlayer.getSessionId());
        messageSender.sendToUser("You tell " + targetPlayer.getName() + " \"" + message + "\"", sender.getPrincipal(), sender.getSessionId());
    }
}
