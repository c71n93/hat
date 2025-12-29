package hat.ui.input;

import android.widget.EditText;

public abstract class EditTextInput<T> implements Input<T> {
    protected final EditText txt;

    @Override
    public void markError(final String msg) {
        this.txt.setError(msg);
    }

    public EditTextInput(final EditText txt) {
        this.txt = txt;
    }

    @Override
    public void clear() {
        this.txt.setText("");
    }
}
