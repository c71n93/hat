package hat.ui.activity.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import hat.app.R;
import hat.model.GameSettings;
import hat.model.viewmodel.GameSettingsViewModel;
import hat.model.Team;
import hat.ui.input.EditIntInput;
import hat.ui.input.EditStringInput;
import hat.ui.input.MultipleTeamInputViews;
import hat.ui.input.validation.DefaultInputsValidation;
import hat.ui.input.validation.DifferentInputsValidation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        final MultipleTeamInputViews teamInputViews = new MultipleTeamInputViews(
            view.findViewById(R.id.container_team_inputs)
        ).addInput().addInput();
        final EditIntInput totalWordsInput = new EditIntInput(view.findViewById(R.id.input_total_words));
        final EditIntInput turnDurationInput = new EditIntInput(view.findViewById(R.id.input_turn_duration));
        view.findViewById(R.id.button_add_team).setOnClickListener(
            button -> teamInputViews.addInput()
        );
        view.findViewById(R.id.button_remove_team).setOnClickListener(
            button -> teamInputViews.removeLastInput()
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
                final Optional<List<Integer>> numbers = new DefaultInputsValidation<>(
                    totalWordsInput,
                    turnDurationInput
                ).validated();
                // TODO: it definitely may be implemented more elegant using something like
                // "InputCombination"
                if (teams.isPresent() && numbers.isPresent()) {
                    final List<Integer> values = numbers.get();
                    GameSettingsViewModel.self(requireActivity())
                        .updateSettings(new GameSettings(teams.get(), values.get(0), values.get(1)));
                    Navigation.findNavController(button)
                        .navigate(R.id.action_gameSettingsFragment_to_hatFillingFragment);
                }
            }
        );
    }
}
