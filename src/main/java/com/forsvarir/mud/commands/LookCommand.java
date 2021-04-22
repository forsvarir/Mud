package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class LookCommand implements MudCommand {
    private final MessageSender messageSender;

    public LookCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void processCommand(String arguments, Player sender) {
        var currentRoom = sender.getRoom();
        // TODO: CurrentRoom == null?
        messageSender.sendToPlayer(currentRoom.getDescription() + "\n\r", sender);

        currentRoom.getPlayersInRoom().stream()
                .filter(player -> player != sender)
                .forEach(player -> messageSender.sendToPlayer(player.getName() + " is here.\n\r", sender));
    }
}
