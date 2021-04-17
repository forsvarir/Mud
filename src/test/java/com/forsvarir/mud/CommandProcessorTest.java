package com.forsvarir.mud;

import com.forsvarir.mud.commands.MudCommand;
import com.forsvarir.mud.commands.ShoutCommand;
import com.forsvarir.mud.commands.TellCommand;
import com.forsvarir.mud.commands.UnknownCommand;
import com.forsvarir.mud.communications.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommandProcessorTest {
    private SessionManager sessionManager;
    private MessageSender messageSender;
    private CommandTokenizer commandTokenizer;
    private UnknownCommand unknownCommand;
    private Map<String, MudCommand> commands;

    @InjectMocks
    private CommandProcessor commandProcessor;

    @BeforeEach
    void beforeEach() {
        sessionManager = mock(SessionManager.class);
        messageSender = mock(MessageSender.class);
        commandTokenizer = mock(CommandTokenizer.class);
        unknownCommand = new UnknownCommand(messageSender);
        commands = Map.of("tellCommand", new TellCommand(messageSender, sessionManager),
                "shoutCommand", new ShoutCommand(messageSender));

        commandProcessor = new CommandProcessor(sessionManager, messageSender, commandTokenizer, unknownCommand, commands);
        when(commandTokenizer.extractTokens(any())).thenReturn(new CommandTokens("", ""));

        Player sendingPlayer = new Player("player", "prince", "sess");
        when(sessionManager.findPlayer(any(), any())).thenReturn(sendingPlayer);
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
        Player sendingPlayer = new Player("Harry", "A User", "A session");
        when(sessionManager.findPlayer(any(), any())).thenReturn(sendingPlayer);

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