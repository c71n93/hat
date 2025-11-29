package com.c71n93.hat.ui.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.c71n93.hat.R;
import com.c71n93.hat.model.GameViewModel;

public class GameSummaryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_summary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView playersSummary = view.findViewById(R.id.text_number_of_players_summary);
        final TextView wordsSummary = view.findViewById(R.id.text_words_per_player_summary);
        GameViewModel.self(requireActivity())
                .game()
                .observe(
                        getViewLifecycleOwner(),
                        game -> {
                            final String team1 = game.teams().isEmpty() ? "" : game.teams().get(0).name();
                            final String team2 = game.teams().size() > 1 ? game.teams().get(1).name() : "";
                            playersSummary.setText(getString(R.string.summary_teams, team1, team2));
                            wordsSummary.setText(getString(R.string.summary_words_total, game.wordsTotal()));
                        }
                );
    }
}
