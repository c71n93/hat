package com.c71n93.hat.ui.input.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.c71n93.hat.ui.input.Result;
import com.c71n93.hat.ui.input.validation.fakes.FakeRecordingError;
import com.c71n93.hat.ui.input.validation.fakes.FakeRecordingInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

public final class DefaultInputsValidationTest {
    @Test
    public void validatedReturnsValuesWhenAllValid() {
        final FakeRecordingInput<Integer> first = new FakeRecordingInput<>(Result.ok(1));
        final FakeRecordingInput<Integer> second = new FakeRecordingInput<>(Result.ok(2));
        final Optional<List<Integer>> values = new DefaultInputsValidation<>(first, second).validated();
        assertTrue(values.isPresent());
        assertEquals(List.of(1, 2), values.get());
        assertTrue(first.markedError().isEmpty());
        assertTrue(second.markedError().isEmpty());
    }

    @Test
    public void validatedMarksErrorsAndReturnsEmpty() {
        final FakeRecordingError error = new FakeRecordingError();
        final FakeRecordingInput<Integer> invalid = new FakeRecordingInput<>(Result.err(error));
        final FakeRecordingInput<Integer> valid = new FakeRecordingInput<>(Result.ok(5));
        final Optional<List<Integer>> values = new DefaultInputsValidation<>(invalid, valid).validated();
        assertFalse(values.isPresent());
        assertEquals(1, error.timesShown());
        assertTrue(valid.markedError().isEmpty());
    }

    @Test
    public void ifValidOrExecutesActionWithValues() {
        final FakeRecordingInput<Integer> first = new FakeRecordingInput<>(Result.ok(3));
        final FakeRecordingInput<Integer> second = new FakeRecordingInput<>(Result.ok(4));
        final List<List<Integer>> collected = new ArrayList<>();
        new DefaultInputsValidation<>(first, second).ifValidOr(collected::add);
        assertEquals(1, collected.size());
        assertEquals(List.of(3, 4), collected.get(0));
    }
}
