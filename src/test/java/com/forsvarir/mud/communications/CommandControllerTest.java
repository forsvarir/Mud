package com.forsvarir.mud.communications;

import com.forsvarir.mud.CommandProcessor;
import com.forsvarir.mud.communications.messages.CommandMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommandControllerTest {

    @Mock
    private CommandProcessor commandProcessor = new CommandProcessor();

    @InjectMocks
    private CommandController commandController;

    @Test
    void command_sendsCommandToProcessor() {
        CommandMessage command = new CommandMessage("hello");

        commandController.command(command, () -> "SomePrincipal", "SomeSession");

        verify(commandProcessor).processCommand("hello", "SomePrincipal", "SomeSession");
    }

}