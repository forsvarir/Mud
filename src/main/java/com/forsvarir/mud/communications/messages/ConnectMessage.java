package com.forsvarir.mud.communications.messages;

public class ConnectMessage {
    private String playerName;

    public ConnectMessage() {

    }

    public ConnectMessage(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
