package com.c71n93.hat.model;

public class GameState {
    private final Words words;
    @SuppressWarnings("PMD.UnusedPrivateField")
    private final Hat hat;
    @SuppressWarnings("PMD.UnusedPrivateField")
    private final TeamsQueue teamsQueue;

    public GameState(final Words words, final Hat hat, final TeamsQueue teamsQueue) {
        this.words = words;
        this.hat = hat;
        this.teamsQueue = teamsQueue;
    }
    public Words words() {
        return this.words;
    }

    public TeamsQueue teamsQueue() {
        return this.teamsQueue;
    }
}
