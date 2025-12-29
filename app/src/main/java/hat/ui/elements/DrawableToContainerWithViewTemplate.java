package hat.ui.elements;

import android.view.View;
import android.view.ViewGroup;

// TODO: This is very strange and unnatural hierarchy. Need to find out how different views can be related to each other and drawn. Fix this interface hierarchy.
public interface DrawableToContainerWithViewTemplate<V extends View> extends DrawableToContainer {
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
