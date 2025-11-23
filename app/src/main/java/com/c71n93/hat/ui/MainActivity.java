package com.c71n93.hat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.c71n93.hat.R;
import com.c71n93.hat.ui.game.GameActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button newGameButton = findViewById(R.id.button_new_game);
        newGameButton.setOnClickListener(
                view -> startActivity(new Intent(this, GameActivity.class))
        );
    }
}
