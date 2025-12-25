package com.c71n93.hat.ui.activity.game;

import android.view.View;

import java.util.function.Consumer;

public sealed interface TurnUiState permits TurnUiState.Ready, TurnUiState.Running, TurnUiState.Finished {
    void render(StatelessTurnViews views);

    // TODO: Fix copy-paste for these methods.
    // TODO: Since TurnUiState is stateless (wtf) this methods can accept Runnable
    // instead of Consumer. But maybe someday TurnUiState will be stateful.
    default void ifRunningOrThrow(final Consumer<? super Running> action) {
        throw new IllegalStateException("Invalid state. Should be 'Running'");
    }
    default void ifFinishedOrThrow(final Consumer<? super Finished> action) {
        throw new IllegalStateException("Invalid state. Should be 'Finished'");
    }

    static TurnUiState ready() {
        return new Ready();
    }
    static TurnUiState running() {
        return new Running();
    }
    static TurnUiState finished() {
        return new Finished();
    }

    record Ready() implements TurnUiState {
        @Override
        public void render(final StatelessTurnViews views) {
            views.startButton.setVisibility(View.VISIBLE);
            views.startButton.setEnabled(true);
            views.acceptButton.setVisibility(View.GONE);
            views.acceptButton.setEnabled(false);
            views.endButton.setVisibility(View.GONE);
            views.endButton.setEnabled(false);
            views.countdownView.setVisibility(View.GONE);
            views.turnScore.setVisibility(View.GONE);
        }
    }

    record Running() implements TurnUiState {
        @Override
        public void ifRunningOrThrow(final Consumer<? super Running> action) {
            action.accept(this);
        }
        @Override
        public void render(final StatelessTurnViews views) {
            views.startButton.setVisibility(View.GONE);
            views.startButton.setEnabled(false);
            views.acceptButton.setVisibility(View.VISIBLE);
            views.acceptButton.setEnabled(true);
            views.endButton.setVisibility(View.GONE);
            views.endButton.setEnabled(false);
            views.countdownView.setVisibility(View.VISIBLE);
            views.turnScore.setVisibility(View.VISIBLE);
        }
    }

    record Finished() implements TurnUiState {
        @Override
        public void ifFinishedOrThrow(final Consumer<? super Finished> action) {
            action.accept(this);
        }
        @Override
        public void render(final StatelessTurnViews views) {
            views.startButton.setVisibility(View.GONE);
            views.startButton.setEnabled(false);
            views.acceptButton.setVisibility(View.GONE);
            views.acceptButton.setEnabled(false);
            views.endButton.setVisibility(View.VISIBLE);
            views.endButton.setEnabled(true);
            views.countdownView.setVisibility(View.VISIBLE);
            views.turnScore.setVisibility(View.VISIBLE);
        }
    }
}
