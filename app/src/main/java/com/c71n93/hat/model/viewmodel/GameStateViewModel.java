package com.c71n93.hat.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.c71n93.hat.model.GameState;
import com.c71n93.hat.model.Hat;
import com.c71n93.hat.model.TeamsQueue;
import com.c71n93.hat.model.Words;

public class GameStateViewModel extends ViewModel {
    private final MutableLiveData<GameState> state = new MutableLiveData<>(
        new GameState(new Words(), new Hat(), new TeamsQueue())
    );

    public LiveData<GameState> state() {
        return this.state;
    }

    public void updateState(final GameState value) {
        this.state.setValue(value);
    }

    public static GameStateViewModel self(final ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner).get(GameStateViewModel.class);
    }
}
