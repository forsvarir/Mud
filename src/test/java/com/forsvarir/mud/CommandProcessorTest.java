package com.forsvarir.mud;

import com.forsvarir.mud.commands.MudCommand;
import com.forsvarir.mud.commands.UnknownCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CommandProcessorTest {
    private SessionManager sessionManager;
    private CommandTokenizer commandTokenizer;
    private UnknownCommand unknownCommand;
    private Map<String, MudCommand> commands;

    @InjectMocks
    private CommandProcessor commandProcessor;

    @BeforeEach
    void beforeEach() {
        sessionManager = mock(SessionManager.class);
        commandTokenizer = mock(CommandTokenizer.class);
        unknownCommand = mock(UnknownCommand.class);
        commands = Map.of("tellCommand", mock(MudCommand.class),
                "shoutCommand", mock(MudCommand.class));

        commandProcessor = new CommandProcessor(sessionManager, commandTokenizer, unknownCommand, commands);
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
    void processCommand_findsSendingPlayer() {
        commandProcessor.processCommand("super secret command", "principal", "harry");

        verify(sessionManager).findPlayer("principal", "harry");
    }

    @Test
    void processCommand_commandNotPresent_invokesUnknownCommand() {
        commandProcessor.processCommand("notreallyacommand", "principal", "session");

        verify(unknownCommand).processCommand(any(), any());
    }

    @Test
    void processCommand_commandPresent_invokesCorrectCommand() {
        MudCommand expectedTarget = mock(MudCommand.class);
        MudCommand notCalled = mock(MudCommand.class);
        Map<String, MudCommand> validCommands = Map.of("knownCommand", expectedTarget, "other", notCalled);
        commandProcessor = new CommandProcessor(sessionManager, commandTokenizer, unknownCommand, validCommands);
        when(commandTokenizer.extractTokens(any())).thenReturn(new CommandTokens("known", ""));

        commandProcessor.processCommand("known", "principal", "session");

        verify(expectedTarget).processCommand(any(), any());
    }

    @Test
    void processCommand_commandPresent_invokesCommandWithArguments() {
        MudCommand expectedTarget = mock(MudCommand.class);
        MudCommand notCalled = mock(MudCommand.class);
        Map<String, MudCommand> validCommands = Map.of("knownCommand", expectedTarget, "other", notCalled);
        commandProcessor = new CommandProcessor(sessionManager, commandTokenizer, unknownCommand, validCommands);
        when(commandTokenizer.extractTokens(any())).thenReturn(new CommandTokens("known", "some arguments"));

        commandProcessor.processCommand("known", "principal", "session");

        verify(expectedTarget).processCommand(eq("some arguments"), any());
    }

    @Test
    void processCommand_commandPresent_invokesCommandSendingPlayer() {
        MudCommand expectedTarget = mock(MudCommand.class);
        MudCommand notCalled = mock(MudCommand.class);
        Map<String, MudCommand> validCommands = Map.of("knownCommand", expectedTarget, "other", notCalled);
        commandProcessor = new CommandProcessor(sessionManager, commandTokenizer, unknownCommand, validCommands);
        when(commandTokenizer.extractTokens(any())).thenReturn(new CommandTokens("known", "some arguments"));

        Player sendingPlayer = new Player("player", "prince", "sess");
        when(sessionManager.findPlayer(any(), any())).thenReturn(sendingPlayer);

        commandProcessor.processCommand("known", "principal", "session");

        verify(expectedTarget).processCommand(any(), eq(sendingPlayer));
    }
}