package com.minclusion.iteration1.CommonDialogues.controller.game;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.minclusion.iteration1.R;

import entities.commondialogues.Exercise;

import com.minclusion.iteration1.utils.ScoreManager;

public class ExerciseActivity extends Exercise {

    private TextView scoreTxt, okLink, highScore;
    public int score;
    private static AlertDialog.Builder alertDialogBuilder;
    private static String level = "one";
    private RadioGroup mcLevel;
    private ScoreManager scoreManager;
    private ViewGroup mcLayout;
    private McSectionFragment mcFragment;
    private static FragmentTransaction fragmentTransaction;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_exercise);
        initializeToolBarAndAppDrawer();
        mcLayout = findViewById(R.id.mc_fragment);
        scoreManager = new ScoreManager(ExerciseActivity.this);

        scoreTxt = findViewById(R.id.scoreTxt);
        scoreTxt.setText(getResources().getString(R.string.currentScore) + " " + Integer.toString(score));
        highScore = findViewById(R.id.highScoreTxt);

        Button nextExercise = findViewById(R.id.nextExercise);
        nextExercise.setOnClickListener(bNextListener);

        setExerciseLevel();

    }

    private void setExerciseLevel() {
        LayoutInflater li = LayoutInflater.from(this);
        final View mcExerciseView = li.inflate(R.layout.multiplechoicepopup, null);
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(mcExerciseView);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);

        mcLevel = mcExerciseView.findViewById(R.id.mcLevel);
        okLink = mcExerciseView.findViewById(R.id.okMc);
        okLink.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {

                if (mcLevel.getCheckedRadioButtonId() != -1) {
                    int radioButtonID = mcLevel.getCheckedRadioButtonId();
                    View radioButton = mcLevel.findViewById(radioButtonID);
                    int idx = mcLevel.indexOfChild(radioButton);
                    if (idx == 0)
                        level = "one";
                    else
                        level = "two";

                    mcFragment = new McSectionFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(mcLayout.getId(), mcFragment,
                            McSectionFragment.class.getName());

                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else
                    Toast.makeText(ExerciseActivity.this, getResources().getString(R.string.chooseLevel), Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
                ((ViewGroup) mcExerciseView.getParent()).removeView(mcExerciseView);
            }

        });
    }

    public String getLevel() {
        return level;
    }

    private View.OnClickListener bNextListener = new View.OnClickListener() {
        public void onClick(View v) {
            getNextExercise();
        }
    };

    protected void getNextExercise() {
        viewpager = mcFragment.getViewPager();
        viewpager.setCurrentItem(getItem(+1), true);
        scoreManager.saveScore(score);
        scoreTxt.setText(getResources().getString(R.string.currentScore) + " " + Integer.toString(score));
        highScore.setText(getResources().getString(R.string.highestScore) + scoreManager.updateScore());
    }

    private int getItem(int i) {
        return viewpager.getCurrentItem() + i;
    }

}
