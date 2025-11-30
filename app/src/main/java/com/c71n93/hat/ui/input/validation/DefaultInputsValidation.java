package com.c71n93.hat.ui.input.validation;

import com.c71n93.hat.ui.input.Input;
import com.c71n93.hat.ui.input.Result;
import com.c71n93.hat.ui.input.error.InputError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Default implementation of inputs validation.
 */
public class DefaultInputsValidation<T> implements InputsValidation<T> {
    private final List<Input<T>> inputs;

    @SafeVarargs
    public DefaultInputsValidation(final Input<T>... inputs) {
        this.inputs = Arrays.asList(inputs);
    }

    @Override
    public void ifValidOr(final Consumer<List<T>> action) {
        this.validated().ifPresent(
                action
        );
    }

    @Override
    public Optional<List<T>> validated() {
        final List<T> values = new ArrayList<>();
        boolean allValid = true;
        for (final Input<T> input : this.inputs) {
            final Result<T, InputError> result = input.result();
            switch (result) {
                case Result.Ok<T, InputError> ok -> values.add(ok.value());
                case Result.Err<T, InputError> err -> {
                    err.error().show();
                    allValid = false;
                }
                default -> throw new IllegalStateException("Unknown result type: " + result.getClass());
            }
        }
        return allValid ? Optional.of(values) : Optional.empty();
    }
}
