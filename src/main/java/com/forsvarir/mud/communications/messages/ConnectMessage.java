package com.forsvarir.mud.communications.messages;

public class ConnectMessage {
    private String playerName;
    private String playerPassword;

    public ConnectMessage() {

    }

    public ConnectMessage(String playerName, String playerPassword) {
        this.playerName = playerName;
        this.playerPassword = playerPassword;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerPassword() {
        return playerPassword;
    }

    public void setPlayerPassword(String playerPassword) {
        this.playerPassword = playerPassword;
    }
}
