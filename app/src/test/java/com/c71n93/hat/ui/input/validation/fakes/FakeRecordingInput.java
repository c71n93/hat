package com.c71n93.hat.ui.input.validation.fakes;

import com.c71n93.hat.ui.input.Input;
import com.c71n93.hat.ui.input.Result;
import com.c71n93.hat.ui.input.error.InputError;
import java.util.Optional;

public final class FakeRecordingInput<T> implements Input<T> {
    private final Result<T, InputError> next;
    private Optional<String> marked = Optional.empty();

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

    public Optional<String> markedError() {
        return this.marked;
    }
}
