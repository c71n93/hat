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
import com.c71n93.hat.model.Hat;
import com.c71n93.hat.model.Team;
import com.c71n93.hat.model.viewmodel.GameStateViewModel;
import com.c71n93.hat.ui.elements.TurnScore;
import com.c71n93.hat.ui.elements.VisualizedCountdownSeconds;
import java.util.Objects;

public class GameTurnFragment extends Fragment {
    private TurnUiState state = TurnUiState.ready();

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
        final StatelessTurnViews views = new StatelessTurnViews(view);
        this.state = renderNewState(TurnUiState.ready(), views);
        final TurnScore score = new TurnScore();
        views.startButton.setOnClickListener(
            button -> this.onStartClicked(views, score)
        );
        views.endButton.setOnClickListener(
            button -> {
                this.state.ifFinishedOrThrow(
                    finished -> this.currentTeam().addPoints(score.score())
                );
                Navigation.findNavController(button)
                    .navigate(R.id.action_gameTurnFragment_to_gameStartFragment);
            }
        );
    }

    private Team currentTeam() {
        return Objects.requireNonNull(
            GameStateViewModel.self(requireActivity()).state().getValue(),
            "Game state is not ready."
        ).teamsQueue().next();
    }

    private Hat currentHat() {
        return Objects.requireNonNull(
            GameStateViewModel.self(requireActivity()).state().getValue(),
            "Game state is not ready."
        ).hat();
    }

    // TODO: Fragile spaghetti-code with lots of temporal coupling. Should be
    // refactored.
    private void onStartClicked(final StatelessTurnViews views, final TurnScore score) {
        this.state = renderNewState(TurnUiState.running(), views);
        score.draw(views.turnScore);
        final Hat hat = this.currentHat();
        if (!this.renderNextWord(hat, views)) {
            this.state = renderNewState(TurnUiState.finished(), views);
            return;
        }
        final VisualizedCountdownSeconds countdown = new VisualizedCountdownSeconds(
            views.countdownView,
            5,
            () -> this.state.ifRunningOrThrow(
                // TODO: Handle situation with last pulled word when timer is up (accept / put
                // back)
                running -> this.state = renderNewState(TurnUiState.finished(), views)
            )
        );
        countdown.start();
        views.acceptButton.setOnClickListener(
            accept -> this.state.ifRunningOrThrow(
                running -> {
                    score.incrementAndDraw(views.turnScore);
                    if (!this.renderNextWord(hat, views)) {
                        countdown.stop();
                        this.state = renderNewState(TurnUiState.finished(), views);
                    }
                }
            )
        );
    }

    private boolean renderNextWord(final Hat hat, final StatelessTurnViews views) {
        return hat.pull().map(
            word -> {
                word.draw(views.word);
                return true;
            }
        ).orElse(false);
    }

    private static TurnUiState renderNewState(final TurnUiState next, final StatelessTurnViews views) {
        next.render(views);
        return next;
    }
}
