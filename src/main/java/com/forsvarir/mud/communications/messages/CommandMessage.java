package com.forsvarir.mud.communications.messages;

public class CommandMessage {
    private String command;

    public CommandMessage() {
    }

    public CommandMessage(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
