package com.forsvarir.mud.communications;

import com.forsvarir.mud.communications.messages.ResponseMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {
    private final SimpMessagingTemplate messagingTemplate;

    MessageSender(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendToAll(String message) {
        messagingTemplate.convertAndSend(WebSocketConfig.ALL_USER_ENDPOINT, new ResponseMessage(message, ""));
    }

    public void sendToUser(String message, String principalName, String sessionId) {
        messagingTemplate.convertAndSendToUser(principalName, WebSocketConfig.USER_ENDPOINT, new ResponseMessage(message, sessionId));
    }
}
