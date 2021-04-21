package com.forsvarir.mud;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoomManagerTest {

    RoomManager roomManager = new RoomManager();

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
}