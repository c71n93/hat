package com.c71n93.hat.ui.activity.game;

import android.view.View;

import java.util.function.Consumer;

// TODO: Refactor logic of states to generalize it across different Fragments somehow.
sealed interface TurnUiState
    permits TurnUiState.Ready, TurnUiState.Running, TurnUiState.LastWord, TurnUiState.Finished {
    void render(TurnViews views);

    // TODO: Fix copy-paste for these methods.
    // TODO: Since TurnUiState is stateless (wtf) this methods can accept Runnable
    // instead of Consumer. But maybe someday TurnUiState will be stateful.
    default void ifRunningOrThrow(final Consumer<? super Running> action) {
        throw new IllegalStateException("Invalid state. Should be 'Running'");
    }
    default void ifLastWordOrThrow(final Consumer<? super LastWord> action) {
        throw new IllegalStateException("Invalid state. Should be 'LastWord'");
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
    static TurnUiState lastWord() {
        return new LastWord();
    }
    static TurnUiState finished() {
        return new Finished();
    }

    record Ready() implements TurnUiState {
        @Override
        public void render(final TurnViews views) {
            views.startBtn.setVisibility(View.VISIBLE);
            views.startBtn.setEnabled(true);
            views.acceptBtn.setVisibility(View.GONE);
            views.acceptBtn.setEnabled(false);
            views.acceptLastBtn.setVisibility(View.GONE);
            views.acceptLastBtn.setEnabled(false);
            views.returnBtn.setVisibility(View.GONE);
            views.returnBtn.setEnabled(false);
            views.endBtn.setVisibility(View.GONE);
            views.endBtn.setEnabled(false);
            views.countdownTxt.setVisibility(View.GONE);
            views.scoreTxt.setVisibility(View.GONE);
            views.wordTxt.setVisibility(View.GONE);
        }
    }

    record Running() implements TurnUiState {
        @Override
        public void ifRunningOrThrow(final Consumer<? super Running> action) {
            action.accept(this);
        }
        @Override
        public void render(final TurnViews views) {
            views.startBtn.setVisibility(View.GONE);
            views.startBtn.setEnabled(false);
            views.acceptBtn.setVisibility(View.VISIBLE);
            views.acceptBtn.setEnabled(true);
            views.acceptLastBtn.setVisibility(View.GONE);
            views.acceptLastBtn.setEnabled(false);
            views.returnBtn.setVisibility(View.GONE);
            views.returnBtn.setEnabled(false);
            views.endBtn.setVisibility(View.GONE);
            views.endBtn.setEnabled(false);
            views.countdownTxt.setVisibility(View.VISIBLE);
            views.scoreTxt.setVisibility(View.VISIBLE);
            views.wordTxt.setVisibility(View.VISIBLE);
        }
    }

    record LastWord() implements TurnUiState {
        @Override
        public void ifLastWordOrThrow(final Consumer<? super LastWord> action) {
            action.accept(this);
        }
        @Override
        public void render(final TurnViews views) {
            views.startBtn.setVisibility(View.GONE);
            views.startBtn.setEnabled(false);
            views.acceptBtn.setVisibility(View.GONE);
            views.acceptBtn.setEnabled(false);
            views.acceptLastBtn.setVisibility(View.VISIBLE);
            views.acceptLastBtn.setEnabled(true);
            views.returnBtn.setVisibility(View.VISIBLE);
            views.returnBtn.setEnabled(true);
            views.endBtn.setVisibility(View.GONE);
            views.endBtn.setEnabled(false);
            views.countdownTxt.setVisibility(View.VISIBLE);
            views.scoreTxt.setVisibility(View.VISIBLE);
            views.wordTxt.setVisibility(View.VISIBLE);
        }
    }

    record Finished() implements TurnUiState {
        @Override
        public void ifFinishedOrThrow(final Consumer<? super Finished> action) {
            action.accept(this);
        }
        @Override
        public void render(final TurnViews views) {
            views.startBtn.setVisibility(View.GONE);
            views.startBtn.setEnabled(false);
            views.acceptBtn.setVisibility(View.GONE);
            views.acceptBtn.setEnabled(false);
            views.acceptLastBtn.setVisibility(View.GONE);
            views.acceptLastBtn.setEnabled(false);
            views.returnBtn.setVisibility(View.GONE);
            views.returnBtn.setEnabled(false);
            views.endBtn.setVisibility(View.VISIBLE);
            views.endBtn.setEnabled(true);
            views.countdownTxt.setVisibility(View.VISIBLE);
            views.scoreTxt.setVisibility(View.VISIBLE);
            views.wordTxt.setVisibility(View.GONE);
        }
    }
}
