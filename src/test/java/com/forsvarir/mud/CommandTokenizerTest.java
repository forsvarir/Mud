package com.forsvarir.mud;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommandTokenizerTest {

    CommandTokenizer tokenizer = new CommandTokenizer();

    @Test
    void extractTokens_emptyString_noTokens() {
        var tokens = tokenizer.extractTokens("");
        assertThat(tokens.getCommand()).isEqualTo("");
    }

    @Test
    void extractTokens_singleWord_populatesCommand() {
        var tokens = tokenizer.extractTokens("command");
        assertThat(tokens.getCommand()).isEqualTo("command");
    }

    @Test
    void extractTokens_singleLetterWord_populatesCapitalizedCommand() {
        var tokens = tokenizer.extractTokens("n");
        assertThat(tokens.getCommand()).isEqualTo("N");
    }

    @Test
    void extractTokens_singleWord_stripsTrailingSpaces() {
        var tokens = tokenizer.extractTokens("command     ");
        assertThat(tokens.getCommand()).isEqualTo("command");
        assertThat(tokens.getArguments()).isEqualTo("");
    }

    @Test
    void extractTokens_singleWord_ignoresLeadingSpaces() {
        var tokens = tokenizer.extractTokens("       command");
        assertThat(tokens.getCommand()).isEqualTo("command");
        assertThat(tokens.getArguments()).isEqualTo("");
    }

    @Test
    void extractTokens_multipleWords_populatesCommandAndArgs() {
        var tokens = tokenizer.extractTokens("command other arguments");
        assertThat(tokens.getCommand()).isEqualTo("command");
        assertThat(tokens.getArguments()).isEqualTo("other arguments");
    }

    @Test
    void extractTokens_multipleWords_ignoresPaddedSpacesBetweenCommandAndArguments() {
        var tokens = tokenizer.extractTokens("command       other arguments");
        assertThat(tokens.getCommand()).isEqualTo("command");
        assertThat(tokens.getArguments()).isEqualTo("other arguments");
    }

    @Test
    void extractTokens_uppercaseCommand_commandIsLowerCased() {
        var tokens = tokenizer.extractTokens("COMMAND arguments");
        assertThat(tokens.getCommand()).isEqualTo("command");
    }

    @Test
    void extractTokens_uppercaseArguments_argumentsAreUpperCased() {
        var tokens = tokenizer.extractTokens("command ARGUMENTS");
        assertThat(tokens.getArguments()).isEqualTo("ARGUMENTS");
    }
}