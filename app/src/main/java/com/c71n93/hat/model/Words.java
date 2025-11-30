package com.c71n93.hat.model;

import java.util.ArrayList;
import java.util.List;

public class Words {
    private final int total;
    private final List<Word> items;
    public Words(final int totalWords) {
        this.total = totalWords;
        this.items = new ArrayList<>(totalWords);
    }

    public void put(final Word word) {
        if (!this.tryPut(word)) {
            throw new IllegalStateException("Words are full.");
        }
    }

    public boolean tryPut(final Word word) {
        if (this.items.size() >= this.total) {
            return false;
        }
        this.items.add(word);
        return true;
    }
}
