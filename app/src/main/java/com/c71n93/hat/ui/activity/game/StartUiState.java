package com.c71n93.hat.ui.activity.game;

import android.view.View;
import java.util.function.Consumer;

sealed interface StartUiState permits StartUiState.Default, StartUiState.EndOfRound {
    void render(StartViews views);
    default void ifDefaultOrThrow(final Consumer<? super Default> action) {
        throw new IllegalStateException("Invalid state. Should be 'Default'");
    }
    default void ifEndOfRoundOrThrow(final Consumer<? super EndOfRound> action) {
        throw new IllegalStateException("Invalid state. Should be 'EndOfRound'");
    }

    static StartUiState defaultState() {
        return new Default();
    }

    static StartUiState endOfRound() {
        return new EndOfRound();
    }

    record Default() implements StartUiState {
        @Override
        public void ifDefaultOrThrow(final Consumer<? super Default> action) {
            action.accept(this);
        }
        @Override
        public void render(final StartViews views) {
            views.wordsLeftTxt.setVisibility(View.VISIBLE);
            views.roundOverTxt.setVisibility(View.INVISIBLE);
            views.startTurnBtn.setVisibility(View.VISIBLE);
            views.startTurnBtn.setEnabled(true);
            views.newRoundBtn.setVisibility(View.INVISIBLE);
            views.newRoundBtn.setEnabled(false);
            views.endGameBtn.setVisibility(View.INVISIBLE);
            views.endGameBtn.setEnabled(false);
        }
    }

    record EndOfRound() implements StartUiState {
        @Override
        public void ifEndOfRoundOrThrow(final Consumer<? super EndOfRound> action) {
            action.accept(this);
        }
        @Override
        public void render(final StartViews views) {
            views.wordsLeftTxt.setVisibility(View.INVISIBLE);
            views.roundOverTxt.setVisibility(View.VISIBLE);
            views.startTurnBtn.setVisibility(View.INVISIBLE);
            views.startTurnBtn.setEnabled(false);
            views.newRoundBtn.setVisibility(View.VISIBLE);
            views.newRoundBtn.setEnabled(true);
            views.endGameBtn.setVisibility(View.VISIBLE);
            views.endGameBtn.setEnabled(true);
        }
    }
}
