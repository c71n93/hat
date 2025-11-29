package com.c71n93.hat.ui.input;

import android.widget.EditText;

public abstract class EditTextInput<T> implements Input<T> {
    protected final EditText txt;

    @Override
    public void markError(String msg) {
        this.txt.setError(msg);
    }

    public EditTextInput(final EditText txt) {
        this.txt = txt;
    }
}
