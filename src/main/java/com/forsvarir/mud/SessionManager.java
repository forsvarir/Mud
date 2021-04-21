package com.forsvarir.mud;

import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionManager {
    private final ConcurrentHashMap<String, Player> playersByPrincipalSession = new ConcurrentHashMap<>();

    public Player createSession(String principalName, String sessionId, String playerName) {
        final Player newPlayer = new Player(capitalisePlayerName(playerName), principalName, sessionId);
        playersByPrincipalSession.put(buildSessionKey(principalName, sessionId), newPlayer);
        return newPlayer;
    }

    private String capitalisePlayerName(String playerName) {
        return playerName.substring(0, 1).toUpperCase(Locale.ROOT)
                + playerName.substring(1).toLowerCase(Locale.ROOT);
    }

    public Player findPlayer(String principalName, String sessionId) {
        return playersByPrincipalSession.get(buildSessionKey(principalName, sessionId));
    }

    public Player findPlayer(String playerName) {
        var targetPlayer = capitalisePlayerName(playerName);
        return playersByPrincipalSession.search(0, (key, player) -> {
            if (player.getName().equals(targetPlayer)) return player;
            return null;
        });
    }

    private String buildSessionKey(String principalName, String sessionId) {
        return String.format("{%s},{%s}", principalName, sessionId);
    }
}
