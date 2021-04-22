package com.forsvarir.mud.actions;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.Room;
import org.springframework.stereotype.Service;

@Service
public class RoomActions {
    public String buildRoomViewForPlayer(Room room, Player player) {
        StringBuffer roomView = new StringBuffer();

        // TODO: CurrentRoom == null?
        roomView.append(room.getDescription()).append("\n\r");

        room.getPlayersInRoom().stream()
                .filter(playerInRoom -> playerInRoom != player)
                .forEach(playerInRoom -> roomView.append(playerInRoom.getName()).append(" is here.\n\r"));

        return roomView.toString();
    }
}
