package com.forsvarir.mud;

import com.forsvarir.mud.communications.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandProcessor {
    @Autowired
    private MessageSender messageSender;

    public void processCommand(String command, String principalName, String sessionId) {
        if (command.equals(("global"))) {
            messageSender.sendToAll("A global echo was requested");
        } else {
            messageSender.sendToUser(command, principalName, sessionId);
        }
    }
}
