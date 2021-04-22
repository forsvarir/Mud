package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class SouthCommand extends MovementCommand implements MudCommand {

    public SouthCommand(MessageSender messageSender, RoomManager roomManager) {
        super(messageSender, roomManager, "South", "south", "North");
    }

    @Override
    public void processCommand(String arguments, Player sender) {
        move(sender);
    }
}
