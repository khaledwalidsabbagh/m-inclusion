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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minclusion.iteration1.R;

import java.util.List;

import entities.cookandlearn.Ingredient;

/**
 * Created by Lisanu Tebikew Yallew on 1/19/18.
 */

public class  DragAndDropChoicesAdapter extends
        RecyclerView.Adapter<DragAndDropChoicesAdapter.MenuViewHolder> {

    // bind with UI elements
    TextView ingredientName, quantity;

    //stores the image of an ingredient shown in the game
    ImageView image;

    // flag showing weather the user has attempted this question or not
    Boolean isAttempted = false;

    // if isAttempted is true, this flag shows if the users guess was correct or not!
    Boolean isCorrect = false;

    //model for this ingredient view
    List<Ingredient> ingredients;

    // used to refer to the activity
    Context context;

    /**
     * the list of questions to show in this screen
     * @param questions
     */
    public DragAndDropChoicesAdapter(List<Ingredient> questions) {
        this.ingredients = questions;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // save the context for latter
        this.context = parent.getContext();
        // inflate the layout
        View view = inflater.inflate(R.layout.drag_drop_game_choices_list, parent, false);

        parent.removeView(view.findViewById(R.id.description_area));
        //parent.removeView(view.findViewById(R.id.menu_item_picture));
        parent.removeView(view.findViewById(R.id.dish_item_cost));

        ingredientName = view.findViewById(R.id.dish_item_name);


        view.findViewById(R.id.description_area).setOnTouchListener(new ChoiceTouchListener());
        view.findViewById(R.id.description_area).setOnDragListener(new ChoiceDragListener());
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        ingredientName.setText(
                ingredients.get(position).getImage().replace("_", " "));
    }


    private class ChoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //view.setVisibility(View.INVISIBLE);

                // hide the view
                view.setAlpha(0);

                // don't allow dragging it anymore
                view.setOnTouchListener(null);
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    private final class ChoiceDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
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
                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();

                    //view dragged item is being dropped on
                    LinearLayout dropTarget = (LinearLayout) v;
                    v.setAlpha(1f);

                    //view being dragged and dropped
                    LinearLayout dropped = (LinearLayout) view;

                    v.setVisibility(View.VISIBLE);

                    //get the item names
                    TextView droppedText = dropped.findViewById(R.id.dish_item_name);
                    TextView origionalText = dropTarget.findViewById(R.id.dish_item_name);

                    // now that the choice is put back on it will have a new touch listener,
                    // that will allow dragging
                    v.setOnTouchListener(new ChoiceTouchListener());

                    //set the dropped color
                    origionalText.setText(droppedText.getText());

                    //if an item has already been dropped here, there will be a tag
                    Object tag = dropTarget.getTag();

                    //if there is already an item here, set it back visible in its original place
                    if (tag != null) {
                        //the tag is the view id already dropped here
                        int existingID = (Integer) tag;
                        //set the original view visible again
                        v.findViewById(existingID).setVisibility(View.VISIBLE);
                    }
                    //show the description are now
                    //findViewById(R.id.description_area).setVisibility(View.VISIBLE);

                    //TODO change the color depending on correct or not

                    //set the tag in the target view to the ID of the view being dropped
                    dropTarget.setTag(dropped.getId());

                    //dropTarget.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                    break;
                default:
                    break;
            }
            return true;
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
            //Log.d("List item clicked", clickedPosition + "");
        }

    }

}
