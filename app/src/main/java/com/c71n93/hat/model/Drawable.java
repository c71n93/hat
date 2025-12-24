package com.c71n93.hat.model;

import android.view.ViewGroup;

public interface Drawable {
    /**
     * Draws itself into the provided container.
     *
     * @param container
     *            Container.
     */
    void draw(final ViewGroup container);
}
