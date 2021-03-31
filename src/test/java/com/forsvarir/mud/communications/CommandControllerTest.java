package com.forsvarir.mud.communications;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommandControllerTest {

    @Mock
    SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    CommandController commandController;

    @Test
    void command_sendsResponseWithSameContent() {
        commandController.command("{\"command\":\"hello\"}");

        verify(messagingTemplate).convertAndSend("/ws/responses", "{\"response\":\"hello\"}");
    }

}