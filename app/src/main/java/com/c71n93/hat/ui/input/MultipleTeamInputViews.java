package com.c71n93.hat.ui.input;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.c71n93.hat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages creation and storage of team name input views inside a container.
 */
// TODO: This class is not configurable and therefore cannot be reused. Figure
// out how dynamic management of multiple inputs can be generalized.
// TODO: Try to use RecyclerView to handle multiple inputs.
// TODO: Add possibility to remove team inputs.
public class MultipleTeamInputViews {
    private final LayoutInflater layoutInflater;
    private final ViewGroup teamsContainer;
    private final List<EditStringInput> inputs = new ArrayList<>();
    private static final int MIN_TEAM_COUNT = 2;

    public MultipleTeamInputViews(final ViewGroup teamsContainer) {
        this(LayoutInflater.from(teamsContainer.getContext()), teamsContainer);
    }

    public MultipleTeamInputViews(final LayoutInflater layoutInflater, final ViewGroup teamsContainer) {
        this.layoutInflater = layoutInflater;
        this.teamsContainer = teamsContainer;
    }

    public MultipleTeamInputViews addInput() {
        final View view = this.layoutInflater.inflate(
            R.layout.view_team_input,
            this.teamsContainer,
            false
        );
        final EditText editText = view.findViewById(R.id.input_team_name);
        final int teamNumber = this.inputs.size() + 1;
        editText.setText(this.layoutInflater.getContext().getString(R.string.label_team_default, teamNumber));
        final EditStringInput input = new EditStringInput(editText);
        this.inputs.add(input);
        this.teamsContainer.addView(view);
        return this;
    }

    public MultipleTeamInputViews removeLastInput() {
        if (this.inputs.size() > MIN_TEAM_COUNT) {
            final int lastIndex = this.inputs.size() - 1;
            this.inputs.remove(lastIndex);
            this.teamsContainer.removeViewAt(lastIndex);
        }
        return this;
    }

    public List<EditStringInput> inputs() {
        return this.inputs;
    }
}
