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
            button -> {
                this.state = renderNewState(TurnUiState.running(), views);
                score.draw(views.turnScore);
                new VisualizedCountdownSeconds(
                    views.countdownView,
                    5,
                    () -> this.state.ifRunningOrThrow(
                        running -> this.state = renderNewState(TurnUiState.finished(), views)
                    )
                ).start();
            }
        );
        views.acceptButton.setOnClickListener(
            button -> this.state.ifRunningOrThrow(
                running -> score.incrementAndDraw(views.turnScore)
            )
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

    private static TurnUiState renderNewState(final TurnUiState next, final StatelessTurnViews views) {
        next.render(views);
        return next;
    }
}
