package com.c71n93.hat.ui.game;

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
import com.c71n93.hat.model.GameViewModel;
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
        GameViewModel.self(requireActivity()).game().observe(
            getViewLifecycleOwner(),
            game -> {
                final Words words = new Words(game.wordsTotal());
                wordsLeft.setText(getString(R.string.label_words_left, words.untilFull()));
                addWordButton.setOnClickListener(
                    button -> wordInput.ifValidOrMarkError(
                        word -> {
                            words.with(new Word(word)).ifFullOrElse(
                                () -> Navigation.findNavController(button).navigate(
                                    R.id.action_hatFillingFragment_to_gameStartFragment
                                ),
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
