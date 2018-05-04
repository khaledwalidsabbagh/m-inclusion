package com.minclusion.iteration1.cookandlearn.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.minclusion.iteration1.BaseActivity;
import com.minclusion.iteration1.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.ListItemClickListener;
import entities.cookandlearn.ImageQuiz;

/**
 * IngredientActivity handles displaying the list of ingredients for
 * a dish. When an item is clicked TODO it should zoom and show the question
 * @author Lisanu Tebikew Yallew on 1/22/18
 */
public class GuessImageGameActivitiy extends BaseActivity
        implements ListItemClickListener {

    // Show hints button
    FloatingActionButton hintFloatingActionButton;

    // view to display choices in a recycler view
    RecyclerView choicesRecyclerView;

    // Quiz choices data adapter
    GuessImageGameAdapter choicesAdapter;

    // indicateds the current question being played
    Integer currentQuestion = 0;

    // holds the image shown on quiz question
    ImageView quizQuestionImage;

    // holds instructions about the quiz
    TextView instructionsTxt;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // specify the UI layout file used in this app
        setContentView(R.layout.activity_game_guess_image);

        // call BaseActivity method to initialize the application drawer and toolbar
        initializeToolBarAndAppDrawer();

        // get the part of the page where choices are displayed
        choicesRecyclerView = findViewById(R.id.choices_recycler_view);

        // set the ingredients layout as a two column window
        GridLayoutManager gridLayoutManager = new GridLayoutManager(GuessImageGameActivitiy.this,
                2);
        choicesRecyclerView.setLayoutManager(gridLayoutManager);

        // configure the ingredient data adapter
        choicesAdapter = new GuessImageGameAdapter(getApplicationContext(), this);
        choicesRecyclerView.setAdapter(choicesAdapter);

        // get a reference to the quiz question
        quizQuestionImage = findViewById(R.id.question_image);

        // construct a resouurce id
        int resID = getApplicationContext().getResources().getIdentifier(
                choicesAdapter.getChoiceMap().get(currentQuestion).getImage(),
                "raw", getApplicationContext().getPackageName());

        // load image with Glide library
        Glide.with(getApplicationContext())
                .load(resID)
                .centerCrop()
                .into(quizQuestionImage);

        // get a reference to the instructions UI element
        instructionsTxt = findViewById(R.id.instructions);
        instructionsTxt.setText(choicesAdapter.getChoiceMap().get(currentQuestion).getQuestion());

        //get the floating (cooking step) button and associate an action to it
        hintFloatingActionButton = findViewById(R.id.hint);
        hintFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO show a hint
                Toast.makeText(getApplicationContext(),
                        "Plese give me a hint", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        //Log.d("List item clicked",clickedItemIndex+"");
        //TODO: show a bigger view of the ingredient when the ingredient is clicked
        //Toast.makeText(getApplicationContext(),"Nope!", Toast.LENGTH_SHORT).show();
    }


    class GuessImageGameAdapter extends RecyclerView.Adapter<com.minclusion.iteration1.cookandlearn.controller.GuessImageGameActivitiy.GuessImageGameAdapter.MenuViewHolder> {

        // bind with UI elements
        TextView choice;

        // indicateds the current question being played
        Integer currentQuestion = 0;

        // listener for clicking on choices
        ListItemClickListener listener;

        //model for this ingredient view
        Map<Integer, ImageQuiz> choiceMap;

        // used to refer to the activity
        Context context;


        /**
         * @param context the application context
         * @param itemClickListener listener for this view
         */
        public GuessImageGameAdapter(Context context, ListItemClickListener itemClickListener) {
            this.listener = itemClickListener;

            //fill the model with sample data
            //TODO fix me
            choiceMap = new HashMap<>();

            // create sample choices
            List<String> choices = new ArrayList<String>();
            choices.add("smör");
            choices.add("strösocker");
            choices.add("vaniljsocker");
            choices.add("smör");


            choiceMap.put(0, new ImageQuiz("What is shown in the image? Click on the correct alternative",
                    "strösocker", "strösocker",
                    "strosocker", choices));

            // save the context for latter
            this.context = context;
        }

        @Override
        public com.minclusion.iteration1.cookandlearn.controller.GuessImageGameActivitiy.GuessImageGameAdapter.MenuViewHolder
        onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.choice_list_item, parent, false);

            choice = view.findViewById(R.id.choice);

            return new com.minclusion.iteration1.cookandlearn.controller.GuessImageGameActivitiy.GuessImageGameAdapter.MenuViewHolder(view);
        }

        @Override
        public void onBindViewHolder(com.minclusion.iteration1.cookandlearn.controller.GuessImageGameActivitiy.GuessImageGameAdapter.MenuViewHolder holder, int position) {
            choice.setText(choiceMap.get(currentQuestion).getChoices().get(position));
        }

        @Override
        public int getItemCount() {
            return choiceMap.get(currentQuestion).getChoices().size();
        }

        public Map<Integer, ImageQuiz> getChoiceMap() {
            return choiceMap;
        }

        class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public MenuViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                int clickedPosition = getAdapterPosition();
                //Log.d("List item clicked", clickedPosition + "");
                listener.onListItemClick(clickedPosition);
            }

        }

    }

}
