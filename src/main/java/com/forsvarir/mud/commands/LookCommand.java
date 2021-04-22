package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.actions.RoomActions;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class LookCommand implements MudCommand {
    private final MessageSender messageSender;
    private final RoomActions roomActions;

    public LookCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
        this.roomActions = new RoomActions();
    }

    @Override
    public void processCommand(String arguments, Player sender) {
        var roomView = roomActions.buildRoomViewForPlayer(sender.getRoom(), sender);
        messageSender.sendToPlayer(roomView, sender);
    }
}
