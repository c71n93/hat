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
 * Unit tests for {@link EditStringInput}.
 */
public class EditStringInputTest {
    private EditText mockEditText;
    private Editable mockEditable;
    private EditStringInput input;
    @Before
    public void setUp() {
        mockEditText = mock(EditText.class);
        mockEditable = mock(Editable.class);
        when(mockEditText.getText()).thenReturn(mockEditable);
        input = new EditStringInput(mockEditText);
    }
    @Test
    public void result_withNonEmptyText_returnsOk() {
        when(mockEditable.toString()).thenReturn("valid text");
        final Result<String, InputError> result = input.result();
        assertTrue(result.isOk());
        assertEquals("valid text", result.unwrap());
    }
    @Test
    public void result_withEmptyText_returnsErr() {
        when(mockEditable.toString()).thenReturn("");
        final Result<String, InputError> result = input.result();
        assertTrue(result.isErr());
    }
    @Test
    public void result_withWhitespaceText_returnsOk() {
        when(mockEditable.toString()).thenReturn("  whitespace  ");
        final Result<String, InputError> result = input.result();
        assertTrue(result.isOk());
        assertEquals("  whitespace  ", result.unwrap());
    }
    @Test
    public void result_withSingleCharacter_returnsOk() {
        when(mockEditable.toString()).thenReturn("a");
        final Result<String, InputError> result = input.result();
        assertTrue(result.isOk());
        assertEquals("a", result.unwrap());
    }
    @Test
    public void markError_callsSetErrorOnEditText() {
        final String errorMessage = "Error message";
        input.markError(errorMessage);
        verify(mockEditText).setError(errorMessage);
    }
    @Test
    public void validOrError_withValidInput_executesAction() {
        when(mockEditable.toString()).thenReturn("valid");
        final AtomicBoolean actionCalled = new AtomicBoolean(false);
        final AtomicReference<String> capturedValue = new AtomicReference<>();
        input.validOrError(value -> {
            actionCalled.set(true);
            capturedValue.set(value);
        });
        assertTrue(actionCalled.get());
        assertEquals("valid", capturedValue.get());
    }
    @Test
    public void validOrError_withEmptyInput_showsError() {
        when(mockEditable.toString()).thenReturn("");
        final AtomicBoolean actionCalled = new AtomicBoolean(false);
        input.validOrError(value -> actionCalled.set(true));
        assertFalse(actionCalled.get());
        verify(mockEditText).setError("Should not be empty");
    }
    @Test
    public void result_multipleCallsWithSameState_returnsSameValue() {
        when(mockEditable.toString()).thenReturn("test");
        final Result<String, InputError> result1 = input.result();
        final Result<String, InputError> result2 = input.result();
        assertTrue(result1.isOk());
        assertTrue(result2.isOk());
        assertEquals(result1.unwrap(), result2.unwrap());
    }
    @Test
    public void result_multipleCallsWithDifferentState_returnsDifferentValues() {
        when(mockEditable.toString()).thenReturn("first");
        final Result<String, InputError> result1 = input.result();
        when(mockEditable.toString()).thenReturn("second");
        final Result<String, InputError> result2 = input.result();
        assertTrue(result1.isOk());
        assertTrue(result2.isOk());
        assertEquals("first", result1.unwrap());
        assertEquals("second", result2.unwrap());
    }
}
