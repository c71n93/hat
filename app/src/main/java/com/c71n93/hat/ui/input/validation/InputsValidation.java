package com.c71n93.hat.ui.input.validation;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Aggregates multiple inputs. Responsible for validation and marking errors, if
 * input is not valid.
 */
public interface InputsValidation<T> {
    /**
     * Validates all inputs: executes action when every input has a value, otherwise
     * marks errors.
     *
     * @param action
     *            action when all inputs are valid
     */
    void ifValidOr(final Consumer<List<T>> action);

    /**
     * Validates all inputs: returns value for every validated inputs if all valid,
     * otherwise marks error and returns {@code Optional.empty()}.
     */
    Optional<List<T>> validated();
}
