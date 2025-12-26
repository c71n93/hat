package com.c71n93.hat.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.c71n93.hat.R;
import com.c71n93.hat.ui.elements.DrawableToContainerWithViewTemplate;

/**
 * Wrapper for {@link Team} that draws a team using a highlighted queue item
 * template.
 */
public class HighlightedTeam implements DrawableToContainerWithViewTemplate<TextView> {
    final Team origin;

    public HighlightedTeam(final Team origin) {
        this.origin = origin;
    }

    @Override
    public void draw(final ViewGroup container) {
        // TODO: this is almost a copy paste from Team.draw
        final LayoutInflater inflater = LayoutInflater.from(container.getContext());
        final View item = inflater.inflate(
            R.layout.view_team_queue_item_highlighted,
            container,
            false
        );
        final TextView teamView = item.findViewById(R.id.label_team_queue_item);
        this.draw(container, teamView);
    }

    @Override
    public void draw(final ViewGroup container, final TextView viewTemplate) {
        this.origin.draw(container, viewTemplate);
    }
}
