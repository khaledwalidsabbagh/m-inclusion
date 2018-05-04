package com.minclusion.iteration1.CommonDialogues.controller.game;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.minclusion.iteration1.R;

import java.util.ArrayList;
import java.util.List;

import adapters.FibExerciseStepAdapter;
import db.Question;
import entities.game.ExerciseStep;
import interfaces.SelectedChoice;


public class FillInTheBlankFragment extends Fragment implements ViewPager.OnPageChangeListener {

    public static ViewPager viewpager;
    private MediaPlayer mPlayer;
    public static Integer pagePosition = 0;
    private List<db.ExerciseStep> exercises;
    FibExerciseStepAdapter fibExerciseStepAdapter = null;

    public FillInTheBlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static ViewPager getViewPager() {
        return viewpager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fillintheblank_item = inflater.inflate(R.layout.fragment_fill_in_the_blank,
                container, false);

        List<ExerciseStep> exerciseSteps = new ArrayList<>();
        //TODO bind session id dynamically
        List<db.ExerciseStep> exerciseStepsDb = ExerciseStep.getAll(0); //getting all exercise steps within session 0, we only have one session at the moment
        for (int i = 0; i < exerciseStepsDb.size(); i++) {
            ExerciseStep exerciseStep = new ExerciseStep(i);
            exerciseSteps.add(exerciseStep);
        }

        fibExerciseStepAdapter = new FibExerciseStepAdapter(exerciseSteps, getContext());

        exercises = ExerciseStep.getAll(0);
        viewpager = fillintheblank_item.findViewById(R.id.fibViewPager);
        viewpager.setAdapter(fibExerciseStepAdapter);
        viewpager.addOnPageChangeListener(this);

        return fillintheblank_item;
    }

    Boolean first = true;

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        if (first && v == 0 && i1 == 0) {
            onPageSelected(i);
            first = false;
        }
    }

    @Override
    public void onPageSelected(int i) {
        View viewTag = viewpager.findViewWithTag("view" + viewpager.getCurrentItem());
        pagePosition = viewpager.getCurrentItem();

        ImageView listen_view = viewTag.findViewById(R.id.listenExImg);
        listen_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudioRecording();
            }
        });
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void checkAnswer() {
        //TODO check the correct answer and calculate score
    }

    private void playAudioRecording() {

        mPlayer = MediaPlayer.create(getContext(), getContext().getResources().
                getIdentifier(exercises.get(viewpager.getCurrentItem()).exerciseStepAudioPath(),
                        "raw", getContext().getPackageName()));
        mPlayer.start();
    }


}
