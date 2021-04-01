package com.forsvarir.mud;

import com.forsvarir.mud.communications.MessageSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommandProcessorTest {
    @Mock
    private MessageSender messageSender;

    @InjectMocks
    private CommandProcessor commandProcessor;

    @Test
    void processCommand_sendsGlobalToAll() {
        commandProcessor.processCommand("global", "", "");

        verify(messageSender).sendToAll("A global echo was requested");
    }

    @Test
    void processCommand_nonGlobalSendToUserSession() {
        commandProcessor.processCommand("a command", "A User", "A session");

        verify(messageSender).sendToUser("a command", "A User", "A session");
    }

}