package com.forsvarir.mud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room {
    private int id;
    private final String description;
    private List<Player> players = new ArrayList<>();

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

    public void addPlayer(Player playerToAdd) {
        players.add(playerToAdd);
    }

    public List<Player> getPlayersInRoom() {
        return Collections.unmodifiableList(players);
    }
}
