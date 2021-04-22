package com.forsvarir.mud.actions;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.Room;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoomActionsTest {

    private final RoomActions roomActions = new RoomActions();

    @Test
    void buildRoomViewForPlayer_onlyPlayerIn_includesDescription() {
        Room currentRoom = new Room(0, "EveryPlaceEver");
        Player player = new Player("name", "principal", "sessionId");
        currentRoom.addPlayer(player);

        var roomView = roomActions.buildRoomViewForPlayer(currentRoom, player);

        assertThat(roomView).isEqualTo("EveryPlaceEver\n\r", player);
    }

    @Test
    void buildRoomViewForPlayer_playersInRoom_includesPlayersAtEnd() {
        Room currentRoom = new Room(0, "EveryPlaceEver");
        Player player = new Player("name", "principal", "sessionId");
        currentRoom.addPlayer(player);
        Player otherPlayer1 = new Player("OtherPlayer", "principal", "sessionId");
        currentRoom.addPlayer(otherPlayer1);
        Player otherPlayer2 = new Player("AnotherPlayer", "principal", "sessionId");
        currentRoom.addPlayer(otherPlayer2);

        var roomView = roomActions.buildRoomViewForPlayer(currentRoom, player);

        assertThat(roomView).isEqualTo("EveryPlaceEver\n\r" +
                "OtherPlayer is here.\n\r"
                + "AnotherPlayer is here.\n\r", player);
    }
}