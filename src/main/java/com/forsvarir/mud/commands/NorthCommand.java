package com.forsvarir.mud.commands;

import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class NorthCommand extends MovementCommand implements MudCommand {
    public NorthCommand(MessageSender messageSender, RoomManager roomManager) {
        super(messageSender, roomManager, "North", "north", "South");
    }
}
