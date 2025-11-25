package com.c71n93.hat.ui.game;

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
import com.c71n93.hat.model.Game;
import com.c71n93.hat.model.GameViewModel;
import com.c71n93.hat.ui.inputs.TxtIntInput;
import com.c71n93.hat.ui.inputs.ValidatedInputs;

public class GameSettingsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(
            @NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // TODO: maybe better to ask just number of teams?
        final TxtIntInput playersInput = new TxtIntInput(view.findViewById(R.id.input_number_of_players)); // TODO: add validation (>=4)
        final TxtIntInput wordsPerPlayerInput = new TxtIntInput(
                view.findViewById(R.id.input_number_of_words_per_player)
        ); // TODO: add validation (>1?)
        view.findViewById(R.id.button_next).setOnClickListener(
                button -> new ValidatedInputs<>(playersInput, wordsPerPlayerInput)
                        .allValidOrError(
                                inputs -> {
                                    GameViewModel.self(requireActivity())
                                            .updateGame(new Game(inputs.get(0), inputs.get(1)));
                                    Navigation.findNavController(button)
                                            .navigate(R.id.action_gameSettingsFragment_to_gameSummaryFragment);
                                },
                                getString(R.string.error_number_required)
                        )
        );
    }
}
