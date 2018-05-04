package com.minclusion.iteration1.cookandlearn.controller;

/**
 * Created by cylic on 3/2/18.
 */

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minclusion.iteration1.BaseActivity;
import com.minclusion.iteration1.R;
import com.minclusion.iteration1.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import adapters.ChoiceTouchListener;
import adapters.DragAndDropChoicesAdapter;
import adapters.DragAndDropImagesAdapter;
import entities.cookandlearn.Ingredient;

public class DragAndDropWordToImageGame extends BaseActivity {

    // view to display the questions (images of ingredients) in a recycler view
    private RecyclerView questionsRecyclerView;
    //view to display the choices (text of different ingredients, to be dragged and dropped
    //into the images shown in the questionsRecyclerView)
    private RecyclerView choicesRecyclerView;

    // Ingredient data adapter
    private DragAndDropImagesAdapter questionsAdapter;
    // Ingredient data adapter
    private DragAndDropChoicesAdapter choicesAdapter;

    private List<Ingredient> questions;
    private List<Ingredient> choices;
    private List<Ingredient> ingredientList;

    //exercise step
    private Integer exerciseStep;
    // the dish this exercise belongs to
    private Integer dishId;

    private Button next;

    private ViewGroup gameContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the dish selected
        try {
            dishId = getIntent().getIntExtra("dishId", Util.NON_EXISTING_ID);
            exerciseStep = getIntent().getIntExtra("step", Util.NON_EXISTING_ID);
        }catch(Exception ex){

        }

        setContentView(R.layout.activity_drag_and_drop_to_images);

        gameContainer = findViewById(R.id.game_container);
        gameContainer.setOnDragListener(new IgnoreDragListener());

        // call BaseActivity method to initialize the application drawer and toolbar
        initializeToolBarAndAppDrawer();
        toolbar.setTitle(getString(R.string.cook_and_learn));

        // generate questions and choices
        this.generateQuestions();

        // get the part of the page where images of ingredients are displayed
        questionsRecyclerView = findViewById(R.id.questions_view);
        // set the ingredients layout as a two column window
        GridLayoutManager gridLayoutManager = new GridLayoutManager(
                DragAndDropWordToImageGame.this,
                2);
        questionsRecyclerView.setLayoutManager(gridLayoutManager);
        // configure the ingredient data adapter
        questionsAdapter = new DragAndDropImagesAdapter(questions);
        questionsRecyclerView.setAdapter(questionsAdapter);


        // get the part of the page where images of ingredients are displayed
        choicesRecyclerView = findViewById(R.id.choices_view);
        // set the choices listed  as a two column window
        choicesRecyclerView.setLayoutManager(
                new GridLayoutManager(
                DragAndDropWordToImageGame.this,
                2));
        // configure the ingredient data adapter
        choicesAdapter = new DragAndDropChoicesAdapter(choices);
        choicesRecyclerView.setAdapter(choicesAdapter);


        // get a reference to the next button
        next = findViewById(R.id.nextExercise);
        // wire an event to the next button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exerciseStep >= 2) {
                    Intent intent = new Intent(getApplicationContext(), DishActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), DragAndDropWordToImageGame.class);
                    intent.putExtra("dishId", dishId);
                    intent.putExtra("step", exerciseStep + 1);
                    startActivity(intent);
                }
            }
        });
    }

    private void generateQuestions(){
        ingredientList = Ingredient.getAll(dishId);
        questions = new ArrayList<>();
        choices = new ArrayList<>();

        // create a seed
        Random rand = new Random();

        // pick a number between [0, ingredientList.size() )
        int question1 = rand.nextInt(ingredientList.size() - 1);
        int question2 = question1;
        while(question2 == question1){
            question2 = rand.nextInt(ingredientList.size() - 1);
        }

        // add two questions
        questions.add(ingredientList.get(question1));
        questions.add(ingredientList.get(question2));

        // add two extra choices
        int choice1 = question1;
        int choice2 = question1;
        while((choice1 == question1 || choice1 == question2) ||     // if choice1 exists in questions
                (choice2 == question1 || choice2 == question2) ||   // if choice2 exists in questions
                (choice1 == choice2) ){                             // if choices are same
            choice1 = rand.nextInt(ingredientList.size() - 1);
            choice2 = rand.nextInt(ingredientList.size() - 1);
        }

        // add the questions to the choices
        Random choiceRand = new Random();
        List<Ingredient> temp = new ArrayList<>();
        temp.add(ingredientList.get(question1));
        temp.add(ingredientList.get(question2));
        temp.add(ingredientList.get(choice1));
        temp.add(ingredientList.get(choice2));


        while(!temp.isEmpty()){   // (0 - 3) - 1
            int nextChoice = 0;
            if(temp.size() == 1 ){
                nextChoice = 0;
            }else {
                nextChoice = choiceRand.nextInt(temp.size() - 1);
            }

            choices.add(temp.get(nextChoice));

            temp.remove(nextChoice);
        }
    }

    private final class IgnoreDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            View view;

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
                    //Log.e("DRAG", "ACTION_DROP");
                    view = (View) event.getLocalState();
                    view.setVisibility(View.VISIBLE);
                    view.setAlpha(1f);

                    view.setOnTouchListener(new ChoiceTouchListener());

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //Log.e("DRAG", "ACTION_DRAG_ENDED");
                    view = (View) event.getLocalState();

                    view.setVisibility(View.VISIBLE);
                    view.setAlpha(1f);

                    view.setOnTouchListener(new ChoiceTouchListener());
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}