package hat.ui.activity.game;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import hat.app.R;

class StartViews {
    final TextView wordsLeftTxt;
    final TextView roundOverTxt;
    final Button startTurnBtn;
    final Button newRoundBtn;
    final Button endGameBtn;

    StartViews(final View root) {
        this.wordsLeftTxt = root.findViewById(R.id.label_words_left);
        this.roundOverTxt = root.findViewById(R.id.label_round_is_over);
        this.startTurnBtn = root.findViewById(R.id.button_start_turn);
        this.newRoundBtn = root.findViewById(R.id.button_new_round);
        this.endGameBtn = root.findViewById(R.id.button_end_game);
    }
}
