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
        if (command.equals(("global"))) {
            messageSender.sendToAll("A global echo was requested");
        } else if (command.startsWith("tell")) {
            processTellCommand(command);
        } else {
            messageSender.sendToUser(command, principalName, sessionId);
        }
    }

    private void processTellCommand(String command) {
        var inter = command.replace("tell ", "");
        var playerName = inter.substring(0, inter.indexOf(" "));
        var targetPlayer = sessionManager.findPlayer(playerName);

        var textStart = inter.indexOf("\"") + 1;
        var textEnd = inter.lastIndexOf("\"");

        var message = inter.substring(textStart, textEnd);
        messageSender.sendToUser(message, targetPlayer.getPrincipal(), targetPlayer.getSessionId());
    }
}
