package com.c71n93.hat.model;

import android.view.ViewGroup;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

/**
 * The queue of teams.
 */
public class TeamsQueue implements Drawable {
    private final Deque<Team> teams;

    public TeamsQueue() {
        this(new ArrayDeque<>());
    }

    public TeamsQueue(final Collection<Team> teams) {
        this.teams = new ArrayDeque<>(teams);
    }

    /**
     * Gets the next team in the queue. Then, puts it to the end of the queue.
     * 
     * @return Next team.
     */
    public Team next() {
        final Team team = this.teams.removeFirst();
        this.teams.addLast(team);
        return team;
    }

    // TODO: Wrap teams into ScrollView to be able to fit more teams in one screen.
    @Override
    public void draw(final ViewGroup container) {
        // TODO: implement decorator for ViewGroup called EmptyContainer, that will
        // guarantee emptiness.
        container.removeAllViews();
        for (final Team team : this.teams) {
            team.draw(container);
        }
    }
}
