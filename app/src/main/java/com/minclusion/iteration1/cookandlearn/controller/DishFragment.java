package com.minclusion.iteration1.cookandlearn.controller;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.minclusion.iteration1.R;

import java.util.List;
import java.util.Map;

import interfaces.ListItemClickListener;
import adapters.DishAdapter;


import entities.cookandlearn.Dish;

public class DishFragment extends Fragment implements ListItemClickListener {
    RecyclerView menuRecyclerView;
    DishAdapter dishAdapter;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dish,container,false);
        menuRecyclerView = v.findViewById(R.id.menu_recycler_view);

        //determine the type of dish this meals this fragment is supposed to show
        String dishType = getArguments().getString("dishType");
        List<Dish> dishes = Dish.getDishesByType(dishType);

        dishAdapter = new DishAdapter(getActivity().getApplicationContext(), this,
                dishes);
        menuRecyclerView.setAdapter(dishAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        menuRecyclerView.setLayoutManager(gridLayoutManager);

        return v;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d("List item clicked",clickedItemIndex+"");

        //start the ingredients view
        Intent ingredientIntent = new Intent(getActivity(), IngredientActivity.class);
        ingredientIntent.putExtra("dishId",
                                    dishAdapter.getDishes().get(clickedItemIndex).getId());
        startActivity(ingredientIntent);
    }
}
