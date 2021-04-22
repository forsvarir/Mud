package com.forsvarir.mud.commands;

import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class UCommand extends UpCommand {
    public UCommand(MessageSender messageSender, RoomManager roomManager) {
        super(messageSender, roomManager);
    }
}
