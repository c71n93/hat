package com.c71n93.hat.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.c71n93.hat.R;
import com.c71n93.hat.ui.elements.DrawableToContainerWithViewTemplate;

public class Team implements DrawableToContainerWithViewTemplate<TextView> {
    final String name;
    private int score;

    public Team(final String name) {
        this.name = name;
        this.score = 0;
    }

    @Override
    public void draw(final ViewGroup container) {
        // TODO: optimize this call somehow. Now it is called in a loop inside
        // TeamsQueue.draw. But LayoutInflater can be passed as an argument here.
        final LayoutInflater inflater = LayoutInflater.from(container.getContext());
        // TODO: maybe this call also can be passed here from outside? I am not sure
        // that we should inflate XML for every team. Probably can be optimized.
        final View item = inflater.inflate(
            R.layout.view_team_queue_item,
            container,
            false
        );
        final TextView teamView = item.findViewById(R.id.label_team_queue_item);
        this.draw(container, teamView);
    }

    @Override
    public void draw(final ViewGroup container, final TextView viewTemplate) {
        viewTemplate.setText(container.getContext().getString(R.string.label_team_queue_item, this.name, this.score));
        container.addView(viewTemplate);
    }

    public void addPoints(final int points) {
        this.score = this.score + points;
    }
}
