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
import com.c71n93.hat.model.Hat;
import com.c71n93.hat.model.RememberingHat;
import com.c71n93.hat.model.TeamsQueue;
import com.c71n93.hat.model.viewmodel.GameStateViewModel;
import com.c71n93.hat.ui.elements.TurnScore;
import com.c71n93.hat.ui.elements.VisualizedCountdownSeconds;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.Objects;

public class GameTurnFragment extends Fragment {
    private TurnUiState state = TurnUiState.ready();
    private RememberingHat hat = new RememberingHat(new Hat());
    private final TurnScore score = new TurnScore();

    @Nullable
    @Override
    public View onCreateView(
        @NonNull final LayoutInflater inflater,
        @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_turn, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TurnViews views = new TurnViews(view);
        final NavController navController = Navigation.findNavController(view);
        this.state = renderNewState(TurnUiState.ready(), views);
        views.startBtn.setOnClickListener(
            button -> this.onStartClicked(views)
        );
        views.endBtn.setOnClickListener(
            button -> {
                this.state.ifFinishedOrThrow(
                    finished -> this.currentTeamsQueue().next().addPoints(this.score.score())
                );
                navController.navigate(R.id.action_gameTurnFragment_to_gameStartFragment);
            }
        );
        requireActivity().getOnBackPressedDispatcher().addCallback(
            getViewLifecycleOwner(),
            new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    GameTurnFragment.this.showLeaveDialog(navController);
                }
            }
        );
    }

    private void onStartClicked(final TurnViews views) {
        this.state = renderNewState(TurnUiState.running(), views);
        this.score.draw(views.scoreTxt);
        this.hat = new RememberingHat(this.currentHat());
        if (!this.renderNextWord(this.hat, views)) {
            this.state = renderNewState(TurnUiState.finished(), views);
            return;
        }
        final VisualizedCountdownSeconds countdown = new VisualizedCountdownSeconds(
            views.countdownTxt,
            5, // TODO: make turn time configurable
            () -> this.state.ifRunningOrThrow(
                // TODO: add timer for TurnUiState.LastWord state
                running -> this.state = renderNewState(TurnUiState.lastWord(), views)
            )
        );
        countdown.start();
        views.acceptBtn.setOnClickListener(
            accept -> this.state.ifRunningOrThrow(
                running -> {
                    this.score.incrementAndDraw(views.scoreTxt);
                    if (!this.renderNextWord(this.hat, views)) {
                        countdown.stop();
                        this.state = renderNewState(TurnUiState.finished(), views);
                    }
                }
            )
        );
        views.acceptLastBtn.setOnClickListener(
            accept -> this.state.ifLastWordOrThrow(
                lastWord -> {
                    this.score.incrementAndDraw(views.scoreTxt);
                    this.state = renderNewState(TurnUiState.finished(), views);
                }
            )
        );
        views.returnLastBtn.setOnClickListener(
            returnLast -> this.state.ifLastWordOrThrow(
                lastWord -> {
                    this.hat.undoPull();
                    this.state = renderNewState(TurnUiState.finished(), views);
                }
            )
        );
    }

    private void showLeaveDialog(final NavController navController) {
        new MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.dialog_live_title)
            .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
            .setPositiveButton(
                R.string.button_leave,
                (dialog, which) -> this.onBackPressed(navController)
            )
            .show();
    }

    private void onBackPressed(final NavController navController) {
        // TODO: Looks like fall-through switch is perfect for this scenario. Find out
        // how to use it with sealed interfaces.
        switch (this.state) {
            case TurnUiState.Ready ready -> this.leaveToStart(navController);
            case TurnUiState.Running running -> this.leaveToStartWithUndoAndScores(navController);
            case TurnUiState.LastWord lastWord -> this.leaveToStartWithUndoAndScores(navController);
            case TurnUiState.Finished finished -> this.leaveToStartWithScore(navController);
            default -> throw new IllegalStateException("Invalid state.");
        }
    }

    private void leaveToStart(final NavController navController) {
        navController.navigate(R.id.action_gameTurnFragment_to_gameStartFragment);
    }

    private void leaveToStartWithScore(final NavController navController) {
        this.currentTeamsQueue().next().addPoints(this.score.score());
        this.leaveToStart(navController);
    }

    private void leaveToStartWithUndoAndScores(final NavController navController) {
        this.hat.undoPull();
        this.leaveToStartWithScore(navController);
    }

    private TeamsQueue currentTeamsQueue() {
        return Objects.requireNonNull(
            GameStateViewModel.self(requireActivity()).state().getValue(),
            "Game state is not ready."
        ).teamsQueue();
    }

    private Hat currentHat() {
        return Objects.requireNonNull(
            GameStateViewModel.self(requireActivity()).state().getValue(),
            "Game state is not ready."
        ).hat();
    }

    private boolean renderNextWord(final RememberingHat hat, final TurnViews views) {
        return hat.pull().map(
            word -> {
                word.draw(views.wordTxt);
                return true;
            }
        ).orElse(false);
    }

    private static TurnUiState renderNewState(final TurnUiState next, final TurnViews views) {
        next.render(views);
        return next;
    }
}
