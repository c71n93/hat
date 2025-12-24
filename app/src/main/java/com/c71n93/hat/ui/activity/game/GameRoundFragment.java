package com.c71n93.hat.ui.activity.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.c71n93.hat.R;
import com.c71n93.hat.ui.elements.VisualizedCountdownSeconds;

public class GameRoundFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(
        @NonNull final LayoutInflater inflater,
        @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_round, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button startButton = view.findViewById(R.id.button_start);
        final Button endButton = view.findViewById(R.id.button_end_round);
        startButton.setOnClickListener(
            button -> {
                startButton.setEnabled(false);
                startButton.setVisibility(View.GONE);
                // TODO: add possibility to configure round duration.
                new VisualizedCountdownSeconds(
                    view.findViewById(R.id.text_round_countdown), 5, () -> endButton.setVisibility(View.VISIBLE)
                ).start();
            }
        );
        endButton.setOnClickListener(
            button -> Navigation.findNavController(button)
                .navigate(R.id.action_gameRoundFragment_to_gameStartFragment)
        );
    }
}
