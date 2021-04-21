package com.forsvarir.mud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Room {
    private int id;
    private final String description;
    private final List<Player> players;
    private final List<Exit> exits;

    public Room(int id, String description) {
        this.id = id;
        this.description = description;
        this.players = new ArrayList<>();
        this.exits = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<Player> getPlayersInRoom() {
        return Collections.unmodifiableList(players);
    }

    public Optional<Exit> getExit(String exitName) {
        return exits.stream().filter(exit -> exit.getName().equals(exitName)).findFirst();
    }

    public void addExit(String exitName, int destinationRoomId) {
        exits.add(new Exit(exitName, destinationRoomId));
    }

    public void addPlayer(Player playerToAdd) {
        players.add(playerToAdd);
        playerToAdd.setRoom(this);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.setRoom(null);
    }
}
