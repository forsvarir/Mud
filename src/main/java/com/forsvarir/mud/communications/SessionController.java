package com.forsvarir.mud.communications;

import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.SessionManager;
import com.forsvarir.mud.actions.RoomActions;
import com.forsvarir.mud.communications.messages.ConnectMessage;
import com.forsvarir.mud.communications.messages.ConnectionStatusMessage;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class SessionController {
    private final SessionManager sessionManager;
    private final MessageSender messageSender;
    private final RoomManager roomManager;
    private final RoomActions roomActions;

    SessionController(SessionManager sessionManager, MessageSender messageSender, RoomManager roomManager, RoomActions roomActions) {
        this.sessionManager = sessionManager;
        this.messageSender = messageSender;
        this.roomManager = roomManager;
        this.roomActions = roomActions;
    }

    @MessageMapping("/createSession")
    @SendToUser("/queue/session")
    public ConnectionStatusMessage createSession(ConnectMessage connectMessage,
                                                 Principal principal,
                                                 @Header("simpSessionId") String sessionId) {

        var player = sessionManager.createSession(principal.getName(), sessionId, connectMessage.getPlayerName());

        var room = roomManager.findRoom(RoomManager.DEFAULT_ROOM).orElseThrow();
        room.addPlayer(player);

        var roomView = roomActions.buildRoomViewForPlayer(room, player);

        messageSender.sendToPlayer(roomView, player);

        return new ConnectionStatusMessage("Connected");
    }
}
