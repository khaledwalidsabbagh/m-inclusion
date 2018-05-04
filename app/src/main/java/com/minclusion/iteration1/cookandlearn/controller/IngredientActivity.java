package com.minclusion.iteration1.cookandlearn.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.minclusion.iteration1.BaseActivity;
import com.minclusion.iteration1.R;
import com.minclusion.iteration1.utils.Util;

import java.util.List;

import adapters.IngredientAdapter;
import entities.cookandlearn.Dish;

import static android.view.View.OVER_SCROLL_ALWAYS;


/**
 * IngredientActivity handles displaying the list of ingredients for
 * a dish. When an item is clicked TODO it should zoom and show the ingredient
 */
public class IngredientActivity extends BaseActivity {

    // Show cooking steps button
    private FloatingActionButton floatingActionButton;
    // view to display ingredients in a recycler view
    private RecyclerView ingredientRecyclerView;
    // Ingredient data adapter
    private IngredientAdapter ingredientAdapter;

    //references selected dish ID
    private Integer dishId = Util.NON_EXISTING_ID;

    // name of the selected dish
    private String dishName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // specify the UI layout file used in this app
        setContentView(R.layout.activity_ingredient);
        TextView title = findViewById(R.id.ingredients_title);


        // call BaseActivity method to initialize the application drawer and toolbar
        initializeToolBarAndAppDrawer();
        toolbar.setTitle(getString(R.string.cook_and_learn));

        // get the part of the page where ingredients are displayed
        ingredientRecyclerView = findViewById(R.id.ingredient_recycler_view);

        // set the ingredients layout as a two column window
        GridLayoutManager gridLayoutManager = new GridLayoutManager(IngredientActivity.this,
                2);
        ingredientRecyclerView.setLayoutManager(gridLayoutManager);

        // get the dish selected
        try {
            dishId = getIntent().getIntExtra("dishId", Util.NON_EXISTING_ID);
            //this.dishName = getIntent().getStringExtra("dishName");
            //fill the model with sample data
            try {
                List<Dish> dishes = Dish.getAll();
                for(Dish d: dishes){
                    if(d.getId().equals(dishId)){
                        this.dishName = d.getName();
                        break;
                    }
                }
            }
            catch(Exception ex){
                Toast.makeText(getApplicationContext(), "Cant get dish name for " + dishId, Toast.LENGTH_SHORT).show();
            }

        }catch(Exception ex){
            Log.e("ERROR_PARSING", ex.toString());
        }
        //set the title with "ingredients (selected dish name)"
        title.setText(getString(R.string.ingredients_title) + " (" + this.dishName + ")");


        // configure the ingredient data adapter
        ingredientAdapter = new IngredientAdapter(dishId);
        ingredientRecyclerView.setAdapter(ingredientAdapter);
        ingredientRecyclerView.setOverScrollMode(OVER_SCROLL_ALWAYS);

        //get the floating (cooking step) button and associate an action to it
        floatingActionButton = findViewById(R.id.show_cooking_step);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // when the floating cooking step button is clicked start the CookingStepActivity
                Intent stepsIntent = new Intent(IngredientActivity.this,
                        CookingStepActivity.class);

                // pass the selected dish ID to the steps activity
                stepsIntent.putExtra("dishId", dishId);

                startActivity(stepsIntent);
            }
        });
    }
}
