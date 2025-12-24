package com.c71n93.hat.model;

import android.view.View;
import android.view.ViewGroup;

public interface DrawableWithViewTemplate<V extends View> extends Drawable {
    /**
     * Draws itself into the specified container using the given view as a template.
     *
     * @param container
     *            The container into which the object is drawn.
     * @param viewTemplate
     *            The view used as a template for drawing.
     */
    void draw(final ViewGroup container, final V viewTemplate);
}
