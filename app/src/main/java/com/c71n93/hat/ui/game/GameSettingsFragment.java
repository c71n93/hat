package com.c71n93.hat.ui.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.c71n93.hat.R;
import com.c71n93.hat.model.GameViewModel;
import com.c71n93.hat.model.PlayersNumber;

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
        final EditText playersInput = view.findViewById(R.id.input_number_of_players);
        final Button next = view.findViewById(R.id.button_next);
        next.setOnClickListener(
                button -> PlayersNumber.tryFromText(playersInput) // TODO: Figure out how to validate playersInput without static method. Maybe try to use decorator.
                        .ifPresentOrElse(
                                value -> {
                                    GameViewModel.self(requireActivity()).updatePlayers(value);
                                    Navigation.findNavController(button)
                                            .navigate(R.id.action_gameSettingsFragment_to_gameSummaryFragment);
                                },
                                () -> playersInput.setError(getString(R.string.error_number_required))
                        )
        );
    }
}
