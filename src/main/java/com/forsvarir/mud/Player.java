package com.forsvarir.mud;

public class Player {
    private final String name;
    private final String principal;
    private final String sessionId;

    public Player(String name, String principal, String sessionId) {
        this.name = name;
        this.principal = principal;
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getSessionId() {
        return sessionId;
    }
}
