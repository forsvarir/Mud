package com.forsvarir.mud;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void createSession_returnsCreatedPlayer() {
        var player = sessionManager.createSession("principal1", "session1", "Player1");

        assertThat(player.getName()).isEqualTo("Player1");
        assertThat(player.getSessionId()).isEqualTo("session1");
        assertThat(player.getPrincipal()).isEqualTo("principal1");
    }

    @Test
    void createSession_capitalisesPlayerName() {
        var player = sessionManager.createSession("principal1", "session1", "player1");

        assertThat(player.getName()).isEqualTo("Player1");
    }

    @Test
    void createSession_lowerCasesTailOfPlayerName() {
        var player = sessionManager.createSession("principal1", "session1", "PLAYER1");

        assertThat(player.getName()).isEqualTo("Player1");
    }

    @Test
    void findPlayer_byPrincipalAndSession_returnsPlayer() {
        sessionManager.createSession("Principal", "sessionId", "Player2");

        var player = sessionManager.findPlayer("Principal", "sessionId");

        assertThat(player.getName()).isEqualTo("Player2");
    }

    @Test
    void findPlayer_byPlayerName_returnsPlayer() {
        sessionManager.createSession("Principal", "sessionId", "Player");

        var player = sessionManager.findPlayer("Player");

        assertThat(player.getName()).isEqualTo("Player");
    }

    @Test
    void findPlayer_byCaseMismatchedPlayerName_returnsPlayer() {
        sessionManager.createSession("Principal", "sessionId", "Player");

        var player = sessionManager.findPlayer("player");

        assertThat(player.getName()).isEqualTo("Player");
    }

    @Test
    void findPlayer_singleCharacterName_returnsPlayer() {
        sessionManager.createSession("Principal", "sessionId", "P");

        var player = sessionManager.findPlayer("P");

        assertThat(player.getName()).isEqualTo("P");
    }
}