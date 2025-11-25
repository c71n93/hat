package com.c71n93.hat.ui.inputs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Aggregates multiple inputs and performs an action only when all inputs are
 * valid.
 */
public class ValidatedInputs<T> {
    private final List<Input<T>> inputs;
    public ValidatedInputs(final Input<T>... inputs) {
        this.inputs = Arrays.asList(inputs);
    }

    /**
     * Validates all inputs: executes action when every input has a value, otherwise
     * marks errors.
     *
     * @param action
     *            action when all inputs are valid
     * @param error
     *            error message used for invalid inputs
     */
    public void allValidOrError(final Consumer<List<T>> action, final String error) {
        final List<T> values = new ArrayList<>();
        boolean allValid = true;
        for (final Input<T> input : this.inputs) {
            final Optional<T> value = input.value();
            if (value.isPresent()) {
                values.add(value.get());
            } else {
                input.error(error);
                allValid = false;
            }
        }
        if (allValid) {
            action.accept(values);
        }
    }
}
