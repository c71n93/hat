package hat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import hat.app.R;
import hat.ui.activity.game.GameActivity;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_new_game).setOnClickListener(
            view -> startActivity(new Intent(this, GameActivity.class))
        );
        final LocaleSwitch localeSwitch = new LocaleSwitch(Locale.ENGLISH, new Locale("ru"));
        findViewById(R.id.button_switch_language).setOnClickListener(
            view -> localeSwitch.toggle()
        );
    }
}
