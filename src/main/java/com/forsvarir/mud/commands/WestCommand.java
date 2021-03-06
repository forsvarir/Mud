package com.forsvarir.mud.commands;

import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.actions.RoomActions;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class WestCommand extends MovementCommand {
    public WestCommand(MessageSender messageSender, RoomManager roomManager, RoomActions roomActions) {
        super(messageSender, roomManager, roomActions, "West", "west", "East");
    }
}
