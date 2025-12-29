package hat.ui.activity.game;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import hat.app.R;

final class TurnViews {
    final Button startBtn;
    final Button acceptBtn;
    final Button acceptLastBtn;
    final Button returnLastBtn;
    final Button endBtn;
    final TextView countdownTxt;
    final TextView scoreTxt;
    final TextView wordTxt;

    TurnViews(final View root) {
        this.startBtn = root.findViewById(R.id.button_start);
        this.acceptBtn = root.findViewById(R.id.button_accept);
        this.acceptLastBtn = root.findViewById(R.id.button_accept_last);
        this.returnLastBtn = root.findViewById(R.id.button_return_last);
        this.endBtn = root.findViewById(R.id.button_end_turn);
        this.countdownTxt = root.findViewById(R.id.text_turn_countdown);
        this.scoreTxt = root.findViewById(R.id.text_turn_score);
        this.wordTxt = root.findViewById(R.id.text_next_word);
    }
}
