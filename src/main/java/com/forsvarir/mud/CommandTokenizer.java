package com.forsvarir.mud;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class CommandTokenizer {
    CommandTokens extractTokens(String command) {
        var commandStripped = command.strip();
        var endOfFirstWord = commandStripped.indexOf(" ");

        if (endOfFirstWord == -1) {
            if (commandStripped.length() == 1) {
                return new CommandTokens(commandStripped.toUpperCase(Locale.ROOT), "");
            } else {
                return new CommandTokens(commandStripped.toLowerCase(Locale.ROOT), "");
            }
        }
        return new CommandTokens(commandStripped.substring(0, endOfFirstWord).toLowerCase(Locale.ROOT), commandStripped.substring(endOfFirstWord + 1).strip());
    }
}
