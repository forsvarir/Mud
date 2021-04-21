package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.communications.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UnknownCommandTest {

    private MessageSender messageSender;
    private UnknownCommand command;

    @BeforeEach
    void beforeEach() {
        messageSender = mock(MessageSender.class);
        command = new UnknownCommand(messageSender);
    }

    @Test
    void processCommand_sendsHuh() {
        Player sendingPlayer = new Player("Harry", "A User", "A session");

        command.processCommand("", sendingPlayer);

        verify(messageSender).sendToUser("Huh?", "A User", "A session");
    }
}