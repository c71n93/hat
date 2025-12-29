package hat.ui.input.validation.fakes;

import hat.ui.input.Input;
import hat.ui.input.Result;
import hat.ui.input.error.InputError;
import java.util.Optional;

public final class FakeRecordingInput<T> implements Input<T> {
    private final Result<T, InputError> next;
    private Optional<String> marked = Optional.empty();
    private boolean cleared;

    public FakeRecordingInput(final Result<T, InputError> next) {
        this.next = next;
    }

    @Override
    public Result<T, InputError> result() {
        return this.next;
    }

    @Override
    public void markError(final String msg) {
        this.marked = Optional.of(msg);
    }

    @Override
    public void clear() {
        this.marked = Optional.empty();
        this.cleared = true;
    }

    public Optional<String> markedError() {
        return this.marked;
    }

    public boolean cleared() {
        return this.cleared;
    }
}
