package com.forsvarir.mud.commands;

import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class DownCommand extends MovementCommand implements MudCommand {
    public DownCommand(MessageSender messageSender, RoomManager roomManager) {
        super(messageSender, roomManager, "Down", "down", "Up");
    }
}
