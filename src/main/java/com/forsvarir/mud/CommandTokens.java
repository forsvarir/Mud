package com.forsvarir.mud;

public class CommandTokens {
    private final String command;
    private final String arguments;

    public CommandTokens(String command, String arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public String getCommand() {
        return command;
    }

    public String getArguments() {
        return arguments;
    }
}
