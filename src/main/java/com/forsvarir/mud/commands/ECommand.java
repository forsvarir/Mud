package com.forsvarir.mud.commands;

import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class ECommand extends EastCommand {
    public ECommand(MessageSender messageSender, RoomManager roomManager) {
        super(messageSender, roomManager);
    }
}
