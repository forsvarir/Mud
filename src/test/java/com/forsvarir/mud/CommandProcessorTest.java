package com.forsvarir.mud;

import com.forsvarir.mud.communications.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandProcessorTest {
    @Mock
    private SessionManager sessionManager;

    @Mock
    private MessageSender messageSender;

    @Mock
    private CommandTokenizer commandTokenizer;

    @InjectMocks
    private CommandProcessor commandProcessor;

    @BeforeEach
    void beforeEach() {
        when(commandTokenizer.extractTokens(any())).thenReturn(new CommandTokens("", ""));
    }

    @Test
    void processCommand_sendsCommandToTokenizer() {
        commandProcessor.processCommand("super secret command", "principal", "harry");

        verify(commandTokenizer).extractTokens("super secret command");
    }

    @Test
    void processCommand_sendsShoutToAll() {
        Player sendingPlayer = new Player("Harry", "principal", "harry");
        when(sessionManager.findPlayer(any(), any())).thenReturn(sendingPlayer);

        when(commandTokenizer.extractTokens(any())).thenReturn(new CommandTokens("shout", "Hello!"));

        commandProcessor.processCommand("shout Hello!", "principal", "harry");

        verify(messageSender).sendToAll("Harry shouts \"Hello!\"");
    }

    @Test
    void processCommand_unknownCommand_sendsHuh() {
        when(commandTokenizer.extractTokens(any())).thenReturn(new CommandTokens("unknownCommand", ""));

        commandProcessor.processCommand("unknownCommand", "A User", "A session");

        verify(messageSender).sendToUser("Huh?", "A User", "A session");
    }

    @Test
    void processCommand_tellSendsToCorrectUser() {
        Player targetPlayer = new Player("harry", "harryPrincipal", "harrySession");
        when(sessionManager.findPlayer(any())).thenReturn(targetPlayer);
        Player talkingPlayer = new Player("talker", "talkingPrincipal", "talkingSession");
        when(sessionManager.findPlayer(any(), any())).thenReturn(talkingPlayer);

        when(commandTokenizer.extractTokens(any())).thenReturn(new CommandTokens("tell", "harry hi!"));

        commandProcessor.processCommand("tell harry hi!", "talkingPrincipal", "talkingSession");

        verify(sessionManager).findPlayer("harry");
        verify(messageSender).sendToUser("talker tells you \"hi!\"", "harryPrincipal", "harrySession");
        verify(messageSender).sendToUser("You tell harry \"hi!\"", "talkingPrincipal", "talkingSession");
    }
}