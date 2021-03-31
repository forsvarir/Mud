package com.forsvarir.mud.communications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class CommandController {
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/command")
    public void command(String message) {
        messagingTemplate.convertAndSend("/ws/responses",
                message.replace("command", "response"));
    }
}
