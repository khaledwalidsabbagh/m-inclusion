package adapters;

/**
 * Created by cylic on 3/15/18.
 */

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.minclusion.iteration1.R;
import com.minclusion.iteration1.cookandlearn.controller.CookingStepActivity;
import com.minclusion.iteration1.utils.Util;

import java.util.List;

import entities.cookandlearn.Ingredient;

/**
 * Created by Lisanu Tebikew Yallew on 1/19/18.
 */

public class  DragAndDropImagesAdapter extends
        RecyclerView.Adapter<DragAndDropImagesAdapter.MenuViewHolder> {

    // bind with UI elements
    private TextView ingredientName, quantity;

    //stores the image of an ingredient shown in the game
    private ImageView image;

    // flag showing weather the user has attempted this question or not
    private Integer correctAnswers = 0;

    // if isAttempted is true, this flag shows if the users guess was correct or not!
    private Boolean isCorrect = false;

    //model for this ingredient view
    private List<Ingredient> ingredients;

    // used to refer to the activity
    private Context context;

    private Button nextButton;

    private ViewGroup parent;

    private ViewGroup cardWidgetWrapper;

    /**
     * the list of questions to show in this screen
     * @param questions
     */
    public DragAndDropImagesAdapter(List<Ingredient> questions) {
        this.ingredients = questions;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;

        // save a reference to the next button, to disable and enable the next button
        this.nextButton = parent.getRootView().findViewById(R.id.nextExercise);
        // disable it initially, so that the user has to attempt all questions
        //this.nextButton.setAlpha(0);
        this.nextButton.setEnabled(false);

        // save the context for latter
        this.context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(this.context);

        // inflate the layout
        View view = inflater.inflate(R.layout.dish_list_item, parent, false);
        image = view.findViewById(R.id.menu_item_picture);

        cardWidgetWrapper = view.findViewById(R.id.card_widget_wrapper);
        cardWidgetWrapper.setOnDragListener(new ChoiceDragListener());

        //register events for the description_area
        //view.findViewById(R.id.description_area).setOnDragListener(new ChoiceDragListener());
        view.findViewById(R.id.description_area).setOnTouchListener(
                new ChoiceTouchListener(cardWidgetWrapper));

        // don't show this component
        view.findViewById(R.id.description_area).setAlpha(0);

        //hide the quantity field
        quantity = view.findViewById(R.id.dish_item_cost);
        ingredientName = view.findViewById(R.id.dish_item_name);
        view.findViewById(R.id.dish_item_cost).setVisibility(View.INVISIBLE);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        //ingredientName.setText("?");
        ingredientName.setText("");
        quantity.setText(
                ingredients.get(position).getImage().replace("_", " "));

        String properResourceName = Util.getResourceName(ingredients.get(position).getImage());

        // construct a resource id
        int resID = context.getResources().getIdentifier(properResourceName,
                "raw", context.getPackageName());

        // load the image with glide library
        Glide.with(this.context)
                .load(resID)
                .into(image);
    }

    private final class ChoiceDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            //handle the dragged view being dropped over a drop view
            View view = (View) event.getLocalState();

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:

                    //view dragged item is being dropped on
                    LinearLayout droppedOn = v.findViewById(R.id.description_area);


                    droppedOn.setAlpha(1f);
                    droppedOn.setVisibility(View.VISIBLE);

                    //view being dragged and dropped
                    LinearLayout dropped = (LinearLayout) view;


                    TextView droppedText = dropped.findViewById(R.id.dish_item_name);
                    TextView origionalText = v.findViewById(R.id.dish_item_name);
                    TextView correctAnswer = v.findViewById(R.id.dish_item_cost);

                    if(droppedText.getText().equals(correctAnswer.getText())) {
                        droppedOn.setBackgroundColor(Color.parseColor("#558C89"));
                        correctAnswers++;

                        //play the sound
                        //replace ä å ö
                        String name = correctAnswer.getText().toString().toLowerCase();
                        name = name.replace("ä", "a");
                        name = name.replace("å", "a");
                        name = name.replace("ö", "o");

                        int resID = parent.getContext().getResources().getIdentifier(name+"_audio",
                                "raw", parent.getContext().getPackageName());

                        // play if the resource is found
                        if(resID != 0) {
                            final MediaPlayer player = MediaPlayer.create(parent.getContext(), resID);
                            player.start();
                        }

                        // enable the next button, since this exercise is done
                        if(correctAnswers.equals(ingredients.size())){
                            nextButton.setEnabled(true);
                            // disable it initially, so that the user has to attempt all questions
                            nextButton.setVisibility(View.VISIBLE);
                            nextButton.setText(context.getString(R.string.next));

                            nextButton.setAlpha(1f);
                        }
                    }
                    else
                        droppedOn.setBackgroundColor(Color.RED);

                    origionalText.setText(droppedText.getText());


                    v.setOnDragListener(null);

                    droppedOn.setOnTouchListener(new ChoiceTouchListener((ViewGroup)v));

                    //if an item has already been dropped here, there will be a tag
                    Object tag = droppedOn.getTag();

                    //if there is already an item here, set it back visible in its original place
                    if (tag != null) {
                        //the tag is the view id already dropped here
                        int existingID = (Integer) tag;
                        //set the original view visible again
                        v.findViewById(existingID).setVisibility(View.VISIBLE);
                    }
                    //show the description are now
                    //findViewById(R.id.description_area).setVisibility(View.VISIBLE);

                    //set the tag in the target view to the ID of the view being dropped
                    droppedOn.setTag(dropped.getId());

                    //droppedOn.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if(!event.getResult()){
                        view.setVisibility(View.VISIBLE);

                    }

                    break;
                default:
                    break;
            }
            return true;
        }
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        private ViewGroup wrapper;

        public ChoiceTouchListener(ViewGroup wrapper){
            this.wrapper = wrapper;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.setAlpha(0);
                view.startDrag(data, shadowBuilder, view, 0);

                // if the user moves a correct answer, decrement the number of correct answers
                if(((ColorDrawable)view.getBackground()).getColor() == Color.BLUE) {
                    correctAnswers--;
                    nextButton.setEnabled(false);
                    // disable it initially, so that the user has to attempt all questions
                    //nextButton.setVisibility(View.INVISIBLE);
                    //nextButton.setAlpha(0f);
                }

                //now an choice can be dropped on the empty question
                //view.findViewById(R.id.description_area).setOnDragListener(new ChoiceDragListener());
                wrapper.setOnDragListener(new ChoiceDragListener());
                //cardWidgetWrapper.setOnDragListener(new ChoiceDragListener());
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MenuViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Log.d("List item clicked", clickedPosition + "");
        }

    }

}
