package com.c71n93.hat.ui.input.validation;

import com.c71n93.hat.ui.input.Input;
import com.c71n93.hat.ui.input.Result;
import com.c71n93.hat.ui.input.error.InputError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Validates inputs and additionally enforces that all successful values are
 * different.
 */
public class DifferentInputsValidation<T> implements InputsValidation<T> {
    private final List<Input<T>> inputs;

    @SafeVarargs
    public DifferentInputsValidation(final Input<T>... inputs) {
        this.inputs = Arrays.asList(inputs);
    }

    @Override
    public void ifValidOr(final Consumer<List<T>> action) {
        this.validated().ifPresent(action);
    }

    @Override
    public Optional<List<T>> validated() {
        // TODO: why the fuck is this so complicated?
        final List<Entry<T>> validated = new ArrayList<>();
        boolean allValid = true;
        for (final Input<T> input : this.inputs) {
            final Result<T, InputError> result = input.result();
            switch (result) {
                case Result.Ok<T, InputError> ok -> validated.add(new Entry<>(input, ok.value()));
                case Result.Err<T, InputError> err -> {
                    err.error().show();
                    allValid = false;
                }
            }
        }
        if (!allValid) {
            return Optional.empty();
        }
        final Map<T, List<Input<T>>> buckets = new HashMap<>();
        for (final Entry<T> entry : validated) {
            buckets.computeIfAbsent(entry.value, key -> new ArrayList<>()).add(entry.input);
        }
        boolean hasDuplicates = false;
        for (final List<Input<T>> bucket : buckets.values()) {
            if (bucket.size() > 1) {
                hasDuplicates = true;
                bucket.forEach(input -> input.markError("Should not be duplicated"));
            }
        }
        if (!hasDuplicates) {
            final List<T> values = new ArrayList<>(validated.size());
            validated.forEach(entry -> values.add(entry.value));
            return Optional.of(values);
        } else {
            return Optional.empty();
        }
    }

    private record Entry<T>(Input<T> input, T value) {
    }
}
