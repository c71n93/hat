package com.c71n93.hat.ui.elements;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import com.c71n93.hat.R;

public class VisualizedCountdownSeconds {
    private final TextView view;
    private final int durationSec;
    private final Runnable onFinish;
    private static final int MILLISEC_IN_SEC = 1000;

    /**
     * Primary constructor.
     * 
     * @param view
     *            View to visualize countdown
     * @param duration
     *            Timer duration in seconds
     * @param onFinishAction
     *            Runnable to be executed when the timer finishes
     */
    public VisualizedCountdownSeconds(final TextView view, final int duration, final Runnable onFinishAction) {
        this.view = view;
        this.durationSec = duration;
        this.onFinish = onFinishAction;
    }

    public void start() {
        final Context context = this.view.getContext();
        this.view.setText(context.getString(R.string.label_time_left, this.durationSec));
        this.view.setVisibility(View.VISIBLE);
        new CountDownTimer(
            VisualizedCountdownSeconds.secToMillisec(this.durationSec), VisualizedCountdownSeconds.MILLISEC_IN_SEC
        ) {
            @Override
            public void onTick(final long millisUntilFinished) {
                VisualizedCountdownSeconds.this.view.setText(
                    context.getString(R.string.label_time_left, millisUntilFinished / 1000L)
                );
            }
            @Override
            public void onFinish() {
                VisualizedCountdownSeconds.this.view.setText(context.getString(R.string.label_time_is_up));
                VisualizedCountdownSeconds.this.onFinish.run();
            }
        }.start();
    }

    private static int secToMillisec(final int sec) {
        return sec * MILLISEC_IN_SEC;
    }
}
