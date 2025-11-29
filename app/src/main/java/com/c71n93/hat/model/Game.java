package com.c71n93.hat.model;

import java.util.List;

public final class Game {
    private final List<Team> teams;
    private final int wordsTotal;

    public Game(final List<Team> players, final int wordsPerPlayer) {
        this.teams = players;
        this.wordsTotal = wordsPerPlayer;
    }

    public List<Team> teams() {
        return this.teams;
    }

    public int wordsTotal() {
        return this.wordsTotal;
    }
}
