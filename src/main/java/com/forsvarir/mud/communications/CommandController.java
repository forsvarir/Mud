package com.forsvarir.mud.communications;

import com.forsvarir.mud.CommandProcessor;
import com.forsvarir.mud.communications.messages.CommandMessage;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class CommandController {
    private final CommandProcessor commandProcessor;

    CommandController(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @MessageMapping("/command")
    public void command(CommandMessage commandMessage,
                        Principal principal,
                        @Header("simpSessionId") String sessionId) {
        commandProcessor.processCommand(commandMessage.getCommand(), principal.getName(), sessionId);
    }
}
