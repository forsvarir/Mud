package com.forsvarir.mud.communications;

import com.forsvarir.mud.CommandProcessor;
import com.forsvarir.mud.communications.messages.CommandMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.logging.Logger;

@Controller
public class CommandController {
    Logger logger = Logger.getLogger(CommandController.class.getName());

    @Autowired
    private CommandProcessor commandProcessor;

    @MessageMapping("/command")
    public void command(CommandMessage commandMessage,
                        Principal principal,
                        @Header("simpSessionId") String sessionId) {
        logger.info(String.format("MESSAGE(%s): %s ", sessionId, commandMessage.getCommand()));
        commandProcessor.processCommand(commandMessage.getCommand(), principal.getName(), sessionId);
    }
}
