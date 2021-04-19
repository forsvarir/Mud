package com.forsvarir.mud;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void createSession_returnsCreatedPlayer() {
        var player = sessionManager.createSession("principal1", "session1", "player1");

        assertThat(player.getName()).isEqualTo("player1");
        assertThat(player.getSessionId()).isEqualTo("session1");
        assertThat(player.getPrincipal()).isEqualTo("principal1");
    }

    @Test
    void findPlayer_byPrincipalAndSession_returnsPlayer() {
        sessionManager.createSession("Principal", "sessionId", "PlayerName");

        var player = sessionManager.findPlayer("Principal", "sessionId");

        assertThat(player.getName()).isEqualTo("PlayerName");
    }

    @Test
    void findPlayer_byPlayerName_returnsPlayer() {
        sessionManager.createSession("Principal", "sessionId", "PlayerName");

        var player = sessionManager.findPlayer("PlayerName");

        assertThat(player.getName()).isEqualTo("PlayerName");
    }
}