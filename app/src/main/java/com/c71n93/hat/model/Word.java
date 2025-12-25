package com.c71n93.hat.model;

import android.widget.TextView;
import com.c71n93.hat.ui.elements.DrawableToView;

public class Word implements DrawableToView<TextView> {
    private final String value;
    public Word(final String value) {
        this.value = value;
    }
    public String value() {
        return this.value;
    }

    @Override
    public void draw(final TextView view) {
        view.setText(this.value);
    }
}
