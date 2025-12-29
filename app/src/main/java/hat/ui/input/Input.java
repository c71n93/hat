package hat.ui.input;

import hat.ui.input.error.InputError;

import java.util.function.Consumer;

public interface Input<T> {
    Result<T, InputError> result();
    void markError(String msg);
    void clear();

    default void ifValidOrMarkError(final Consumer<T> action) {
        this.result().ifOkOrElse(action, error -> error.show());
    }
}
