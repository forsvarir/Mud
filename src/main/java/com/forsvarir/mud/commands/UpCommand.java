package com.forsvarir.mud.commands;

import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class UpCommand extends MovementCommand implements MudCommand {
    public UpCommand(MessageSender messageSender, RoomManager roomManager) {
        super(messageSender, roomManager, "Up", "up", "down");
    }
}
