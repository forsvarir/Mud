package com.forsvarir.mud;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class RoomManager {
    public static final int DEFAULT_ROOM = 0;
    Map<Integer, Room> rooms = new HashMap<>();

    RoomManager() {
        rooms.put(0, new Room(0, "Welcome\n\r"));

    }

    public Optional<Room> findRoom(int roomId) {
        return Optional.ofNullable(rooms.get(roomId));
    }
}
