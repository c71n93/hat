package com.c71n93.hat.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class GameViewModel extends ViewModel {
    private final MutableLiveData<Game> game = new MutableLiveData<>(new Game(0));

    public LiveData<Game> game() {
        return game;
    }
    public void updatePlayers(final PlayersNumber players) {
        game.setValue(new Game(players));
    }
    public static GameViewModel self(final ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner).get(GameViewModel.class);
    }
}
