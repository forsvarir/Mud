package com.forsvarir.mud.commands;

import com.forsvarir.mud.Player;

public interface MudCommand {

    void processCommand(String arguments, Player sender);
}
