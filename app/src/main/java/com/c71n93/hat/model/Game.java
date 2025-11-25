package com.c71n93.hat.model;

public final class Game {
    private final int players;
    private final int wordsPerPlayer;

    public Game(final int players, final int wordsPerPlayer) {
        this.players = players;
        this.wordsPerPlayer = wordsPerPlayer;
    }

    public int players() {
        return this.players;
    }

    public int wordsPerPlayer() {
        return this.wordsPerPlayer;
    }
}
