package com.c71n93.hat.model;

import android.widget.EditText;
import java.util.Optional;

public final class PlayersNumber {
    private final int players;

    public PlayersNumber(final int players) {
        this.players = players;
    }

    public int value() {
        return players;
    }

    public static Optional<PlayersNumber> tryFromText(final EditText input) {
        final String value = input.getText().toString().trim();
        if (value.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(new PlayersNumber(Integer.parseInt(value)));
        } catch (NumberFormatException ignored) {
            return Optional.empty();
        }
    }
}
