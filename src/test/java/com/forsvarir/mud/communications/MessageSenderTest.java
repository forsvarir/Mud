package com.forsvarir.mud.communications;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.communications.messages.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageSenderTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private MessageSender messageSender;

    @Test
    void sendToAll_sendsToAllUsersEndpoint() {
        messageSender.sendToAll("Some Message");

        ArgumentCaptor<ResponseMessage> responseCaptor = ArgumentCaptor.forClass(ResponseMessage.class);
        verify(messagingTemplate).convertAndSend(eq(WebSocketConfig.ALL_USER_ENDPOINT), responseCaptor.capture());
        assertThat(responseCaptor.getValue().getResponse()).isEqualTo("Some Message");
    }

    @Test
    void sendToUser_sendsToUserWithSessionEncoded() {
        messageSender.sendToUser("Some Message", "A User", "A Session");

        ArgumentCaptor<ResponseMessage> responseCaptor = ArgumentCaptor.forClass(ResponseMessage.class);
        verify(messagingTemplate).convertAndSendToUser(eq("A User"),
                eq(WebSocketConfig.USER_ENDPOINT),
                responseCaptor.capture());
        assertThat(responseCaptor.getValue().getResponse()).isEqualTo("Some Message");
        assertThat(responseCaptor.getValue().getSessionId()).isEqualTo("A Session");
    }

    @Test
    void sendToPlayer_sendsToPlayerWithSessionEncoded() {
        var player = new Player("A Player", "A Principal", "A Session");
        messageSender.sendToPlayer("Some Message", player);

        ArgumentCaptor<ResponseMessage> responseCaptor = ArgumentCaptor.forClass(ResponseMessage.class);
        verify(messagingTemplate).convertAndSendToUser(eq("A Principal"),
                eq(WebSocketConfig.USER_ENDPOINT),
                responseCaptor.capture());
        assertThat(responseCaptor.getValue().getResponse()).isEqualTo("Some Message");
        assertThat(responseCaptor.getValue().getSessionId()).isEqualTo("A Session");
    }
}