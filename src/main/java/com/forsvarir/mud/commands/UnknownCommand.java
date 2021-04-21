package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class UnknownCommand implements MudCommand {
    private final MessageSender messageSender;

    public UnknownCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void processCommand(String arguments, Player sender) {
        messageSender.sendToUser("Huh?", sender.getPrincipal(), sender.getSessionId());
    }
}
