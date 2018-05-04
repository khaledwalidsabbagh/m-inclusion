package adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.minclusion.iteration1.CommonDialogues.controller.game.ExerciseActivity;
import com.minclusion.iteration1.R;

import java.util.List;

import entities.commondialogues.Answer;

import com.minclusion.iteration1.utils.ScoreManager;
import com.minclusion.iteration1.utils.UsageLogger;

public class McChoiceAdapter extends ArrayAdapter<Answer> {
    private Context context;
    private List<Answer> answers;

    private ExerciseActivity mc;
    private MediaPlayer mPlayer;
    private Button buttonAnswer;
    private ImageView av;
    private String mcType, question = "";
    private View exercise_item;

    public McChoiceAdapter(Context context, List<Answer> answers, ExerciseActivity exerciseActivity, String mcType, String question, View exercise_item) {
        super(context, R.layout.grid_answer_element, answers);
        this.context = context;
        this.answers = answers;
        this.mc = exerciseActivity;
        this.mcType = mcType;
        this.question = question;
        this.exercise_item = exercise_item;
    }

    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.grid_answer_element, parent, false);

        final Answer answer = answers.get(position);
        av = (ImageView) view.findViewById(R.id.playChoice);

        if (mcType != null && mcType.equals("vowel")) {
            av.setVisibility(View.VISIBLE);
            av.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPlayer = MediaPlayer.create(getContext(), getContext().getResources()
                            .getIdentifier(answer.getSoundFile(), "raw", getContext().getPackageName()));
                    if (mPlayer != null) {
                        mPlayer.start();
                        UsageLogger.appendActivity(v.getContext(), "play answer id, " + answer.getId());
                    }
                }
            });
        } else
            av.setVisibility(View.GONE);

        buttonAnswer = (Button) view.findViewById(R.id.answerBtn);
        buttonAnswer.setText(answers.get(position).getAnswerSe());
        buttonAnswer.setTag(answers.get(position));
        buttonAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer a = (Answer) v.getTag();
                ViewGroup layout =  exercise_item.findViewById(R.id.gridViewMultipleChoice);
                recursiveChecker(layout); //iterating through viewgroup's children to mark the correct and wrong buttons in an exercise

                if (a.isCorrect()) {
                    mc.score = mc.score + 1;
                    ScoreManager scoreManager = new ScoreManager(getContext());
                    scoreManager.saveScore(mc.score);
                    new ScoreManager(getContext()).updateScore();
                }
            }
        });

        return view;
    }

    private void recursiveChecker(ViewGroup parent) { //identify which button was clicked
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                recursiveChecker((ViewGroup) child);
            } else if (child instanceof Button) {
                Button button = (Button) child;
                Answer a = (Answer) button.getTag();
                button.setEnabled(false);
                if (a.isCorrect()) {
                    button.setBackgroundColor(context.getResources().getColor(R.color.correctAnswer));
                    if (!mcType.equals("vowel")) {
                        button.setText(question);
                    }
                } else {
                    button.setBackgroundColor(context.getResources().getColor(R.color.wrongAnswer));
                }
            }
        }
    }

    @Override
    public int getCount() {
        return answers.size();
    }

}
