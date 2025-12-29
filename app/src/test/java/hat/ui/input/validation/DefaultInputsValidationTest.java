package hat.ui.input.validation;

import hat.ui.input.Result;
import hat.ui.input.validation.fakes.FakeRecordingError;
import hat.ui.input.validation.fakes.FakeRecordingInput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

public final class DefaultInputsValidationTest {
    @Test
    public void validatedReturnsValuesWhenAllValid() {
        final FakeRecordingInput<Integer> first = new FakeRecordingInput<>(Result.ok(1));
        final FakeRecordingInput<Integer> second = new FakeRecordingInput<>(Result.ok(2));
        final Optional<List<Integer>> values = new DefaultInputsValidation<>(first, second).validated();
        Assert.assertTrue("All valid inputs should be present", values.isPresent());
        Assert.assertEquals("Values should match", List.of(1, 2), values.get());
        Assert.assertTrue("First input should have no errors", first.markedError().isEmpty());
        Assert.assertTrue("Second input should have no errors", second.markedError().isEmpty());
    }

    @Test
    public void validatedMarksErrorsAndReturnsEmpty() {
        final FakeRecordingError error = new FakeRecordingError();
        final FakeRecordingInput<Integer> invalid = new FakeRecordingInput<>(Result.err(error));
        final FakeRecordingInput<Integer> valid = new FakeRecordingInput<>(Result.ok(5));
        final Optional<List<Integer>> values = new DefaultInputsValidation<>(invalid, valid).validated();
        Assert.assertFalse("Invalid input should make result empty", values.isPresent());
        Assert.assertEquals("Error should be shown once", 1, error.timesShown());
        Assert.assertTrue("Valid input should have no errors", valid.markedError().isEmpty());
    }

    @Test
    public void ifValidOrExecutesActionWithValues() {
        final FakeRecordingInput<Integer> first = new FakeRecordingInput<>(Result.ok(3));
        final FakeRecordingInput<Integer> second = new FakeRecordingInput<>(Result.ok(4));
        final List<List<Integer>> collected = new ArrayList<>();
        new DefaultInputsValidation<>(first, second).ifValidOr(collected::add);
        Assert.assertEquals("Action should be called once", 1, collected.size());
        Assert.assertEquals("Action should receive values", List.of(3, 4), collected.get(0));
    }
}
