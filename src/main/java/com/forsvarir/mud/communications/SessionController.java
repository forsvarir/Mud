package com.forsvarir.mud.communications;

import com.forsvarir.mud.SessionManager;
import com.forsvarir.mud.communications.messages.ConnectMessage;
import com.forsvarir.mud.communications.messages.ConnectionStatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class SessionController {
    @Autowired
    private SessionManager sessionManager;

    @MessageMapping("/createSession")
    @SendToUser("/queue/session")
    public ConnectionStatusMessage createSession(ConnectMessage connectMessage,
                                                 Principal principal,
                                                 @Header("simpSessionId") String sessionId) {

        sessionManager.createSession(principal.getName(), sessionId, connectMessage.getPlayerName());

        return new ConnectionStatusMessage("Connected");
    }
}
