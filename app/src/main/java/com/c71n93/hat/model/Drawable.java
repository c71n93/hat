package com.c71n93.hat.model;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;

public interface Drawable {
    /**
     * Draws itself into the specified container.
     *
     * @param container
     *            The container into which the object is drawn.
     */
    void draw(final ViewGroup container);
}
