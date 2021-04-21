package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.SessionManager;
import com.forsvarir.mud.communications.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TellCommandTest {

    private SessionManager sessionManager;
    private MessageSender messageSender;
    private TellCommand command;

    @BeforeEach
    void beforeEach() {
        messageSender = mock(MessageSender.class);
        sessionManager = mock(SessionManager.class);
        command = new TellCommand(messageSender, sessionManager);
    }

    @Test
    void processCommand_SendsToCorrectUser() {
        Player targetPlayer = new Player("harry", "harryPrincipal", "harrySession");
        when(sessionManager.findPlayer(any())).thenReturn(targetPlayer);

        Player talkingPlayer = new Player("talker", "talkingPrincipal", "talkingSession");
        command.processCommand("harry hi!", talkingPlayer);

        verify(sessionManager).findPlayer("harry");
        verify(messageSender).sendToUser("talker tells you \"hi!\"", "harryPrincipal", "harrySession");
        verify(messageSender).sendToUser("You tell harry \"hi!\"", "talkingPrincipal", "talkingSession");
    }

    @Test
    void processCommand_targetPlayerNotPresent_whoMessage() {
        when(sessionManager.findPlayer(any())).thenReturn(null);

        Player talkingPlayer = new Player("talker", "talkingPrincipal", "talkingSession");
        command.processCommand("harry hi!", talkingPlayer);

        verify(messageSender).sendToUser("No-one by that name here.\n\r", "talkingPrincipal", "talkingSession");
    }
}