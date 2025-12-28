package com.c71n93.hat.model.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.c71n93.hat.model.GameSettings;

import java.util.ArrayList;

public class GameSettingsViewModel extends ViewModel {
    private final MutableLiveData<GameSettings> settings = new MutableLiveData<>(
        new GameSettings(new ArrayList<>(), 0, 0)
    );

    public LiveData<GameSettings> settings() {
        return this.settings;
    }

    public void updateSettings(final GameSettings gameSettings) {
        this.settings.setValue(gameSettings);
    }

    public static GameSettingsViewModel self(final ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner).get(GameSettingsViewModel.class);
    }
}
