package hat.ui.elements;

import android.view.View;

public interface DrawableToView<V extends View> {
    /**
     * Draws itself into the given view.
     *
     * @param view
     *            The view into which the object is drawn.
     */
    void draw(V view);
}
