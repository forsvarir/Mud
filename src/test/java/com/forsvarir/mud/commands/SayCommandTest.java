package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.Room;
import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.communications.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class SayCommandTest {
    RoomManager roomManager;
    MessageSender messageSender;
    SayCommand sayCommand;

    @BeforeEach
    void beforeEach() {
        messageSender = mock(MessageSender.class);
        roomManager = mock(RoomManager.class);
        sayCommand = new SayCommand(messageSender, roomManager);
    }

    @Test
    void processCommand_onlyPlayerInRoom_saysMessage() {
        Player talkingPlayer = new Player("Talker", "talkerPrincipal", "talkerSession");

        Room optionalRoom = CreateRoomWithPlayers(singletonList(talkingPlayer));

        when(roomManager.findRoom(anyInt())).thenReturn(Optional.of(optionalRoom));

        sayCommand.processCommand("Hello!", talkingPlayer);

        verify(messageSender).sendToUser("You say 'Hello!'\n\r", talkingPlayer.getPrincipal(), talkingPlayer.getSessionId());
    }

    @Test
    void processCommand_otherPlayersInRoom_sendsValidMessageToEachPlayer() {
        Player talkingPlayer = new Player("Talker", "talkerPrincipal", "talkerSession");
        Player playerInRoom1 = new Player("Player1", "inRoomPrincipal1", "inRoomSession1");
        Player playerInRoom2 = new Player("Player2", "inRoomPrincipal2", "inRoomSession2");

        Room optionalRoom = CreateRoomWithPlayers(asList(talkingPlayer, playerInRoom1, playerInRoom2));

        when(roomManager.findRoom(anyInt())).thenReturn(Optional.of(optionalRoom));

        sayCommand.processCommand("hi there", talkingPlayer);

        verify(messageSender).sendToUser("You say 'hi there'\n\r", talkingPlayer.getPrincipal(), talkingPlayer.getSessionId());
        verify(messageSender, never()).sendToUser("Talker says 'hi there'\n\r", talkingPlayer.getPrincipal(), talkingPlayer.getSessionId());
        verify(messageSender).sendToUser("Talker says 'hi there'\n\r", playerInRoom1.getPrincipal(), playerInRoom1.getSessionId());
        verify(messageSender).sendToUser("Talker says 'hi there'\n\r", playerInRoom2.getPrincipal(), playerInRoom2.getSessionId());
    }

    @Test
    void processCommand_anotherPlayerNotInRoom_messageNotSentToThem() {
        Player talkingPlayer = new Player("Talker", "talkerPrincipal", "talkerSession");
        Player playerNOTInRoom = new Player("Player3", "NOTinRoomPrincipal", "NOTinRoomSession");

        Room optionalRoom = CreateRoomWithPlayers(singletonList(talkingPlayer));

        when(roomManager.findRoom(anyInt())).thenReturn(Optional.of(optionalRoom));

        sayCommand.processCommand("hi there", talkingPlayer);

        verify(messageSender).sendToUser("You say 'hi there'\n\r", talkingPlayer.getPrincipal(), talkingPlayer.getSessionId());
        verify(messageSender, never()).sendToUser(any(), eq(playerNOTInRoom.getPrincipal()), eq(playerNOTInRoom.getSessionId()));
    }

    private Room CreateRoomWithPlayers(List<Player> players) {
        Room room = new Room(5, "Room1");

        players.forEach(room::addPlayer);

        return room;
    }
}