package com.forsvarir.mud.communications;

import com.forsvarir.mud.communications.messages.CommandMessage;
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
class CommandControllerTest {

    @Mock
    SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    CommandController commandController;

    @Test
    void command_sendsResponseWithSameContent() {
        CommandMessage command = new CommandMessage("hello");
        commandController.command(command);

        ArgumentCaptor<ResponseMessage> responseCaptor = ArgumentCaptor.forClass(ResponseMessage.class);

        verify(messagingTemplate).convertAndSend(eq("/ws/responses"),
                responseCaptor.capture());

        assertThat(responseCaptor.getValue().getResponse()).isEqualTo("hello");
    }

}