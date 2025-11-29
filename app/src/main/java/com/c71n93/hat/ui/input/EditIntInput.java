package com.c71n93.hat.ui.input;

import android.widget.EditText;

import com.c71n93.hat.ui.input.error.EditTextInputError;
import com.c71n93.hat.ui.input.error.InputError;

public class EditIntInput extends EditTextInput<Integer> {
    public EditIntInput(final EditText txt) {
        super(txt);
    }

    @Override
    public Result<Integer, InputError> result() {
        final String text = this.txt.getText().toString().trim();
        try {
            // TODO: error message strings may be duplicated. To figure out how to use
            // Fragment::getString method from here to access R.string.some_str.
            return text.isEmpty()
                    ? Result.err(new EditTextInputError(this.txt, "Should not be empty."))
                    : Result.ok(Integer.valueOf(text));
        } catch (final NumberFormatException ignored) {
            return Result.err(new EditTextInputError(txt, "Please enter correct number."));
        }
    }
}
