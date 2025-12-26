package com.c71n93.hat.model;

import java.util.Optional;

/**
 * {@link Hat} wrapper that remembers the last pulled word and can put it back
 * if needed.
 */
public class RememberingHat {
    private final Hat origin;
    private Optional<Word> hanging = Optional.empty();

    public RememberingHat(final Hat origin) {
        this.origin = origin;
    }

    /**
     * Pulls a word from the hat and remembers it, replacing any previously held
     * word.
     *
     * @return Pulled word if present, or empty if the hat is empty.
     */
    public Optional<Word> pull() {
        this.hanging = this.origin.pull();
        return this.hanging;
    }

    /**
     * Puts the currently remembered word back into the hat and clears it.
     */
    public void undoPull() {
        this.hanging.ifPresent(
            this.origin::put
        );
        this.hanging = Optional.empty();
    }
}
