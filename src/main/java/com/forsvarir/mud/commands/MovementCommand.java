package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.communications.MessageSender;

public abstract class MovementCommand implements MudCommand {
    private final MessageSender messageSender;
    private final RoomManager roomManager;
    private final String direction;
    private final String lowerCaseDirection;
    private final String inverseDirection;

    public MovementCommand(MessageSender messageSender, RoomManager roomManager, String direction, String lowerCaseDirection, String inverseDirection) {
        this.messageSender = messageSender;
        this.roomManager = roomManager;
        this.direction = direction;
        this.lowerCaseDirection = lowerCaseDirection;
        this.inverseDirection = inverseDirection;
    }

    @Override
    public void processCommand(String arguments, Player sender) {
        var currentRoom = sender.getRoom();

        if (currentRoom == null) {
            // TODO: Can this happen?  Possibly during character creation...
            return;
        }

        var exit = currentRoom.getExit(direction);
        if (exit.isEmpty()) {
            messageSender.sendToPlayer("You can't go that way.\n\r", sender);
            return;
        }

        var destinationRoom = roomManager.findRoom(exit.get().getDestinationRoomId());
        if (destinationRoom.isEmpty()) {
            // TODO: Corrupt definition, pointing at room that doesn't exist
            return;
        }

        currentRoom.removePlayer(sender);

        messageSender.sendToPlayer(destinationRoom.get().getDescription() + "\n\r", sender);

        for (var player : currentRoom.getPlayersInRoom()) {
            messageSender.sendToPlayer(sender.getName() + " leaves " + lowerCaseDirection + ".\n\r", player);
        }

        for (var player : destinationRoom.get().getPlayersInRoom()) {
            messageSender.sendToPlayer(sender.getName() + " has arrived from the " + inverseDirection + ".\n\r", player);
        }

        destinationRoom.get().addPlayer(sender);
    }
}
