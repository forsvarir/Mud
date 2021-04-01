package com.forsvarir.mud;

import com.forsvarir.mud.communications.MessageSender;
import com.forsvarir.mud.communications.messages.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommandProcessorTest {
    @Mock
    MessageSender messageSender;

    @InjectMocks
    CommandProcessor commandProcessor;

    @Test
    void processCommand_sendsResponseWithCommand() {
        commandProcessor.processCommand("hello");

        ArgumentCaptor<ResponseMessage> responseCaptor = ArgumentCaptor.forClass(ResponseMessage.class);
        verify(messageSender).sendToAll(responseCaptor.capture());
        assertThat(responseCaptor.getValue().getResponse()).isEqualTo("hello");
    }

}