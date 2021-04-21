package com.forsvarir.mud;

import com.forsvarir.mud.commands.MudCommand;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class NorthCommand implements MudCommand {

    private final MessageSender messageSender;
    private final RoomManager roomManager;

    public NorthCommand(MessageSender messageSender, RoomManager roomManager) {
        this.messageSender = messageSender;
        this.roomManager = roomManager;
    }

    @Override
    public void processCommand(String arguments, Player sender) {
        var currentRoom = sender.getRoom();

        if (currentRoom == null) {
            // TODO: Can this happen?  Possibly during character creation...
            return;
        }

        var exit = currentRoom.getExit("North");
        if (exit.isEmpty()) {
            messageSender.sendToPlayer("You can't go that way.\n\r", sender);
            return;
        }

        var destinationRoom = roomManager.findRoom(exit.get().getDestinationRoomId());
        if (destinationRoom.isEmpty()) {
            // TODO: Corrupt definition, pointing at room that doesn't exist
            return;
        }

        messageSender.sendToPlayer("You go North.\n\r", sender);

        currentRoom.removePlayer(sender);

        messageSender.sendToPlayer(destinationRoom.get().getDescription() + "\n\r", sender);

        for (var player : currentRoom.getPlayersInRoom()) {
            messageSender.sendToPlayer(sender.getName() + " leaves North.\n\r", player);
        }

        for (var player : destinationRoom.get().getPlayersInRoom()) {
            messageSender.sendToPlayer(sender.getName() + " has arrived from the South.\n\r", player);
        }

        destinationRoom.get().addPlayer(sender);
    }
}
