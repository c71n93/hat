package com.c71n93.hat.ui.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.text.Editable;
import android.widget.EditText;

import com.c71n93.hat.ui.input.error.InputError;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Unit tests for {@link EditIntInput}.
 */
public class EditIntInputTest {
    private EditText mockEditText;
    private Editable mockEditable;
    private EditIntInput input;
    @Before
    public void setUp() {
        mockEditText = mock(EditText.class);
        mockEditable = mock(Editable.class);
        when(mockEditText.getText()).thenReturn(mockEditable);
        input = new EditIntInput(mockEditText);
    }
    @Test
    public void result_withValidInteger_returnsOk() {
        when(mockEditable.toString()).thenReturn("42");
        final Result<Integer, InputError> result = input.result();
        assertTrue(result.isOk());
        assertEquals(Integer.valueOf(42), result.unwrap());
    }
    @Test
    public void result_withNegativeInteger_returnsOk() {
        when(mockEditable.toString()).thenReturn("-10");
        final Result<Integer, InputError> result = input.result();
        assertTrue(result.isOk());
        assertEquals(Integer.valueOf(-10), result.unwrap());
    }
    @Test
    public void result_withZero_returnsOk() {
        when(mockEditable.toString()).thenReturn("0");
        final Result<Integer, InputError> result = input.result();
        assertTrue(result.isOk());
        assertEquals(Integer.valueOf(0), result.unwrap());
    }
    @Test
    public void result_withMaxInteger_returnsOk() {
        final String maxInt = String.valueOf(Integer.MAX_VALUE);
        when(mockEditable.toString()).thenReturn(maxInt);
        final Result<Integer, InputError> result = input.result();
        assertTrue(result.isOk());
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), result.unwrap());
    }
    @Test
    public void result_withMinInteger_returnsOk() {
        final String minInt = String.valueOf(Integer.MIN_VALUE);
        when(mockEditable.toString()).thenReturn(minInt);
        final Result<Integer, InputError> result = input.result();
        assertTrue(result.isOk());
        assertEquals(Integer.valueOf(Integer.MIN_VALUE), result.unwrap());
    }
    @Test
    public void result_withLeadingAndTrailingWhitespace_returnsOk() {
        when(mockEditable.toString()).thenReturn("  123  ");
        final Result<Integer, InputError> result = input.result();
        assertTrue(result.isOk());
        assertEquals(Integer.valueOf(123), result.unwrap());
    }
    @Test
    public void result_withEmptyText_returnsErr() {
        when(mockEditable.toString()).thenReturn("");
        final Result<Integer, InputError> result = input.result();
        assertTrue(result.isErr());
    }
    @Test
    public void result_withWhitespaceOnly_returnsErr() {
        when(mockEditable.toString()).thenReturn("   ");
        final Result<Integer, InputError> result = input.result();
        assertTrue(result.isErr());
    }
    @Test
    public void result_withNonNumericText_returnsErr() {
        when(mockEditable.toString()).thenReturn("abc");
        final Result<Integer, InputError> result = input.result();
        assertTrue(result.isErr());
    }
    @Test
    public void result_withDecimalNumber_returnsErr() {
        when(mockEditable.toString()).thenReturn("12.34");
        final Result<Integer, InputError> result = input.result();
        assertTrue(result.isErr());
    }
    @Test
    public void result_withPartiallyNumericText_returnsErr() {
        when(mockEditable.toString()).thenReturn("12abc");
        final Result<Integer, InputError> result = input.result();
        assertTrue(result.isErr());
    }
    @Test
    public void result_withNumberTooLarge_returnsErr() {
        when(mockEditable.toString()).thenReturn("9999999999999999999");
        final Result<Integer, InputError> result = input.result();
        assertTrue(result.isErr());
    }
    @Test
    public void markError_callsSetErrorOnEditText() {
        final String errorMessage = "Error message";
        input.markError(errorMessage);
        verify(mockEditText).setError(errorMessage);
    }
    @Test
    public void validOrError_withValidInput_executesAction() {
        when(mockEditable.toString()).thenReturn("100");
        final AtomicBoolean actionCalled = new AtomicBoolean(false);
        final AtomicReference<Integer> capturedValue = new AtomicReference<>();
        input.validOrError(value -> {
            actionCalled.set(true);
            capturedValue.set(value);
        });
        assertTrue(actionCalled.get());
        assertEquals(Integer.valueOf(100), capturedValue.get());
    }
    @Test
    public void validOrError_withEmptyInput_showsError() {
        when(mockEditable.toString()).thenReturn("");
        final AtomicBoolean actionCalled = new AtomicBoolean(false);
        input.validOrError(value -> actionCalled.set(true));
        assertFalse(actionCalled.get());
        verify(mockEditText).setError("Should not be empty.");
    }
    @Test
    public void validOrError_withInvalidNumber_showsError() {
        when(mockEditable.toString()).thenReturn("abc");
        final AtomicBoolean actionCalled = new AtomicBoolean(false);
        input.validOrError(value -> actionCalled.set(true));
        assertFalse(actionCalled.get());
        verify(mockEditText).setError("Please enter correct number.");
    }
    @Test
    public void result_multipleCallsWithSameState_returnsSameValue() {
        when(mockEditable.toString()).thenReturn("42");
        final Result<Integer, InputError> result1 = input.result();
        final Result<Integer, InputError> result2 = input.result();
        assertTrue(result1.isOk());
        assertTrue(result2.isOk());
        assertEquals(result1.unwrap(), result2.unwrap());
    }
    @Test
    public void result_multipleCallsWithDifferentState_returnsDifferentValues() {
        when(mockEditable.toString()).thenReturn("10");
        final Result<Integer, InputError> result1 = input.result();
        when(mockEditable.toString()).thenReturn("20");
        final Result<Integer, InputError> result2 = input.result();
        assertTrue(result1.isOk());
        assertTrue(result2.isOk());
        assertEquals(Integer.valueOf(10), result1.unwrap());
        assertEquals(Integer.valueOf(20), result2.unwrap());
    }
    @Test
    public void result_withPlusSign_returnsOk() {
        when(mockEditable.toString()).thenReturn("+42");
        final Result<Integer, InputError> result = input.result();
        assertTrue(result.isOk());
        assertEquals(Integer.valueOf(42), result.unwrap());
    }
}
