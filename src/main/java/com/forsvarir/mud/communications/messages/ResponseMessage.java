package com.forsvarir.mud.communications.messages;

public class ResponseMessage {

    private final String response;
    private final String sessionId;

    public ResponseMessage(String response, String sessionId) {
        this.response = response;
        this.sessionId = sessionId;
    }

    public String getResponse() {
        return response;
    }

    public String getSessionId() {
        return sessionId;
    }
}
