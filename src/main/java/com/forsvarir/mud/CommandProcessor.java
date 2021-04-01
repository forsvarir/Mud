package com.forsvarir.mud;

import com.forsvarir.mud.communications.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandProcessor {
    @Autowired
    private MessageSender messageSender;

    public void processCommand(String command) {
        messageSender.sendToAll(command);
    }
}
