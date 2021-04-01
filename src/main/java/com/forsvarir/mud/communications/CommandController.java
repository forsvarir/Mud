package com.forsvarir.mud.communications;

import com.forsvarir.mud.CommandProcessor;
import com.forsvarir.mud.communications.messages.CommandMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CommandController {
    @Autowired
    private CommandProcessor commandProcessor;

    @MessageMapping("/command")
    public void command(CommandMessage commandMessage) {
        commandProcessor.processCommand(commandMessage.getCommand());
    }
}
