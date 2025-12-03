package com.c71n93.hat.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * This class is a heart of this game. You can pull random word from it.
 */
public class Hat {
    private final List<Word> bag;
    private final Random rnd;

    public Hat() {
        this(new ArrayList<Word>());
    }
    public Hat(final Iterable<? extends Word> words) {
        this(Hat.fromIterable(words));
    }
    public Hat(final Collection<? extends Word> words) {
        this(words, new Random());
    }

    public Hat(final Collection<? extends Word> words, final Random rnd) {
        this.bag = new ArrayList<>(words);
        this.rnd = rnd;
    }

    public void add(final Word word) {
        this.bag.add(word);
    }

    public Optional<Word> pull() {
        if (this.bag.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(
                this.pull(this.rnd.nextInt(this.bag.size()))
            );
        }
    }

    private Word pull(final int i) {
        final Word pulled = this.bag.get(i);
        final Word last = this.bag.get(this.bag.size() - 1);
        this.bag.set(i, last);
        this.bag.remove(this.bag.size() - 1);
        return pulled;
    }

    private static List<Word> fromIterable(final Iterable<? extends Word> words) {
        final List<Word> tmpBag = new ArrayList<>();
        words.forEach(tmpBag::add);
        return tmpBag;
    }
}
