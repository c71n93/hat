package hat.ui.input.validation;

import hat.ui.input.Result;
import hat.ui.input.validation.fakes.FakeRecordingError;
import hat.ui.input.validation.fakes.FakeRecordingInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

public final class DifferentInputsValidationTest {
    @Test
    public void validatedReturnsValuesWhenUnique() {
        final FakeRecordingInput<String> first = new FakeRecordingInput<>(Result.ok("alpha"));
        final FakeRecordingInput<String> second = new FakeRecordingInput<>(Result.ok("beta"));
        final Optional<List<String>> result = new DifferentInputsValidation<>(first, second).validated();
        Assert.assertTrue("Valid unique inputs should be present", result.isPresent());
        Assert.assertEquals("Values should match", List.of("alpha", "beta"), result.get());
        Assert.assertTrue("First input should have no errors", first.markedError().isEmpty());
        Assert.assertTrue("Second input should have no errors", second.markedError().isEmpty());
    }

    @Test
    public void marksDuplicatesAndReturnsEmpty() {
        final FakeRecordingInput<String> first = new FakeRecordingInput<>(Result.ok("same"));
        final FakeRecordingInput<String> second = new FakeRecordingInput<>(Result.ok("same"));
        final Optional<List<String>> result = new DifferentInputsValidation<>(first, second).validated();
        Assert.assertFalse("Duplicates should produce empty result", result.isPresent());
        Assert.assertEquals(
            "First input should be marked duplicate", Optional.of("Should not be duplicated"), first.markedError()
        );
        Assert.assertEquals(
            "Second input should be marked duplicate", Optional.of("Should not be duplicated"), second.markedError()
        );
    }

    @Test
    public void stopsOnUnderlyingValidationError() {
        final FakeRecordingError error = new FakeRecordingError();
        final FakeRecordingInput<String> invalid = new FakeRecordingInput<>(Result.err(error));
        final FakeRecordingInput<String> valid = new FakeRecordingInput<>(Result.ok("value"));
        final Optional<List<String>> result = new DifferentInputsValidation<>(invalid, valid).validated();
        Assert.assertFalse("Underlying error should stop validation", result.isPresent());
        Assert.assertEquals("Error should be shown once", 1, error.timesShown());
        Assert.assertTrue("Valid input should have no errors", valid.markedError().isEmpty());
    }

    @Test
    public void ifValidOrSkipsActionWhenDuplicatesPresent() {
        final FakeRecordingInput<String> first = new FakeRecordingInput<>(Result.ok("d"));
        final FakeRecordingInput<String> second = new FakeRecordingInput<>(Result.ok("d"));
        final List<List<String>> collected = new ArrayList<>();
        new DifferentInputsValidation<>(first, second).ifValidOr(collected::add);
        Assert.assertTrue("Action should not run when duplicates exist", collected.isEmpty());
    }
}
