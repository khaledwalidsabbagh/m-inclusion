package adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.minclusion.iteration1.R;

import java.util.List;

import db.Choice;
import db.Question;


/**
 * Created by khaled1 on 2018-03-02.
 */

class FibQuestionAdapter extends RecyclerView.Adapter<FibQuestionAdapter.SimpleViewHolder> {

    private List<Question> questions;
    private static final String DEFAULT_CHOICE = "--";

    public FibQuestionAdapter(List<Question> questions) {
        this.questions = questions;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView exerciseStep;

        public SimpleViewHolder(View view) {
            super(view);
            exerciseStep = view.findViewById(R.id.exerciseStep);
        }
    }

    @Override
    public FibQuestionAdapter.SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_question_element, parent, false);
        return new FibQuestionAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder viewHolder, final int i) {

        if (questions.get(i).getGiven()) { //
            viewHolder.exerciseStep.setText(questions.get(i).getQuestionString());
//            Log.e("letter string: ", questions.get(i).getQuestionString() + " isGiven: " + questions.get(i).getGiven());

        } else {
            viewHolder.exerciseStep.setTextColor(Color.RED);
            viewHolder.exerciseStep.setText(DEFAULT_CHOICE);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    /**
     * clearChoices removes all the choices available in this adapter. Used mainly to clear and
     * recrate the choices after a user selected a specific value
     */
    public void clearQuestions(){
        this.questions = null;
    }

    public void setQuestions(List<Question> questions){
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
