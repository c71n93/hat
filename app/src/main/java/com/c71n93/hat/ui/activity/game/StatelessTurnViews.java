package com.c71n93.hat.ui.activity.game;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.c71n93.hat.R;
import com.c71n93.hat.ui.elements.TurnScore;

final class StatelessTurnViews {
    final Button startButton;
    final Button acceptButton;
    final Button endButton;
    final TextView countdownView;
    final TextView turnScore;

    StatelessTurnViews(final View root) {
        this.startButton = root.findViewById(R.id.button_start);
        this.acceptButton = root.findViewById(R.id.button_accept);
        this.endButton = root.findViewById(R.id.button_end_turn);
        this.countdownView = root.findViewById(R.id.text_turn_countdown);
        this.turnScore = root.findViewById(R.id.text_turn_score);
    }
}
