package com.forsvarir.mud.communications;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.Room;
import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.SessionManager;
import com.forsvarir.mud.actions.RoomActions;
import com.forsvarir.mud.communications.messages.ConnectMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionControllerTest {
    @Mock
    private SessionManager sessionManager;
    @Mock
    private MessageSender messageSender;
    @Mock
    private RoomManager roomManager;
    @Mock
    private RoomActions roomActions;

    @InjectMocks
    SessionController sessionController;

    @BeforeEach
    void beforeEach() {
        Room defaultRoom = new Room(0, "default\n\r");
        when(roomManager.findRoom(anyInt())).thenReturn(Optional.of(defaultRoom));
        Player player = new Player("player", "", "");
        when(sessionManager.createSession(any(), any(), any())).thenReturn(player);
    }

    @Test
    void createSession_addsSession() {
        ConnectMessage connectMessage = new ConnectMessage("PlayerName", "password");
        Principal principal = () -> "PrincipalName";

        sessionController.createSession(connectMessage, principal, "sessionId");

        verify(sessionManager).createSession("PrincipalName", "sessionId", "PlayerName");
    }

    @Test
    void createSession_sendsRoomView() {
        ConnectMessage connectMessage = new ConnectMessage("PlayerName", "password");
        Principal principal = () -> "PrincipalName";

        Player player = new Player("name", "prince", "sess");
        when(sessionManager.createSession(any(), any(), any())).thenReturn(player);

        when(roomActions.buildRoomViewForPlayer(any(), any())).thenReturn("Welcome!\n\r");

        sessionController.createSession(connectMessage, principal, "sessionId");

        verify(messageSender).sendToPlayer("Welcome!\n\r", player);
    }

    @Test
    void createSession_getsCorrectRoomView() {
        ConnectMessage connectMessage = new ConnectMessage("PlayerName", "password");
        Principal principal = () -> "PrincipalName";

        Player player = new Player("name", "prince", "sess");
        when(sessionManager.createSession(any(), any(), any())).thenReturn(player);

        Room welcomeRoom = new Room(0, "Welcome!\n\r");
        when(roomManager.findRoom(anyInt())).thenReturn(Optional.of(welcomeRoom));

        sessionController.createSession(connectMessage, principal, "sessionId");

        verify(roomActions).buildRoomViewForPlayer(welcomeRoom, player);
    }

    @Test
    void createSession_addsPlayerToWelcomeRoom() {
        ConnectMessage connectMessage = new ConnectMessage("PlayerName", "password");
        Principal principal = () -> "PrincipalName";

        Player player = new Player("name", "prince", "sess");
        when(sessionManager.createSession(any(), any(), any())).thenReturn(player);

        Room welcomeRoom = new Room(0, "Welcome!\n\r");
        when(roomManager.findRoom(anyInt())).thenReturn(Optional.of(welcomeRoom));

        sessionController.createSession(connectMessage, principal, "sessionId");

        assertThat(welcomeRoom.getPlayersInRoom()).containsExactly(player);
    }

    @Test
    void createSession_returnsSuccess() {
        var response = sessionController.createSession(new ConnectMessage(), () -> "", "");
        assertThat(response.getStatus()).isEqualTo("Connected");
    }
}