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

    public Words with(final Word word) {
        if (!this.tryPut(word)) {
            throw new IllegalStateException("Words are full.");
        }
        return this;
    }

    public boolean tryPut(final Word word) {
        if (this.items.size() >= this.total) {
            return false;
        }
        this.items.add(word);
        return true;
    }

    public void ifFullOrElse(final Runnable actionFull, final Runnable actionElse) {
        if (this.isFull()) {
            actionFull.run();
        } else {
            actionElse.run();
        }
    }

    public boolean isFull() {
        return this.untilFull() == 0;
    }

    public int untilFull() {
        return this.total - this.items.size();
    }
}
