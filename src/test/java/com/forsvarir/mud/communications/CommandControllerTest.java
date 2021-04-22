package com.forsvarir.mud.communications;

import com.forsvarir.mud.CommandProcessor;
import com.forsvarir.mud.Player;
import com.forsvarir.mud.SessionManager;
import com.forsvarir.mud.communications.messages.CommandMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandControllerTest {

    @Mock
    private CommandProcessor commandProcessor;

    @Mock
    private SessionManager sessionManager;

    @InjectMocks
    private CommandController commandController;

    @Test
    void command_sendsCommandToProcessor() {
        CommandMessage command = new CommandMessage("hello");

        var player = new Player("Player", "SomePrincipal", "SomeSession");
        when(sessionManager.findPlayer(any(), any())).thenReturn(player);

        commandController.command(command, () -> "SomePrincipal", "SomeSession");

        verify(commandProcessor).processCommand("hello", player);
    }

    @Test
    void command_sendsGetsPlayerFromSession() {
        CommandMessage command = new CommandMessage("hello");

        commandController.command(command, () -> "SomePrincipal", "SomeSession");

        verify(sessionManager).findPlayer("SomePrincipal", "SomeSession");
    }

}