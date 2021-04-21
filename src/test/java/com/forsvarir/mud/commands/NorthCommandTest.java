package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.Room;
import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.communications.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class NorthCommandTest {
    private MessageSender messageSender;
    private RoomManager roomManager;
    private NorthCommand command;

    @BeforeEach
    void beforeEach() {
        messageSender = mock(MessageSender.class);
        roomManager = mock(RoomManager.class);
        command = new NorthCommand(messageSender, roomManager);
    }

    @Test
    void processCommand_noExitThatWay_message() {
        when(roomManager.findRoom(anyInt())).thenReturn(Optional.empty());

        Player player = new Player("name", "principal", "sessionId");
        Room startingRoom = new Room(0, "Start");
        startingRoom.addPlayer(player);
        command.processCommand("", player);

        verify(messageSender).sendToPlayer("You can't go that way.\n\r", player);
    }

    @Test
    void processCommand_exitExists_movesPlayer() {
        Room destinationRoom = new Room(222, "Destination");
        Room startingRoom = new Room(0, "Start");
        startingRoom.addExit("North", 222);
        when(roomManager.findRoom(anyInt())).thenReturn(Optional.of(destinationRoom));

        Player player = new Player("name", "principal", "sessionId");
        startingRoom.addPlayer(player);
        command.processCommand("N", player);

        assertThat(startingRoom.getPlayersInRoom()).isEmpty();
        assertThat(destinationRoom.getPlayersInRoom()).containsExactly(player);
    }

    @Test
    void processCommand_exitExists_sendsNewDescriptionToPlayer() {
        Room destinationRoom = new Room(222, "Destination");
        Room startingRoom = new Room(0, "Start");
        startingRoom.addExit("North", 222);
        when(roomManager.findRoom(anyInt())).thenReturn(Optional.of(destinationRoom));

        Player player = new Player("name", "principal", "sessionId");
        startingRoom.addPlayer(player);
        command.processCommand("N", player);

        verify(messageSender).sendToPlayer("Destination\n\r", player);
    }

    @Test
    void processCommand_exitExists_sendsPlayerLeavesToOthersInStartRoom() {
        Room destinationRoom = new Room(222, "Destination");
        Room startingRoom = new Room(0, "Start");
        startingRoom.addExit("North", 222);
        when(roomManager.findRoom(anyInt())).thenReturn(Optional.of(destinationRoom));

        Player player = new Player("Movingplayer", "principal", "sessionId");
        Player stationaryPlayer1 = new Player("p1", "principal", "sessionId");
        Player stationaryPlayer2 = new Player("p2", "principal", "sessionId");
        startingRoom.addPlayer(player);
        startingRoom.addPlayer(stationaryPlayer1);
        startingRoom.addPlayer(stationaryPlayer2);
        command.processCommand("N", player);

        verify(messageSender).sendToPlayer("Movingplayer leaves north.\n\r", stationaryPlayer1);
        verify(messageSender).sendToPlayer("Movingplayer leaves north.\n\r", stationaryPlayer2);
    }

    @Test
    void processCommand_exitExists_sendsPlayerArrivesToOthersInDestinationRoom() {
        Room destinationRoom = new Room(222, "Destination");
        Room startingRoom = new Room(0, "Start");
        startingRoom.addExit("North", 222);
        when(roomManager.findRoom(anyInt())).thenReturn(Optional.of(destinationRoom));

        Player player = new Player("Movingplayer", "principal", "sessionId");
        Player stationaryPlayer1 = new Player("p1", "principal", "sessionId");
        Player stationaryPlayer2 = new Player("p2", "principal", "sessionId");
        startingRoom.addPlayer(player);
        destinationRoom.addPlayer(stationaryPlayer1);
        destinationRoom.addPlayer(stationaryPlayer2);
        command.processCommand("N", player);

        verify(messageSender).sendToPlayer("Movingplayer has arrived from the South.\n\r", stationaryPlayer1);
        verify(messageSender).sendToPlayer("Movingplayer has arrived from the South.\n\r", stationaryPlayer2);
    }

}