package com.c71n93.hat.ui.input;

import java.util.function.Consumer;

public sealed interface Result<T, E> permits Result.Ok, Result.Err {
    boolean isOk();
    default boolean isErr() {
        return !isOk();
    }
    T unwrap();
    void ifOkOrElse(Consumer<? super T> action, Consumer<? super E> errorAction);

    static <T, E> Result<T, E> ok(T value) {
        return new Ok<>(value);
    }
    static <T, E> Result<T, E> err(E error) {
        return new Err<>(error);
    }

    record Ok<T, E>(T value) implements Result<T, E> {
        @Override
        public boolean isOk() {
            return true;
        }
        @Override
        public T unwrap() {
            return value;
        }
        @Override
        public void ifOkOrElse(Consumer<? super T> action, Consumer<? super E> errorAction) {
            action.accept(value);
        }
    }

    record Err<T, E>(E error) implements Result<T, E> {
        @Override
        public boolean isOk() {
            return false;
        }
        @Override
        public T unwrap() {
            throw new IllegalStateException("Cannot unwrap Err result");
        }
        @Override
        public void ifOkOrElse(Consumer<? super T> action, Consumer<? super E> errorAction) {
            errorAction.accept(error);
        }
    }
}
