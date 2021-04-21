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
        // Build an initial room layout
        //        1   5
        //        | /
        //    3 - 0 - 4
        //      / |
        //    6   2

        rooms.put(0, new Room(0, "Welcome\n\r"));
        rooms.put(1, new Room(1, "North Room\n\r"));
        rooms.put(2, new Room(2, "South Room\n\r"));
        rooms.put(3, new Room(3, "West Room\n\r"));
        rooms.put(4, new Room(4, "East Room\n\r"));
        rooms.put(5, new Room(5, "Up Room\n\r"));
        rooms.put(6, new Room(6, "Down Room\n\r"));

        rooms.get(0).addExit("N", 1);
        rooms.get(0).addExit("S", 2);
        rooms.get(0).addExit("W", 3);
        rooms.get(0).addExit("E", 4);
        rooms.get(0).addExit("U", 5);
        rooms.get(0).addExit("D", 6);

        rooms.get(1).addExit("S", 0);
        rooms.get(2).addExit("N", 0);
        rooms.get(3).addExit("E", 0);
        rooms.get(4).addExit("W", 0);
        rooms.get(5).addExit("D", 0);
        rooms.get(6).addExit("U", 0);
    }

    public Optional<Room> findRoom(int roomId) {
        return Optional.ofNullable(rooms.get(roomId));
    }
}
