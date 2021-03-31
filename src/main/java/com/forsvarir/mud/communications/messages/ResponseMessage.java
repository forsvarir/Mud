package com.forsvarir.mud.communications.messages;

public class ResponseMessage {

    private final String response;

    public ResponseMessage(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
