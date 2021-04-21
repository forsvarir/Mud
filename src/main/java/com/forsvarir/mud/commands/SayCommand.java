package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;
import com.forsvarir.mud.RoomManager;
import com.forsvarir.mud.communications.MessageSender;
import org.springframework.stereotype.Service;

@Service
public class SayCommand implements MudCommand {
    private MessageSender messageSender;
    private RoomManager roomManager;

    public SayCommand(MessageSender messageSender, RoomManager roomManager) {
        this.messageSender = messageSender;
        this.roomManager = roomManager;
    }

    @Override
    public void processCommand(String arguments, Player sender) {
        messageSender.sendToUser("You say '" + arguments + "'\n\r", sender.getPrincipal(), sender.getSessionId());

        var room = roomManager.findRoom(0).orElseThrow();

        for (var player : room.getPlayersInRoom()) {
            if (sender != player) {
                messageSender.sendToUser(sender.getName() + " says '" + arguments + "'\n\r", player.getPrincipal(), player.getSessionId());
            }
        }
    }
}
