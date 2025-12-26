package com.c71n93.hat.model;

public class GameState {
    private final Words words;
    private final Hat hat;
    private final TeamsQueue teamsQueue;

    public GameState(final Words words, final Hat hat, final TeamsQueue teamsQueue) {
        this.words = words;
        this.hat = hat;
        this.teamsQueue = teamsQueue;
    }

    public Words words() {
        return this.words;
    }
    public Hat hat() {
        return this.hat;
    }
    public TeamsQueue teamsQueue() {
        return this.teamsQueue;
    }
}
