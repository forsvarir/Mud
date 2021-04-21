package com.forsvarir.mud;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomTest {
    @Test
    void getExit_exitDoesNotExist_empty() {
        Room room = new Room(0, "");

        Optional<Exit> exit = room.getExit("N");

        assertThat(exit).isEmpty();
    }

    @Test
    void getExit_exitExists_correctExit() {
        Room room = new Room(0, "");
        room.addExit("N", 5);

        Optional<Exit> optionalExit = room.getExit("N");

        assertThat(optionalExit).hasValueSatisfying(exit -> assertThat(exit.getDestinationRoomId()).isEqualTo(5));
    }

}
