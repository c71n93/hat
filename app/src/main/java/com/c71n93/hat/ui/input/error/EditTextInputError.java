package com.c71n93.hat.ui.input.error;

import android.widget.EditText;

public class EditTextInputError implements InputError {
    private final EditText txt;
    private final String msg;

    public EditTextInputError(EditText txt, String msg) {
        this.txt = txt;
        this.msg = msg;
    }

    @Override
    public void show() {
        txt.setError(this.msg);
    }

    @Override
    public void show(String msg) {
        txt.setError(msg);
    }
}
