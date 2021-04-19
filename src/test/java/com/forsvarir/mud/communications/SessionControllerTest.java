package com.forsvarir.mud.communications;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.SessionManager;
import com.forsvarir.mud.communications.messages.ConnectMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionControllerTest {
    @Mock
    SessionManager sessionManager;
    @Mock
    MessageSender messageSender;

    @InjectMocks
    SessionController sessionController;

    @Test
    void createSession_addsSession() {
        ConnectMessage connectMessage = new ConnectMessage("PlayerName", "password");
        Principal principal = () -> "PrincipalName";

        sessionController.createSession(connectMessage, principal, "sessionId");

        verify(sessionManager).createSession("PrincipalName", "sessionId", "PlayerName");
    }

    @Test
    void createSession_sendsWelcomeMessage() {
        ConnectMessage connectMessage = new ConnectMessage("PlayerName", "password");
        Principal principal = () -> "PrincipalName";

        Player player = new Player("name", "prince", "sess");
        when(sessionManager.createSession(any(), any(), any())).thenReturn(player);

        sessionController.createSession(connectMessage, principal, "sessionId");

        verify(messageSender).sendToPlayer("Welcome!\n\r", player);
    }

    @Test
    void createSession_returnsSuccess() {
        var response = sessionController.createSession(new ConnectMessage(), () -> "", "");
        assertThat(response.getStatus()).isEqualTo("Connected");
    }
}