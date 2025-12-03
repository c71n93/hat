package com.c71n93.hat.ui.activity.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.c71n93.hat.R;
import com.c71n93.hat.model.GameState;
import com.c71n93.hat.model.Hat;
import com.c71n93.hat.model.TeamsQueue;
import com.c71n93.hat.model.view.GameSettingsViewModel;
import com.c71n93.hat.model.view.GameStateViewModel;
import com.c71n93.hat.model.Words;
import com.c71n93.hat.model.Word;
import com.c71n93.hat.ui.input.EditStringInput;

public class HatFillingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(
        @NonNull final LayoutInflater inflater,
        @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hat_filling, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditStringInput wordInput = new EditStringInput(view.findViewById(R.id.input_word));
        final View addWordButton = view.findViewById(R.id.button_add_word);
        final TextView wordsLeft = view.findViewById(R.id.text_words_left);
        GameSettingsViewModel.self(requireActivity()).settings().observe(
            getViewLifecycleOwner(),
            settings -> {
                final Words words = new Words(settings.wordsTotal());
                wordsLeft.setText(getString(R.string.label_words_left, words.untilFull()));
                addWordButton.setOnClickListener(
                    button -> wordInput.ifValidOrMarkError(
                        word -> {
                            words.with(new Word(word)).ifFullOrElse(
                                () -> {
                                    GameStateViewModel.self(requireActivity()).updateState(
                                        new GameState(words, new Hat(words), new TeamsQueue(settings.teams()))
                                    );
                                    Navigation.findNavController(button).navigate(
                                        R.id.action_hatFillingFragment_to_gameStartFragment
                                    );
                                },
                                () -> wordsLeft.setText(getString(R.string.label_words_left, words.untilFull()))
                            );
                            wordInput.clear();
                        }
                    )
                );
            }
        );
    }
}
