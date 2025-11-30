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
 * Unit tests for {@link EditStringInput}.
 */
public class EditStringInputTest {
    private final EditText mockEditText = Mockito.mock(EditText.class);
    private final Editable mockEditable = Mockito.mock(Editable.class);
    private final EditStringInput input = new EditStringInput(this.mockEditText);
    @Before
    public void setUp() {
        Mockito.when(this.mockEditText.getText()).thenReturn(this.mockEditable);
    }
    @Test
    public void result_withNonEmptyText_returnsOk() {
        Mockito.when(this.mockEditable.toString()).thenReturn("valid text");
        final Result<String, InputError> result = this.input.result();
        Assert.assertTrue("Non-empty text should be ok", result.isOk());
        Assert.assertEquals("Should return original text", "valid text", result.unwrap());
    }
    @Test
    public void result_withEmptyText_returnsErr() {
        Mockito.when(this.mockEditable.toString()).thenReturn("");
        final Result<String, InputError> result = this.input.result();
        Assert.assertTrue("Empty text should be error", result.isErr());
    }
    @Test
    public void result_withWhitespaceText_returnsOk() {
        Mockito.when(this.mockEditable.toString()).thenReturn("  whitespace  ");
        final Result<String, InputError> result = this.input.result();
        Assert.assertTrue("Whitespace text should be ok", result.isOk());
        Assert.assertEquals("Should keep whitespace", "  whitespace  ", result.unwrap());
    }
    @Test
    public void result_withSingleCharacter_returnsOk() {
        Mockito.when(this.mockEditable.toString()).thenReturn("a");
        final Result<String, InputError> result = this.input.result();
        Assert.assertTrue("Single character should be ok", result.isOk());
        Assert.assertEquals("Should unwrap single character", "a", result.unwrap());
    }
    @Test
    public void markError_callsSetErrorOnEditText() {
        final String errorMessage = "Error message";
        this.input.markError(errorMessage);
        Mockito.verify(this.mockEditText).setError(errorMessage);
    }
    @Test
    public void validOrError_withValidInput_executesAction() {
        Mockito.when(this.mockEditable.toString()).thenReturn("valid");
        final AtomicBoolean actionCalled = new AtomicBoolean(false);
        final AtomicReference<String> capturedValue = new AtomicReference<>();
        this.input.ifValidOrMarkError(value -> {
            actionCalled.set(true);
            capturedValue.set(value);
        });
        Assert.assertTrue("Action should be called for valid input", actionCalled.get());
        Assert.assertEquals("Captured value should match input", "valid", capturedValue.get());
    }
    @Test
    public void validOrError_withEmptyInput_showsError() {
        Mockito.when(this.mockEditable.toString()).thenReturn("");
        final AtomicBoolean actionCalled = new AtomicBoolean(false);
        this.input.ifValidOrMarkError(value -> actionCalled.set(true));
        Assert.assertFalse("Action should not run for empty input", actionCalled.get());
        Mockito.verify(this.mockEditText).setError("Should not be empty");
    }
    @Test
    public void result_multipleCallsWithSameState_returnsSameValue() {
        Mockito.when(this.mockEditable.toString()).thenReturn("test");
        final Result<String, InputError> result1 = this.input.result();
        final Result<String, InputError> result2 = this.input.result();
        Assert.assertTrue("First call should be ok", result1.isOk());
        Assert.assertTrue("Second call should be ok", result2.isOk());
        Assert.assertEquals("Unwrapped values should match", result1.unwrap(), result2.unwrap());
    }
    @Test
    public void result_multipleCallsWithDifferentState_returnsDifferentValues() {
        Mockito.when(this.mockEditable.toString()).thenReturn("first");
        final Result<String, InputError> result1 = this.input.result();
        Mockito.when(this.mockEditable.toString()).thenReturn("second");
        final Result<String, InputError> result2 = this.input.result();
        Assert.assertTrue("First call should be ok", result1.isOk());
        Assert.assertTrue("Second call should be ok", result2.isOk());
        Assert.assertEquals("First value should match stub", "first", result1.unwrap());
        Assert.assertEquals("Second value should match stub", "second", result2.unwrap());
    }
}
