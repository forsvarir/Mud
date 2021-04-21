package com.forsvarir.mud;

public class Room {
    private int id;
    private final String description;

    public Room(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
