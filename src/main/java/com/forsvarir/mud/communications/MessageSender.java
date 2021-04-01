package com.forsvarir.mud.communications;

import com.forsvarir.mud.communications.messages.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendToAll(String message) {
        messagingTemplate.convertAndSend(WebSocketConfig.ALL_USER_ENDPOINT, new ResponseMessage(message));
    }
}
