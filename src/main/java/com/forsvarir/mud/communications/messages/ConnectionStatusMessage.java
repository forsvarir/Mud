package com.forsvarir.mud.communications.messages;

public class ConnectionStatusMessage {
    private final String status;

    public ConnectionStatusMessage(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
