package hat.ui.input;

import java.util.function.Consumer;

public sealed interface Result<T, E> permits Result.Ok, Result.Err {
    boolean isOk();
    default boolean isErr() {
        return !this.isOk();
    }
    T unwrap();
    void ifOkOrElse(final Consumer<? super T> action, final Consumer<? super E> errorAction);

    static <T, E> Result<T, E> ok(final T value) {
        return new Ok<>(value);
    }
    static <T, E> Result<T, E> err(final E error) {
        return new Err<>(error);
    }

    record Ok<T, E>(T value) implements Result<T, E> {
        @Override
        public boolean isOk() {
            return true;
        }
        @Override
        public T unwrap() {
            return this.value;
        }
        @Override
        public void ifOkOrElse(final Consumer<? super T> action, final Consumer<? super E> errorAction) {
            action.accept(this.value);
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
        public void ifOkOrElse(final Consumer<? super T> action, final Consumer<? super E> errorAction) {
            errorAction.accept(this.error);
        }
    }
}
