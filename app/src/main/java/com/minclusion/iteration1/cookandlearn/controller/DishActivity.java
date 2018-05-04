package com.minclusion.iteration1.cookandlearn.controller;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.minclusion.iteration1.R;

import com.minclusion.iteration1.utils.Util;

import java.util.List;

import adapters.DishAdapter;
import adapters.DishFragmentPagerAdapter;
import interfaces.ListItemClickListener;
import entities.cookandlearn.Dish;


public class DishActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ListItemClickListener {
    DrawerLayout mDrawerLayout;
    String mActivityTitle;
    ViewPager pager;
    Fragment[] fragments;
    Toolbar toolbar;
    AppBarLayout appBarLayout;
    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout drawer;
    DishAdapter dishAdapter;

    List<Dish> dishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish_home);

        //crate the fragments based on food type (e.g breakfast dinner, ....)

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        pager = findViewById(R.id.dish_pager);

        //initialize fragments
        dishes = Dish.getAll();
        dishAdapter = new DishAdapter(getApplicationContext(), this, dishes);
        fragments = new Fragment[3];
        String[] MEALS = {"Meal", "Appetizer", "Fika"};

        for(int i=0; i < fragments.length; i++){
            //set what type of meals need to be shown in this fragent
            Bundle bundle = new Bundle();
            bundle.putString("dishType", MEALS[i]);

            //create a fragment and pass the type of meal to the fragment!
            fragments[i] = new DishFragment();
            fragments[i].setArguments(bundle);
        }

        pager.setAdapter(new DishFragmentPagerAdapter(getSupportFragmentManager(), fragments,
                            getApplicationContext()));

        final TabLayout tabLayout = findViewById(R.id.business_home_tabs);
        tabLayout.setupWithViewPager(pager);

        toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle(getString(R.string.cook_and_learn));

        //toolbar.setTitle(R.string.app_name);
        appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.setExpanded(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                NavigationView navigationView = findViewById(R.id.nav_view);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        drawer = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        return Util.handleNavigation(this, mDrawerLayout, id);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        //Log.d("List item clicked",clickedItemIndex+"");
        //TODO remove me! Here for debugging
        Toast.makeText(getApplicationContext(), "Dish selected ID = " + dishAdapter.getDishes().get(clickedItemIndex).getName(), Toast.LENGTH_SHORT).show();

        //start the ingredients view
        Intent ingredientIntent = new Intent(DishActivity.this, IngredientActivity.class);
        ingredientIntent.putExtra("dishId", dishAdapter.getDishes().get(clickedItemIndex).getId());
        Log.e("DISH_NAME", dishAdapter.getDishes().get(clickedItemIndex).getName());
        ingredientIntent.putExtra("dishName", dishAdapter.getDishes().get(clickedItemIndex).getName());
        startActivity(ingredientIntent);
    }
}
