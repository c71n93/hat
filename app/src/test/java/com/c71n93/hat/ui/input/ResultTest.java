package com.c71n93.hat.ui.input;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Unit tests for {@link Result} sealed interface and its Ok/Err implementations.
 */
public class ResultTest {
    @Test
    public void okResult_isOk_returnsTrue() {
        final Result<String, String> result = Result.ok("success");
        assertTrue(result.isOk());
    }
    @Test
    public void okResult_isErr_returnsFalse() {
        final Result<String, String> result = Result.ok("success");
        assertFalse(result.isErr());
    }
    @Test
    public void okResult_unwrap_returnsValue() {
        final String expected = "success";
        final Result<String, String> result = Result.ok(expected);
        assertEquals(expected, result.unwrap());
    }
    @Test
    public void okResult_ifOkOrElse_executesOkAction() {
        final Result<String, String> result = Result.ok("success");
        final AtomicBoolean okCalled = new AtomicBoolean(false);
        final AtomicBoolean errCalled = new AtomicBoolean(false);
        result.ifOkOrElse(
            value -> okCalled.set(true),
            error -> errCalled.set(true)
        );
        assertTrue(okCalled.get());
        assertFalse(errCalled.get());
    }
    @Test
    public void okResult_ifOkOrElse_passesCorrectValue() {
        final String expected = "test value";
        final Result<String, String> result = Result.ok(expected);
        final AtomicReference<String> captured = new AtomicReference<>();
        result.ifOkOrElse(
            value -> captured.set(value),
            error -> {}
        );
        assertEquals(expected, captured.get());
    }
    @Test
    public void errResult_isOk_returnsFalse() {
        final Result<String, String> result = Result.err("error");
        assertFalse(result.isOk());
    }
    @Test
    public void errResult_isErr_returnsTrue() {
        final Result<String, String> result = Result.err("error");
        assertTrue(result.isErr());
    }
    @Test
    public void errResult_unwrap_throwsIllegalStateException() {
        final Result<String, String> result = Result.err("error");
        final IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            result::unwrap
        );
        assertEquals("Cannot unwrap Err result", exception.getMessage());
    }
    @Test
    public void errResult_ifOkOrElse_executesErrorAction() {
        final Result<String, String> result = Result.err("error");
        final AtomicBoolean okCalled = new AtomicBoolean(false);
        final AtomicBoolean errCalled = new AtomicBoolean(false);
        result.ifOkOrElse(
            value -> okCalled.set(true),
            error -> errCalled.set(true)
        );
        assertFalse(okCalled.get());
        assertTrue(errCalled.get());
    }
    @Test
    public void errResult_ifOkOrElse_passesCorrectError() {
        final String expected = "test error";
        final Result<String, String> result = Result.err(expected);
        final AtomicReference<String> captured = new AtomicReference<>();
        result.ifOkOrElse(
            value -> {},
            error -> captured.set(error)
        );
        assertEquals(expected, captured.get());
    }
    @Test
    public void okResult_withDifferentTypes_worksCorrectly() {
        final Result<Integer, String> result = Result.ok(42);
        assertTrue(result.isOk());
        assertEquals(Integer.valueOf(42), result.unwrap());
    }
    @Test
    public void errResult_withDifferentTypes_worksCorrectly() {
        final Result<Integer, String> result = Result.err("error message");
        assertTrue(result.isErr());
        final AtomicReference<String> captured = new AtomicReference<>();
        result.ifOkOrElse(
            value -> {},
            error -> captured.set(error)
        );
        assertEquals("error message", captured.get());
    }
}
