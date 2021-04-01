package com.forsvarir.mud;

import com.forsvarir.mud.communications.MessageSender;
import com.forsvarir.mud.communications.messages.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandProcessor {
    @Autowired
    MessageSender messageSender;

    public void processCommand(String command) {
        messageSender.sendToAll(new ResponseMessage(command));
    }
}