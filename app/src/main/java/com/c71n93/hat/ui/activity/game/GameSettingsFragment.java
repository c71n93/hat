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
import com.c71n93.hat.model.GameSettings;
import com.c71n93.hat.model.view.GameSettingsViewModel;
import com.c71n93.hat.model.Team;
import com.c71n93.hat.ui.input.EditIntInput;
import com.c71n93.hat.ui.input.EditStringInput;
import com.c71n93.hat.ui.input.validation.DefaultInputsValidation;
import com.c71n93.hat.ui.input.validation.DifferentInputsValidation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameSettingsFragment extends Fragment {
    private LayoutInflater layoutInflater;

    @Nullable
    @Override
    public View onCreateView(
        @NonNull final LayoutInflater inflater,
        @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {
        this.layoutInflater = inflater;
        return inflater.inflate(R.layout.fragment_game_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final MultipleTeamInputViews teamInputViews = new MultipleTeamInputViews(
            this.layoutInflater, view.findViewById(R.id.container_team_inputs)
        ).addInput().addInput();
        final EditIntInput totalWordsInput = new EditIntInput(view.findViewById(R.id.input_total_words));
        view.findViewById(R.id.button_add_team).setOnClickListener(
            button -> teamInputViews.addInput()
        );
        view.findViewById(R.id.button_next).setOnClickListener(
            button -> {
                // TODO: maybe it will be useful to implement TeamInputsValidation
                final Optional<List<Team>> teams = new DifferentInputsValidation<>(
                    teamInputViews.inputs().toArray(new EditStringInput[0])
                ).validated().map(
                    names -> names.stream().map(Team::new).collect(Collectors.toList())
                );
                // TODO: maybe it will be useful to implement WordsNumInputsValidation
                final Optional<Integer> words = new DefaultInputsValidation<>(totalWordsInput).validated().map(
                    numbers -> numbers.get(0)
                );
                // TODO: it definitely may be implemented more elegant using something like
                // "InputCombination"
                if (teams.isPresent() && words.isPresent()) {
                    GameSettingsViewModel.self(requireActivity())
                        .updateSettings(new GameSettings(teams.get(), words.get()));
                    Navigation.findNavController(button)
                        .navigate(R.id.action_gameSettingsFragment_to_hatFillingFragment);
                }
            }
        );
    }
}
