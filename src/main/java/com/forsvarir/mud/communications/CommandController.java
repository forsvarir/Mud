package com.forsvarir.mud.communications;

import com.forsvarir.mud.CommandProcessor;
import com.forsvarir.mud.SessionManager;
import com.forsvarir.mud.communications.messages.CommandMessage;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class CommandController {
    private final CommandProcessor commandProcessor;
    private final SessionManager sessionManager;

    CommandController(CommandProcessor commandProcessor, SessionManager sessionManager) {
        this.commandProcessor = commandProcessor;
        this.sessionManager = sessionManager;
    }

    @MessageMapping("/command")
    public void command(CommandMessage commandMessage,
                        Principal principal,
                        @Header("simpSessionId") String sessionId) {
        var sender = sessionManager.findPlayer(principal.getName(), sessionId);
        commandProcessor.processCommand(commandMessage.getCommand(), sender);
    }
}
