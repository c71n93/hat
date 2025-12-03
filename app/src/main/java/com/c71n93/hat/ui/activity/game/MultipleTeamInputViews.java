package com.c71n93.hat.ui.activity.game;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.c71n93.hat.R;
import com.c71n93.hat.ui.input.EditStringInput;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages creation and storage of team name input views inside a container.
 *
 * TODO: This class is not configurable and therefore cannot be reused. Figure
 * out how dynamic management of multiple inputs can be generalized. TODO: Try
 * to use RecyclerView to handle multiple inputs. TODO: Add possibility to
 * remove team inputs.
 */
public class MultipleTeamInputViews {
    private final LayoutInflater layoutInflater;
    private final LinearLayout teamsContainer;
    private final List<EditStringInput> inputs = new ArrayList<>();

    public MultipleTeamInputViews(final LayoutInflater layoutInflater, final LinearLayout teamsContainer) {
        this.layoutInflater = layoutInflater;
        this.teamsContainer = teamsContainer;
    }

    public MultipleTeamInputViews addInput() {
        final View view = this.layoutInflater.inflate(
            R.layout.view_team_input,
            this.teamsContainer,
            false
        );
        final TextView label = view.findViewById(R.id.label_team_name);
        final int teamNumber = this.inputs.size() + 1;
        label.setText(this.layoutInflater.getContext().getString(R.string.label_team_name, teamNumber));
        final EditText editText = view.findViewById(R.id.input_team_name);
        final EditStringInput input = new EditStringInput(editText);
        this.inputs.add(input);
        this.teamsContainer.addView(view);
        return this;
    }

    public List<EditStringInput> inputs() {
        return this.inputs;
    }
}
