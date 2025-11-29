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

public final class DifferentInputsValidationTest {
    @Test
    public void validatedReturnsValuesWhenUnique() {
        final FakeRecordingInput<String> first = new FakeRecordingInput<>(Result.ok("alpha"));
        final FakeRecordingInput<String> second = new FakeRecordingInput<>(Result.ok("beta"));
        final Optional<List<String>> result = new DifferentInputsValidation<>(first, second).validated();
        assertTrue(result.isPresent());
        assertEquals(List.of("alpha", "beta"), result.get());
        assertTrue(first.markedError().isEmpty());
        assertTrue(second.markedError().isEmpty());
    }

    @Test
    public void marksDuplicatesAndReturnsEmpty() {
        final FakeRecordingInput<String> first = new FakeRecordingInput<>(Result.ok("same"));
        final FakeRecordingInput<String> second = new FakeRecordingInput<>(Result.ok("same"));
        final Optional<List<String>> result = new DifferentInputsValidation<>(first, second).validated();
        assertFalse(result.isPresent());
        assertEquals(Optional.of("Should not be duplicated"), first.markedError());
        assertEquals(Optional.of("Should not be duplicated"), second.markedError());
    }

    @Test
    public void stopsOnUnderlyingValidationError() {
        final FakeRecordingError error = new FakeRecordingError();
        final FakeRecordingInput<String> invalid = new FakeRecordingInput<>(Result.err(error));
        final FakeRecordingInput<String> valid = new FakeRecordingInput<>(Result.ok("value"));
        final Optional<List<String>> result = new DifferentInputsValidation<>(invalid, valid).validated();
        assertFalse(result.isPresent());
        assertEquals(1, error.timesShown());
        assertTrue(valid.markedError().isEmpty());
    }

    @Test
    public void ifValidOrSkipsActionWhenDuplicatesPresent() {
        final FakeRecordingInput<String> first = new FakeRecordingInput<>(Result.ok("d"));
        final FakeRecordingInput<String> second = new FakeRecordingInput<>(Result.ok("d"));
        final List<List<String>> collected = new ArrayList<>();
        new DifferentInputsValidation<>(first, second).ifValidOr(collected::add);
        assertTrue(collected.isEmpty());
    }
}
