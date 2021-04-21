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

        assertThat(defaultRoom.getExit("North")).isNotEmpty();
        assertThat(defaultRoom.getExit("South")).isNotEmpty();
        assertThat(defaultRoom.getExit("West")).isNotEmpty();
        assertThat(defaultRoom.getExit("East")).isNotEmpty();
        assertThat(defaultRoom.getExit("Up")).isNotEmpty();
        assertThat(defaultRoom.getExit("Down")).isNotEmpty();
    }
}