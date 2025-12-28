package com.c71n93.hat.model;

import java.util.List;

public final class GameSettings {
    private final List<Team> teams;
    private final int wordsTotal;
    private final int turnDurationSec;

    public GameSettings(final List<Team> players, final int wordsPerPlayer, final int turnDuration) {
        this.teams = players;
        this.wordsTotal = wordsPerPlayer;
        this.turnDurationSec = turnDuration;
    }

    public List<Team> teams() {
        return this.teams;
    }

    public int wordsTotal() {
        return this.wordsTotal;
    }

    public int turnDurationSec() {
        return this.turnDurationSec;
    }
}
