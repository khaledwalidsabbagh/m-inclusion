package adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.minclusion.iteration1.CommonDialogues.controller.game.FillInTheBlankFragment;
import com.minclusion.iteration1.R;

import java.util.List;

import db.Choice;
import entities.game.ExerciseStep;
import interfaces.SelectedChoice;

public class FibChoiceAdapter extends RecyclerView.Adapter<FibChoiceAdapter.SimpleViewHolder> {

    private List<Choice> choices;
    //    private FibQuestionAdapter fibQuestionAdapter;
    private List<entities.game.ExerciseStep> exerciseSteps;
    private static Integer clickIndex = 0;

//    private FibExerciseStepAdapter fibExerciseStepAdapter;

    private Integer pagePosition;
    public Integer getPagePosition() {
        return pagePosition;
    }
    public void setPagePosition(Integer pagePosition) {
        this.pagePosition = pagePosition;
    }

    public SelectedChoice mCallBack;

    public FibChoiceAdapter(List<ExerciseStep> exerciseSteps, List<Choice> choices, SelectedChoice mCallBack) { //, SelectedChoice mCallBack
//        super(context,c,flags);
        this.mCallBack = mCallBack;
        this.choices = choices;
        this.exerciseSteps = exerciseSteps;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final Button exerciseStepChoice;

        public SimpleViewHolder(View view) {
            super(view);
            exerciseStepChoice = view.findViewById(R.id.exerciseStepChoice);
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_choice_element, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, final int position) {
        holder.exerciseStepChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Selected Choice", "Position = "+pagePosition + " Choice = "+ choices.get(position).getChoice());
                if (choices.get(position).getCorrect()) {  //compare to correct choices only
                    mCallBack.respond(pagePosition, choices.get(position).getChoice());

//                    FibExerciseStepAdapter.fibQuestionAdapter.notifyDataSetChanged();

                    // notifyDataSetChanged();
                }
            }
        });
        holder.exerciseStepChoice.setText(choices.get(position).getChoice());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return choices.size();
    }
}