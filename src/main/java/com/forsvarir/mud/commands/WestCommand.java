package com.forsvarir.mud.commands;

import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class WestCommand extends MovementCommand implements MudCommand {
    public WestCommand(MessageSender messageSender, RoomManager roomManager) {
        super(messageSender, roomManager, "West", "west", "East");
    }
}
