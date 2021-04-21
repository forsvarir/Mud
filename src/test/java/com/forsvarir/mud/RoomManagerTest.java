package com.forsvarir.mud;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoomManagerTest {

    private final RoomManager roomManager = new RoomManager();

    @Test
    void findRoom_roomNotPresent_empty() {
        var found = roomManager.findRoom(-1);

        assertThat(found).isEmpty();
    }

    @Test
    void findRoom_defaultRoom_found() {
        var room = roomManager.findRoom(RoomManager.DEFAULT_ROOM);

        assertThat(room).hasValueSatisfying(r -> assertThat(r.getId()).isEqualTo(0));
    }

    @Test
    void findRoom_defaultRoom_hasExitsToAllStandardDirections() {
        var defaultRoom = roomManager.findRoom(RoomManager.DEFAULT_ROOM).orElseThrow();

        assertThat(defaultRoom.getExit("N")).isNotEmpty();
        assertThat(defaultRoom.getExit("S")).isNotEmpty();
        assertThat(defaultRoom.getExit("W")).isNotEmpty();
        assertThat(defaultRoom.getExit("E")).isNotEmpty();
        assertThat(defaultRoom.getExit("U")).isNotEmpty();
        assertThat(defaultRoom.getExit("D")).isNotEmpty();
    }
}