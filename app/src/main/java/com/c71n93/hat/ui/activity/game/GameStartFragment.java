package com.c71n93.hat.ui.activity.game;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.c71n93.hat.R;
import com.c71n93.hat.model.view.GameStateViewModel;

public class GameStartFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(
        @NonNull final LayoutInflater inflater,
        @Nullable final ViewGroup container,
        @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView wordsReady = view.findViewById(R.id.label_words_ready);
        GameStateViewModel.self(requireActivity()).state().observe(
            getViewLifecycleOwner(),
            state -> wordsReady.setText(getString(R.string.game_start_words_ready, state.words().total()))
        );
    }
}
