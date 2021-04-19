package com.forsvarir.mud.communications;

import com.forsvarir.mud.SessionManager;
import com.forsvarir.mud.communications.messages.ConnectMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SessionControllerTest {
    @Mock
    SessionManager sessionManager;

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
    void createSession_returnsSuccess() {
        var response = sessionController.createSession(new ConnectMessage(), () -> "", "");
        assertThat(response.getStatus()).isEqualTo("Connected");
    }
}