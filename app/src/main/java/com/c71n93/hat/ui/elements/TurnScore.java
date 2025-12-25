package com.c71n93.hat.ui.elements;

import android.widget.TextView;
import com.c71n93.hat.R;

/**
 * Track the score and renders it to a given view.
 */
public final class TurnScore implements DrawableToView<TextView> {
    private int score;

    public TurnScore() {
        this.score = 0;
    }

    public int score() {
        return this.score;
    }

    public void increment() {
        this.score++;
    }

    public void incrementAndDraw(final TextView view) {
        this.increment();
        this.draw(view);
    }

    @Override
    public void draw(final TextView view) {
        view.setText(view.getContext().getString(R.string.label_turn_score, this.score));
    }
}
