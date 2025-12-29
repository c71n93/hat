package hat.ui.input;

import android.widget.EditText;

import hat.ui.input.error.EditTextInputError;
import hat.ui.input.error.InputError;

public class EditStringInput extends EditTextInput<String> {
    public EditStringInput(final EditText txt) {
        super(txt);
    }

    @Override
    public Result<String, InputError> result() {
        final String text = this.txt.getText().toString();
        return text.isEmpty() ? Result.err(new EditTextInputError(this.txt, "Should not be empty")) : Result.ok(text);
    }
}
