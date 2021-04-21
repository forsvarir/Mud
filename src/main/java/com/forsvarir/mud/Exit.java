package com.forsvarir.mud;

public class Exit {
    private final String name;
    private final int destinationRoomId;

    public Exit(String name, int destinationRoomId) {
        this.name = name;
        this.destinationRoomId = destinationRoomId;
    }

    public String getName() {
        return name;
    }

    public int getDestinationRoomId() {
        return destinationRoomId;
    }
}
