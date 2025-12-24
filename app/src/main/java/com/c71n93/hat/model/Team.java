package com.c71n93.hat.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.c71n93.hat.R;

public class Team implements Drawable {
    final String name;
    final int score = 0;

    public Team(final String name) {
        this.name = name;
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
        teamView.setText(container.getContext().getString(R.string.label_team_queue_item, this.name, this.score));
        container.addView(teamView);
    }
}
