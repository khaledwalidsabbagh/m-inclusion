package com.minclusion.iteration1.CommonDialogues.controller.game;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.minclusion.iteration1.R;

public class FillInTheBlankActivity extends AppCompatActivity {

    private FillInTheBlankFragment fillInTheBlankFragment;
    private static ViewPager viewpager;
    private ViewGroup mcLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_in_the_blank);

        mcLayout = findViewById(R.id.mc_fragment);

        fillInTheBlankFragment = new FillInTheBlankFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fib_fragment, fillInTheBlankFragment,
                FillInTheBlankFragment.class.getName());

//        fragmentTransaction.replace(R.id.fib_fragment, fillInTheBlankFragment,
//                McSectionFragment.class.getName());

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private View.OnClickListener bNextListener = new View.OnClickListener() {
        public void onClick(View v) {
            getNextExercise();
        }
    };

    protected void getNextExercise() {
//        viewpager = fillInTheBlankFragment.getViewPager();
//        viewpager.setCurrentItem(getItem(+1), true);
//        scoreManager.saveScore(score);
//        scoreTxt.setText(getResources().getString(R.string.currentScore) + " " + Integer.toString(score));
//        highScore.setText(getResources().getString(R.string.highestScore) + scoreManager.updateScore());
    }
}
