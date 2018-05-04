package adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.minclusion.iteration1.R;
import com.minclusion.iteration1.cookandlearn.controller.CookingStepClickableSpan;
import com.minclusion.iteration1.utils.QuickActionWindow;
import com.minclusion.iteration1.utils.Util;

import java.util.List;

import entities.cookandlearn.Ingredient;
import entities.cookandlearn.Translation;

/**
 * Created by Lisanu Tebikew Yallew on 1/19/18.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    // bind with UI elements
    private TextView ingredientName, quantity;
    private ImageView itemPicture;

    //model for this ingredient view
    private List<Ingredient> ingredients;

    // used to refer to the activity
    private Context context;

    //translations db conn
    private Translation translation = new Translation();

    /**
     * @param dishId           ID of the selected dish (this activity displays the ingredients
     *                         required)
     */
    public IngredientAdapter(Integer dishId) {

        //fill the model with sample data
        try {
            ingredients = Ingredient.getAll(dishId);
        } catch (Exception ex) {
            ingredients = Ingredient.getEmpty();
            Toast.makeText(context, "Cannot load ingredients. For dish id " + dishId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // save the context for later
        this.context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.dish_list_item, parent, false);

        ingredientName = view.findViewById(R.id.dish_item_name);
        quantity = view.findViewById(R.id.dish_item_cost);
        itemPicture = view.findViewById(R.id.menu_item_picture);

        return new IngredientViewHolder(view);
    }

    public void showQuickAction(TextView textView, final String swedish, final String arabic) {

        QuickActionWindow actionWindow = new QuickActionWindow(context, textView, getRectangle(textView));


        actionWindow.addString(arabic, new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        actionWindow.addImage(ContextCompat.getDrawable(context, R.drawable.ic_play), new View.OnClickListener() {
            public void onClick(View v) {
                // play the music

                String name = Util.getResourceName(swedish);

                int resID = context.getResources().getIdentifier(
                        name + "_audio",
                        "raw", context.getPackageName()
                );

                if (resID == 0) {
                    resID = context.getResources().getIdentifier(
                            name, "raw", context.getPackageName()
                    );
                }

                //play an audio if the file is found
                if (resID != 0) {
                    try {
                        final MediaPlayer player = MediaPlayer.create(context, resID);
                        player.start();
                    } catch (Exception ex) {
                        //if the resource cannot be played skip it

                    }
                }
            }
        });
        actionWindow.show();
    }

    public Rect getRectangle(View widget) {
        TextView parentTextView = (TextView) widget;
        Rect parentTextViewRect = new Rect();

        // Initialize values for the computing of clickedText position
        SpannableString completeText = (SpannableString) (parentTextView).getText();
        Layout textViewLayout = parentTextView.getLayout();

        double startOffsetOfClickedText = completeText.getSpanStart(this);
        double endOffsetOfClickedText = completeText.getSpanEnd(this);
        double startXCoordinatesOfClickedText = textViewLayout.getPrimaryHorizontal((int) startOffsetOfClickedText);
        double endXCoordinatesOfClickedText = textViewLayout.getPrimaryHorizontal((int) endOffsetOfClickedText);

        // Get the rectangle of the clicked text
        int currentLineStartOffset = textViewLayout.getLineForOffset((int) startOffsetOfClickedText);
        int currentLineEndOffset = textViewLayout.getLineForOffset((int) endOffsetOfClickedText);
        boolean keywordIsInMultiLine = currentLineStartOffset != currentLineEndOffset;
        textViewLayout.getLineBounds(currentLineStartOffset, parentTextViewRect);

        // Update the rectangle position to his real position on screen
        int[] parentTextViewLocation = {0, 0};
        parentTextView.getLocationOnScreen(parentTextViewLocation);

        double parentTextViewTopAndBottomOffset = (
                parentTextViewLocation[1] -
                        parentTextView.getScrollY() +
                        parentTextView.getCompoundPaddingTop()
        );
        parentTextViewRect.top += parentTextViewTopAndBottomOffset;
        parentTextViewRect.bottom += parentTextViewTopAndBottomOffset;

        parentTextViewRect.left += (
                parentTextViewLocation[0] +
                        startXCoordinatesOfClickedText +
                        parentTextView.getCompoundPaddingLeft() -
                        parentTextView.getScrollX()
        );
        parentTextViewRect.right = (int) (
                parentTextViewRect.left +
                        endXCoordinatesOfClickedText -
                        startXCoordinatesOfClickedText
        );

        int x = parentTextViewRect.left;

        //int x = (parentTextViewRect.left + parentTextViewRect.right) / 2;
        int y = parentTextViewRect.top;
        if (keywordIsInMultiLine) {
            x = parentTextViewRect.left;
        }

        Log.e("LOCATION", "location2:" + x + "," + y +
                " startXCoordinatesOfClickedText " + startXCoordinatesOfClickedText +
                "endXCoordinatesOfClickedText" + endXCoordinatesOfClickedText);

        return new Rect(x, y, x + 1, y + 1);
    }


    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {


        final String descriptionText = ingredients.get(position).getName();
        //we want to create a clickable spannablestring
        final SpannableString ss = new SpannableString(ingredients.get(position).getName());

        // create a clickable span for each part of ingredient we know
        for (String word : translation.getTranslations().keySet()) {
            String arabicTranslation = translation.getTranslations().get(word);
            //if the description contains a translation as a word
            int startOfWord = descriptionText.indexOf(word);
            if (startOfWord != -1) {

                startOfWord = descriptionText.indexOf(word);
                int endOfWord = startOfWord + word.length();

                CookingStepClickableSpan cSpan = new CookingStepClickableSpan(this.context, word, arabicTranslation);

                //set the color of the clickable element to white
                cSpan.setColor(Color.WHITE);

                ss.setSpan(cSpan, startOfWord, endOfWord, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        ingredientName.setText(ss);
        ingredientName.setMovementMethod(LinkMovementMethod.getInstance());
        ingredientName.setTextColor(Color.WHITE);
        //ingredientName.setHighlightColor(Color.W);
//        // must have a clickable span
//        ingredientName.setText(ingredients.get(position).getName());

        String quantityStr = "";
        if (ingredients.get(position).getQuantity() != null) {
            quantityStr = String.format("%.1f ", ingredients.get(position).getQuantity());
        }
        if (ingredients.get(position).getUnit() != null) {
            quantityStr += ingredients.get(position).getUnit();
        }
        quantity.setText(quantityStr);

        String properResourceName = Util.getResourceName(ingredients.get(position).getImage());

        // construct a resouurce id
        int resID = context.getResources().getIdentifier(properResourceName,
                "raw", context.getPackageName());

        // load image with Glide library
        Glide.with(this.context)
                .load(resID)
                .into(itemPicture);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        public IngredientViewHolder(View itemView) {
            super(itemView);

            // show the arabic translation when the ingredient is clicked
            itemView.findViewById(R.id.card_widget_wrapper).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //show a quick action (popup with arabic translation) when the cardview
                            // is clicked
                            ingredientName = view.findViewById(R.id.dish_item_name);
                            showQuickAction(ingredientName, ingredientName.getText().toString(), translation.translate(ingredientName.getText().toString()));
                        }
                    });
        }
    }

}
