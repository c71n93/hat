package com.c71n93.hat.ui.input;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link Result} sealed interface and its Ok/Err
 * implementations.
 */
public class ResultTest {
    private static final String SUCCESS = "success";
    private static final String ERROR = "error";
    @Test
    public void okResult_isOk_returnsTrue() {
        final Result<String, String> result = Result.ok(SUCCESS);
        Assert.assertTrue("Ok result should report ok", result.isOk());
    }
    @Test
    public void okResult_isErr_returnsFalse() {
        final Result<String, String> result = Result.ok(SUCCESS);
        Assert.assertFalse("Ok result should not report error", result.isErr());
    }
    @Test
    public void okResult_unwrap_returnsValue() {
        final Result<String, String> result = Result.ok(SUCCESS);
        Assert.assertEquals("Ok unwrap should return value", SUCCESS, result.unwrap());
    }
    @Test
    public void okResult_ifOkOrElse_executesOkAction() {
        final Result<String, String> result = Result.ok(SUCCESS);
        final AtomicBoolean okCalled = new AtomicBoolean(false);
        final AtomicBoolean errCalled = new AtomicBoolean(false);
        result.ifOkOrElse(
            value -> okCalled.set(true),
            error -> errCalled.set(true)
        );
        Assert.assertTrue("Ok action should run", okCalled.get());
        Assert.assertFalse("Error action should not run", errCalled.get());
    }
    @Test
    public void okResult_ifOkOrElse_passesCorrectValue() {
        final String expected = "test value";
        final Result<String, String> result = Result.ok(expected);
        final AtomicReference<String> captured = new AtomicReference<>();
        result.ifOkOrElse(
            value -> captured.set(value),
            error -> {
            }
        );
        Assert.assertEquals("Ok action should receive value", expected, captured.get());
    }
    @Test
    public void errResult_isOk_returnsFalse() {
        final Result<String, String> result = Result.err(ERROR);
        Assert.assertFalse("Err result should not report ok", result.isOk());
    }
    @Test
    public void errResult_isErr_returnsTrue() {
        final Result<String, String> result = Result.err(ERROR);
        Assert.assertTrue("Err result should report error", result.isErr());
    }
    @Test
    public void errResult_unwrap_throwsIllegalStateException() {
        final Result<String, String> result = Result.err(ERROR);
        final IllegalStateException exception = Assert.assertThrows(
            "Unwrapping Err should throw",
            IllegalStateException.class,
            result::unwrap
        );
        Assert.assertEquals("Exception message should match", "Cannot unwrap Err result", exception.getMessage());
    }
    @Test
    public void errResult_ifOkOrElse_executesErrorAction() {
        final Result<String, String> result = Result.err(ERROR);
        final AtomicBoolean okCalled = new AtomicBoolean(false);
        final AtomicBoolean errCalled = new AtomicBoolean(false);
        result.ifOkOrElse(
            value -> okCalled.set(true),
            error -> errCalled.set(true)
        );
        Assert.assertFalse("Ok action should not run", okCalled.get());
        Assert.assertTrue("Error action should run", errCalled.get());
    }
    @Test
    public void errResult_ifOkOrElse_passesCorrectError() {
        final String expected = "test error";
        final Result<String, String> result = Result.err(expected);
        final AtomicReference<String> captured = new AtomicReference<>();
        result.ifOkOrElse(
            value -> {
            },
            error -> captured.set(error)
        );
        Assert.assertEquals("Error action should receive error", expected, captured.get());
    }
    @Test
    public void okResult_withDifferentTypes_worksCorrectly() {
        final Result<Integer, String> result = Result.ok(42);
        Assert.assertTrue("Ok result should be ok", result.isOk());
        Assert.assertEquals("Should unwrap integer value", Integer.valueOf(42), result.unwrap());
    }
    @Test
    public void errResult_withDifferentTypes_worksCorrectly() {
        final Result<Integer, String> result = Result.err("error message");
        Assert.assertTrue("Err result should report error", result.isErr());
        final AtomicReference<String> captured = new AtomicReference<>();
        result.ifOkOrElse(
            value -> {
            },
            error -> captured.set(error)
        );
        Assert.assertEquals("Should pass through error message", "error message", captured.get());
    }
}
