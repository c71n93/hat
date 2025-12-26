package com.c71n93.hat.ui.activity.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.c71n93.hat.R;
import com.c71n93.hat.model.viewmodel.GameStateViewModel;

public class GameStartFragment extends Fragment {
    private StartUiState state = StartUiState.defaultState();

    @Nullable
    @Override
    public View onCreateView(
        @NonNull final LayoutInflater inflater,
        @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final StartViews views = new StartViews(view);
        this.state = renderNewState(StartUiState.defaultState(), views);
        GameStateViewModel.self(requireActivity()).state().observe(
            getViewLifecycleOwner(),
            gameState -> {
                gameState.teamsQueue().draw(view.findViewById(R.id.container_teams_queue));
                views.wordsLeftTxt.setText(getString(R.string.label_words_in_hat, gameState.hat().wordsLeft()));
                if (gameState.hat().wordsLeft() == 0) {
                    this.state = renderNewState(StartUiState.endOfRound(), views);
                } else {
                    this.state = renderNewState(StartUiState.defaultState(), views);
                }
            }
        );
        views.startTurnBtn.setOnClickListener(
            button -> Navigation.findNavController(button)
                .navigate(R.id.action_gameStartFragment_to_gameTurnFragment)
        );
        views.newRoundBtn.setOnClickListener(
            button -> this.state.ifEndOfRoundOrThrow(
                endOfRound -> GameStateViewModel.self(requireActivity()).startNewRound()
            )
        );
        views.endGameBtn.setOnClickListener(
            button -> requireActivity().finish()
        );
    }

    private static StartUiState renderNewState(final StartUiState next, final StartViews views) {
        next.render(views);
        return next;
    }
}
