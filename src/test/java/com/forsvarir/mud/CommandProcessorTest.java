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
    private CommandTokenizer commandTokenizer;
    private UnknownCommand unknownCommand;
    private Map<String, MudCommand> commands;

    @InjectMocks
    private CommandProcessor commandProcessor;

    @BeforeEach
    void beforeEach() {
        commandTokenizer = mock(CommandTokenizer.class);
        unknownCommand = mock(UnknownCommand.class);
        commands = Map.of("tellCommand", mock(MudCommand.class),
                "shoutCommand", mock(MudCommand.class));

        commandProcessor = new CommandProcessor(commandTokenizer, unknownCommand, commands);
        when(commandTokenizer.extractTokens(any())).thenReturn(new CommandTokens("", ""));
    }

    @Test
    void processCommand_sendsCommandToTokenizer() {
        Player sendingPlayer = new Player("player", "prince", "sess");

        commandProcessor.processCommand("super secret command", sendingPlayer);

        verify(commandTokenizer).extractTokens("super secret command");
    }

    @Test
    void processCommand_commandNotPresent_invokesUnknownCommand() {
        Player sendingPlayer = new Player("player", "prince", "sess");

        commandProcessor.processCommand("notreallyacommand", sendingPlayer);

        verify(unknownCommand).processCommand(any(), any());
    }

    @Test
    void processCommand_commandPresent_invokesCorrectCommand() {
        Player sendingPlayer = new Player("player", "prince", "sess");

        MudCommand expectedTarget = mock(MudCommand.class);
        MudCommand notCalled = mock(MudCommand.class);
        Map<String, MudCommand> validCommands = Map.of("knownCommand", expectedTarget, "other", notCalled);
        commandProcessor = new CommandProcessor(commandTokenizer, unknownCommand, validCommands);
        when(commandTokenizer.extractTokens(any())).thenReturn(new CommandTokens("known", ""));

        commandProcessor.processCommand("known", sendingPlayer);

        verify(expectedTarget).processCommand(any(), any());
    }

    @Test
    void processCommand_commandPresent_invokesCommandWithArguments() {
        Player sendingPlayer = new Player("player", "prince", "sess");

        MudCommand expectedTarget = mock(MudCommand.class);
        MudCommand notCalled = mock(MudCommand.class);
        Map<String, MudCommand> validCommands = Map.of("knownCommand", expectedTarget, "other", notCalled);
        commandProcessor = new CommandProcessor(commandTokenizer, unknownCommand, validCommands);
        when(commandTokenizer.extractTokens(any())).thenReturn(new CommandTokens("known", "some arguments"));

        commandProcessor.processCommand("known", sendingPlayer);

        verify(expectedTarget).processCommand(eq("some arguments"), any());
    }

    @Test
    void processCommand_commandPresent_invokesCommandSendingPlayer() {
        Player sendingPlayer = new Player("player", "prince", "sess");

        MudCommand expectedTarget = mock(MudCommand.class);
        MudCommand notCalled = mock(MudCommand.class);
        Map<String, MudCommand> validCommands = Map.of("knownCommand", expectedTarget, "other", notCalled);
        commandProcessor = new CommandProcessor(commandTokenizer, unknownCommand, validCommands);
        when(commandTokenizer.extractTokens(any())).thenReturn(new CommandTokens("known", "some arguments"));

        commandProcessor.processCommand("known", sendingPlayer);

        verify(expectedTarget).processCommand(any(), eq(sendingPlayer));
    }
}