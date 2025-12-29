package hat.ui.elements;

import android.view.ViewGroup;

public interface DrawableToContainer extends DrawableToView<ViewGroup> {
    /**
     * Draws itself into the specified container.
     *
     * @param container
     *            The container into which the object is drawn.
     */
    void draw(final ViewGroup container);
}
