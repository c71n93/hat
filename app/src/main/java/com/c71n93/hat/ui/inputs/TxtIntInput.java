package com.c71n93.hat.ui.inputs;

import android.widget.EditText;
import java.util.Optional;

public class TxtIntInput implements Input<Integer> {
    private final EditText txt;

    public TxtIntInput(final EditText txt) {
        this.txt = txt;
    }

    @Override
    public Optional<Integer> value() {
        final String text = this.txt.getText().toString().trim();
        try {
            return text.isEmpty() ? Optional.empty() : Optional.of(Integer.valueOf(text));
        } catch (final NumberFormatException ignored) {
            return Optional.empty();
        }
    }

    @Override
    public void error(final String message) {
        this.txt.setError(message);
    }
}
