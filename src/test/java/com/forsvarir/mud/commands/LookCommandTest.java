package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.Room;
import com.forsvarir.mud.communications.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LookCommandTest {

    private MessageSender messageSender;
    private LookCommand command;

    @BeforeEach
    void beforeEach() {
        messageSender = mock(MessageSender.class);
        command = new LookCommand(messageSender);
    }

    @Test
    void processCommand_displaysDescriptionOfCurrentRoom() {
        Room currentRoom = new Room(0, "EveryPlaceEver");
        Player player = new Player("name", "principal", "sessionId");
        currentRoom.addPlayer(player);

        command.processCommand("", player);

        verify(messageSender).sendToPlayer("EveryPlaceEver\n\r", player);
    }

    @Test
    void processCommand_playersInRoom_playersSentToActor() {
        Room currentRoom = new Room(0, "EveryPlaceEver\n\r");
        Player player = new Player("name", "principal", "sessionId");
        currentRoom.addPlayer(player);
        Player otherPlayer1 = new Player("OtherPlayer", "principal", "sessionId");
        currentRoom.addPlayer(otherPlayer1);
        Player otherPlayer2 = new Player("AnotherPlayer", "principal", "sessionId");
        currentRoom.addPlayer(otherPlayer2);

        command.processCommand("", player);

        verify(messageSender).sendToPlayer("OtherPlayer is here.\n\r", player);
        verify(messageSender).sendToPlayer("AnotherPlayer is here.\n\r", player);
    }
}