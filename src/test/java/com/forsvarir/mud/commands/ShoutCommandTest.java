package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.communications.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ShoutCommandTest {

    private MessageSender messageSender;
    private ShoutCommand command;

    @BeforeEach
    void beforeEach() {
        messageSender = mock(MessageSender.class);
        command = new ShoutCommand(messageSender);
    }

    @Test
    void processCommand_sendsShoutToAll() {
        Player sendingPlayer = new Player("Harry", "principal", "harry");

        command.processCommand("Hello!", sendingPlayer);

        verify(messageSender).sendToAll("Harry shouts \"Hello!\"");
    }
}