package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class ShoutCommand implements MudCommand {
    private final MessageSender messageSender;

    public ShoutCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void processCommand(String message, Player sender) {
        messageSender.sendToAll(sender.getName() + " shouts \"" + message + "\"");
    }
}
