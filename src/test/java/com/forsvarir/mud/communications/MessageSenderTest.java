package com.forsvarir.mud.communications;

import com.forsvarir.mud.communications.messages.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageSenderTest {

    @Mock
    SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    MessageSender messageSender;

    @Test
    void sendToAll_sendsToAllUsersEndpoint() {
        ResponseMessage message = new ResponseMessage("Some Message");

        messageSender.sendToAll(message);

        verify(messagingTemplate).convertAndSend(WebSocketConfig.ALL_USER_ENDPOINT, message);
    }
}