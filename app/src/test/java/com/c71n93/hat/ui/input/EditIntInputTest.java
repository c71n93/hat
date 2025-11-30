package com.c71n93.hat.ui.input;

import android.text.Editable;
import android.widget.EditText;
import com.c71n93.hat.ui.input.error.InputError;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests for {@link EditIntInput}.
 */
public class EditIntInputTest {
    private final EditText mockEditText = Mockito.mock(EditText.class);
    private final Editable mockEditable = Mockito.mock(Editable.class);
    private final EditIntInput input = new EditIntInput(this.mockEditText);
    @Before
    public void setUp() {
        Mockito.when(this.mockEditText.getText()).thenReturn(this.mockEditable);
    }
    @Test
    public void result_withValidInteger_returnsOk() {
        Mockito.when(this.mockEditable.toString()).thenReturn("42");
        final Result<Integer, InputError> result = this.input.result();
        Assert.assertTrue("Valid integer should be ok", result.isOk());
        Assert.assertEquals("Should unwrap parsed integer", Integer.valueOf(42), result.unwrap());
    }
    @Test
    public void result_withNegativeInteger_returnsOk() {
        Mockito.when(this.mockEditable.toString()).thenReturn("-10");
        final Result<Integer, InputError> result = this.input.result();
        Assert.assertTrue("Negative integer should be ok", result.isOk());
        Assert.assertEquals("Should unwrap negative integer", Integer.valueOf(-10), result.unwrap());
    }
    @Test
    public void result_withZero_returnsOk() {
        Mockito.when(this.mockEditable.toString()).thenReturn("0");
        final Result<Integer, InputError> result = this.input.result();
        Assert.assertTrue("Zero should be ok", result.isOk());
        Assert.assertEquals("Should unwrap zero", Integer.valueOf(0), result.unwrap());
    }
    @Test
    public void result_withMaxInteger_returnsOk() {
        final String maxInt = String.valueOf(Integer.MAX_VALUE);
        Mockito.when(this.mockEditable.toString()).thenReturn(maxInt);
        final Result<Integer, InputError> result = this.input.result();
        Assert.assertTrue("Max integer should be ok", result.isOk());
        Assert.assertEquals("Should unwrap max integer", Integer.valueOf(Integer.MAX_VALUE), result.unwrap());
    }
    @Test
    public void result_withMinInteger_returnsOk() {
        final String minInt = String.valueOf(Integer.MIN_VALUE);
        Mockito.when(this.mockEditable.toString()).thenReturn(minInt);
        final Result<Integer, InputError> result = this.input.result();
        Assert.assertTrue("Min integer should be ok", result.isOk());
        Assert.assertEquals("Should unwrap min integer", Integer.valueOf(Integer.MIN_VALUE), result.unwrap());
    }
    @Test
    public void result_withLeadingAndTrailingWhitespace_returnsOk() {
        Mockito.when(this.mockEditable.toString()).thenReturn("  123  ");
        final Result<Integer, InputError> result = this.input.result();
        Assert.assertTrue("Trimmed integer should be ok", result.isOk());
        Assert.assertEquals("Should parse trimmed integer", Integer.valueOf(123), result.unwrap());
    }
    @Test
    public void result_withEmptyText_returnsErr() {
        Mockito.when(this.mockEditable.toString()).thenReturn("");
        final Result<Integer, InputError> result = this.input.result();
        Assert.assertTrue("Empty text should be error", result.isErr());
    }
    @Test
    public void result_withWhitespaceOnly_returnsErr() {
        Mockito.when(this.mockEditable.toString()).thenReturn("   ");
        final Result<Integer, InputError> result = this.input.result();
        Assert.assertTrue("Whitespace should be error", result.isErr());
    }
    @Test
    public void result_withNonNumericText_returnsErr() {
        Mockito.when(this.mockEditable.toString()).thenReturn("abc");
        final Result<Integer, InputError> result = this.input.result();
        Assert.assertTrue("Non-numeric text should be error", result.isErr());
    }
    @Test
    public void result_withDecimalNumber_returnsErr() {
        Mockito.when(this.mockEditable.toString()).thenReturn("12.34");
        final Result<Integer, InputError> result = this.input.result();
        Assert.assertTrue("Decimal number should be error", result.isErr());
    }
    @Test
    public void result_withPartiallyNumericText_returnsErr() {
        Mockito.when(this.mockEditable.toString()).thenReturn("12abc");
        final Result<Integer, InputError> result = this.input.result();
        Assert.assertTrue("Partially numeric text should be error", result.isErr());
    }
    @Test
    public void result_withNumberTooLarge_returnsErr() {
        Mockito.when(this.mockEditable.toString()).thenReturn("9999999999999999999");
        final Result<Integer, InputError> result = this.input.result();
        Assert.assertTrue("Overflowing number should be error", result.isErr());
    }
    @Test
    public void markError_callsSetErrorOnEditText() {
        final String errorMessage = "Error message";
        this.input.markError(errorMessage);
        Mockito.verify(this.mockEditText).setError(errorMessage);
    }
    @Test
    public void validOrError_withValidInput_executesAction() {
        Mockito.when(this.mockEditable.toString()).thenReturn("100");
        final AtomicBoolean actionCalled = new AtomicBoolean(false);
        final AtomicReference<Integer> capturedValue = new AtomicReference<>();
        this.input.ifValidOrMarkError(value -> {
            actionCalled.set(true);
            capturedValue.set(value);
        });
        Assert.assertTrue("Action should be called for valid input", actionCalled.get());
        Assert.assertEquals("Captured value should match input", Integer.valueOf(100), capturedValue.get());
    }
    @Test
    public void validOrError_withEmptyInput_showsError() {
        Mockito.when(this.mockEditable.toString()).thenReturn("");
        final AtomicBoolean actionCalled = new AtomicBoolean(false);
        this.input.ifValidOrMarkError(value -> actionCalled.set(true));
        Assert.assertFalse("Action should not run for empty input", actionCalled.get());
        Mockito.verify(this.mockEditText).setError("Should not be empty.");
    }
    @Test
    public void validOrError_withInvalidNumber_showsError() {
        Mockito.when(this.mockEditable.toString()).thenReturn("abc");
        final AtomicBoolean actionCalled = new AtomicBoolean(false);
        this.input.ifValidOrMarkError(value -> actionCalled.set(true));
        Assert.assertFalse("Action should not run for invalid number", actionCalled.get());
        Mockito.verify(this.mockEditText).setError("Please enter correct number.");
    }
    @Test
    public void result_multipleCallsWithSameState_returnsSameValue() {
        Mockito.when(this.mockEditable.toString()).thenReturn("42");
        final Result<Integer, InputError> result1 = this.input.result();
        final Result<Integer, InputError> result2 = this.input.result();
        Assert.assertTrue("First call should be ok", result1.isOk());
        Assert.assertTrue("Second call should be ok", result2.isOk());
        Assert.assertEquals("Unwrapped values should match", result1.unwrap(), result2.unwrap());
    }
    @Test
    public void result_multipleCallsWithDifferentState_returnsDifferentValues() {
        Mockito.when(this.mockEditable.toString()).thenReturn("10");
        final Result<Integer, InputError> result1 = this.input.result();
        Mockito.when(this.mockEditable.toString()).thenReturn("20");
        final Result<Integer, InputError> result2 = this.input.result();
        Assert.assertTrue("First call should be ok", result1.isOk());
        Assert.assertTrue("Second call should be ok", result2.isOk());
        Assert.assertEquals("First value should match stub", Integer.valueOf(10), result1.unwrap());
        Assert.assertEquals("Second value should match stub", Integer.valueOf(20), result2.unwrap());
    }
    @Test
    public void result_withPlusSign_returnsOk() {
        Mockito.when(this.mockEditable.toString()).thenReturn("+42");
        final Result<Integer, InputError> result = this.input.result();
        Assert.assertTrue("Plus sign should be allowed", result.isOk());
        Assert.assertEquals("Should unwrap parsed integer", Integer.valueOf(42), result.unwrap());
    }
}
