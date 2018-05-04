package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minclusion.iteration1.R;

import java.util.ArrayList;
import java.util.List;

import db.Question;
import interfaces.SelectedChoice;


public class FibExerciseStepAdapter extends PagerAdapter implements SelectedChoice {

    private Context context;
    //private FibQuestionAdapter fibQuestionAdapter = null;
    private List<FibQuestionAdapter> observers = new ArrayList<>();
    //private View fillInTheBlank_View = null;
    private List<View> pagesOfQuestions = new ArrayList<>();

    private List<entities.game.ExerciseStep> exerciseSteps;
    //private HorizontalGridView questionGridView;


    private ArrayList<Question> questions;
    private List<Question> questionsInSteps;
    private HorizontalGridView choiceGridView;

    //TODO create an entity ExerciseStep and get questions and choices arraylists
    public FibExerciseStepAdapter(List<entities.game.ExerciseStep> exerciseSteps, Context ctx) { //List<Question> questions, List<Choice> choices,
        this.context = ctx;

        this.exerciseSteps = exerciseSteps;
    }

    @Override
    public int getCount() {
        return exerciseSteps.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return o == view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        Log.e("msg: ", "destroyed"); //Integer.toString(FillInTheBlankFragment.pagePosition

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater layoutinflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View fillInTheBlank_View = layoutinflater.inflate(R.layout.fib_layout,
                container, false);
        fillInTheBlank_View.setTag("view" + position);

        questions = new ArrayList<>();

        //Copy the initial question
        questionsInSteps = Question.copyAll(exerciseSteps.get(position).getQuestion(), "");

        // a working copy of the question (modified by the user)
        questions = Question.copyAll(questionsInSteps, "");

        String newQuestionString = "";
        for (Question q : questions) {
            newQuestionString += q.getQuestionString() + "-";
        }
        Log.e("INITIAL QUESTION", newQuestionString + "When position = " + position);

        FibQuestionAdapter fibQuestionAdapter = new FibQuestionAdapter(questions);

        HorizontalGridView questionGridView = fillInTheBlank_View.findViewById(R.id.exerciseQuestionsGridview);
        questionGridView.setAdapter(fibQuestionAdapter);

        choiceGridView = fillInTheBlank_View.findViewById(R.id.exerciseChoiceGridview);
        FibChoiceAdapter choiceAdapter = new FibChoiceAdapter(exerciseSteps, exerciseSteps.get(position).getChoices(), this);
        choiceAdapter.setPagePosition(position);
        choiceGridView.setAdapter(choiceAdapter);


        // DONT CHANGE THE DATA MODEL WHILE WE ARE IN PROGRESS
        try {
            observers.get(position);
        } catch (Exception ex) {
            observers.add(position, fibQuestionAdapter);
        }
        try {
            fillInTheBlank_View = pagesOfQuestions.get(position);
        } catch (Exception ex) {
            pagesOfQuestions.add(position, fillInTheBlank_View);
        }

        container.addView(fillInTheBlank_View);




        return fillInTheBlank_View;
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void respond(int selectedChoiceOrder, String choice) {

        Log.e("selected choice: ", "Choice =" + choice + ", Page #" + selectedChoiceOrder); //Integer.toString(FillInTheBlankFragment.pagePosition

        List<Question> temp = Question.copyAll(observers.get(selectedChoiceOrder).getQuestions(), choice);
        String newQuestionString = "";
        for (Question q : temp) {
            newQuestionString += q.getQuestionString() + "-";
        }
        Log.e("New QUESTION", newQuestionString);

        observers.get(selectedChoiceOrder).setQuestions(temp);
        observers.get(selectedChoiceOrder).notifyDataSetChanged();
        pagesOfQuestions.get(selectedChoiceOrder).invalidate();
        ((HorizontalGridView) pagesOfQuestions.get(selectedChoiceOrder).findViewById(R.id.exerciseQuestionsGridview)).
                setAdapter(observers.get(selectedChoiceOrder));

    }
}
