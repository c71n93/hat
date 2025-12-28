package com.c71n93.hat.ui.activity.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.NavController;
import com.c71n93.hat.R;
import com.c71n93.hat.model.viewmodel.GameStateViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
        final NavController navController = Navigation.findNavController(view);
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
            button -> navController.navigate(R.id.action_gameStartFragment_to_gameTurnFragment)
        );
        views.newRoundBtn.setOnClickListener(
            button -> this.state.ifEndOfRoundOrThrow(
                endOfRound -> GameStateViewModel.self(requireActivity()).startNewRound()
            )
        );
        views.endGameBtn.setOnClickListener(
            button -> requireActivity().finish()
        );
        requireActivity().getOnBackPressedDispatcher().addCallback(
            getViewLifecycleOwner(),
            new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    GameStartFragment.this.showLeaveDialog(navController);
                }
            }
        );
    }

    private static StartUiState renderNewState(final StartUiState next, final StartViews views) {
        next.render(views);
        return next;
    }

    // TODO: unify showLeaveDialog code to make it reusable (see
    // GameTurnFragment.showLeaveDialog)
    private void showLeaveDialog(final NavController navController) {
        new MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_live_title)
            .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
            .setPositiveButton(
                R.string.button_leave,
                (dialog, which) -> navController.popBackStack(R.id.gameSettingsFragment, false)
            )
            .show();
    }
}
