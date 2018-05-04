package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.minclusion.iteration1.CommonDialogues.controller.game.ExerciseActivity;
import com.minclusion.iteration1.R;


import java.util.ArrayList;

import entities.commondialogues.Exercise;

/**
 * Created by khaled1 on 2018-02-17.
 */

public class McExerciseAdapter extends PagerAdapter {

    private ArrayList<Exercise> exercises = new ArrayList<>();
    private String mcType;
    private Context ctx;
    private EditText spellingTxt;

    public McExerciseAdapter(Context ctx, ArrayList<Exercise> exercises, String mcType) {
        this.mcType = mcType;
        this.ctx = ctx;
        this.exercises = exercises;
    }

    @Override
    public int getCount() {
        return exercises.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (LinearLayout) o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutinflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View exercise_item = null;

        exercise_item = layoutinflater.inflate(R.layout.multiplechoice_layout, container, false);

        exercise_item.setTag("view" + position);
        GridView gridViewChoices = exercise_item.findViewById(R.id.gridViewMultipleChoice);

        if (exercises.get(position).getType().equals("mc")) {
            gridViewChoices.setAdapter(new McChoiceAdapter(ctx,
                    exercises.get(position).getAnswers(),
                    (ExerciseActivity) ctx, mcType, exercises.get(position).getQuestion(), exercise_item));
        } else {
            exercise_item = layoutinflater.inflate(R.layout.spelling_layout, container, false);
            exercise_item.setTag("view" + position);
            spellingTxt = exercise_item.findViewById(R.id.spellingAnswer);

            ImageView submitSpellingAnswer = exercise_item.findViewById(R.id.submitAnswer);
            submitSpellingAnswer.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            submitAnswer(view, position);
                        }
                    }
            );
        }
        container.addView(exercise_item);
        return exercise_item;
    }

    public void submitAnswer(View v, int position) {
        checkAnswer(position);
        spellingTxt.setText("");
        InputMethodManager softKeyboard = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        softKeyboard.hideSoftInputFromWindow(spellingTxt.getWindowToken(), 0);

    }

    private void checkAnswer(int position) {
        if (!spellingTxt.getText().toString().equals("")) {
            if (spellingTxt.getText().toString().equals(exercises.get(position).getQuestion())) {
                Toast.makeText(ctx, ctx.getResources().getString(R.string.correctAnswer), Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(ctx, ctx.getResources().getString(R.string.wrongAnswer) + exercises.get(position).getQuestion(), Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(ctx, ctx.getResources().getString(R.string.missedSpelling), Toast.LENGTH_LONG).show();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
