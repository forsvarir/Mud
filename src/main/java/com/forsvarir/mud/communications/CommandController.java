package com.forsvarir.mud.communications;

import com.forsvarir.mud.communications.messages.CommandMessage;
import com.forsvarir.mud.communications.messages.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class CommandController {
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/command")
    public void command(CommandMessage commandMessage) {
        var response = new ResponseMessage(commandMessage.getCommand());

        messagingTemplate.convertAndSend("/ws/responses", response);
    }
}
