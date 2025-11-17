package com.c71n93.hat.model;

public final class Game {
    private final PlayersNumber players;

    public Game(final PlayersNumber players) {
        this.players = players;
    }
    public Game(final int players) {
        this(new PlayersNumber(players));
    }
    public PlayersNumber players() {
        return players;
    }
}
