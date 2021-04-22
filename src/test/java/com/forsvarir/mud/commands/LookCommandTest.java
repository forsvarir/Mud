package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.Room;
import com.forsvarir.mud.actions.RoomActions;
import com.forsvarir.mud.communications.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LookCommandTest {

    private MessageSender messageSender;
    private RoomActions roomActions;
    private LookCommand command;

    @BeforeEach
    void beforeEach() {
        messageSender = mock(MessageSender.class);
        roomActions = mock(RoomActions.class);
        command = new LookCommand(messageSender, roomActions);
    }

    @Test
    void processCommand_getsRoomViewFromRoomActions() {
        Room currentRoom = new Room(0, "EveryPlaceEver");
        Player player = new Player("name", "principal", "sessionId");
        currentRoom.addPlayer(player);

        command.processCommand("", player);

        verify(roomActions).buildRoomViewForPlayer(currentRoom, player);
    }

    @Test
    void processCommand_sendsRoomViewToPlayer() {
        Room currentRoom = new Room(0, "EveryPlaceEver");
        Player player = new Player("name", "principal", "sessionId");
        currentRoom.addPlayer(player);
        String roomView = "This is the room View";

        when(roomActions.buildRoomViewForPlayer(any(), any())).thenReturn(roomView);

        command.processCommand("", player);

        verify(messageSender).sendToPlayer(roomView, player);
    }
}