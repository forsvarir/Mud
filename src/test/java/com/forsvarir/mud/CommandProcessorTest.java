package com.forsvarir.mud;

import com.forsvarir.mud.communications.MessageSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandProcessorTest {
    @Mock
    private SessionManager sessionManager;

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

    @Test
    void processCommand_tellSendsToCorrectUser() {
        Player targetPlayer = new Player("harry", "harryPrincipal", "harrySession");
        when(sessionManager.findPlayer(any())).thenReturn(targetPlayer);

        commandProcessor.processCommand("tell harry \"hi!\"", "", "");

        verify(sessionManager).findPlayer("harry");
        verify(messageSender).sendToUser("hi!", "harryPrincipal", "harrySession");
    }
}