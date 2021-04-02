package com.forsvarir.mud;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

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