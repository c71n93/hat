package com.c71n93.hat.ui.inputs;

import java.util.Optional;

public interface Input<T> {
    Optional<T> value();
    void error(String message);
}
