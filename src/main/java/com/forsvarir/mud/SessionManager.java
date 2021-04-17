package com.forsvarir.mud;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionManager {
    private final ConcurrentHashMap<String, Player> playersByPrincipalSession = new ConcurrentHashMap<>();

    public void createSession(String principalName, String sessionId, String playerName) {
        playersByPrincipalSession.put(buildSessionKey(principalName, sessionId), new Player(playerName, principalName, sessionId));
    }

    public Player findPlayer(String principalName, String sessionId) {
        return playersByPrincipalSession.get(buildSessionKey(principalName, sessionId));
    }

    public Player findPlayer(String playerName) {
        return playersByPrincipalSession.search(0, (key, player) -> {
            if (player.getName().equals(playerName)) return player;
            return null;
        });
    }

    private String buildSessionKey(String principalName, String sessionId) {
        return String.format("{%s},{%s}", principalName, sessionId);
    }
}