package com.minclusion.iteration1.CommonDialogues.controller.game;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.minclusion.iteration1.R;

import java.util.ArrayList;

import adapters.McExerciseAdapter;
import entities.commondialogues.Exercise;


public class McSectionFragment extends android.support.v4.app.Fragment implements ViewPager.OnPageChangeListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private ViewPager viewpager;
    private static ArrayList<Exercise> exercises;
    private static MediaPlayer mPlayer;
    private static SurfaceHolder mSurfaceHolder;

    public McSectionFragment() {
    }

    public ViewPager getViewPager() {
        return viewpager;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View multiplechoice_item = inflater.inflate(R.layout.fragment_multiplechoice, container, false);
        McExerciseAdapter mcExerciseAdapter = null;

        String mcType = getActivity().getIntent().getExtras().getString("exercisetype");
        ExerciseActivity activity = (ExerciseActivity) getActivity();
        String level = activity.getLevel();

        Exercise e = new Exercise(getContext(), mcType);
        exercises = e.getAll(level);

        mcExerciseAdapter = new McExerciseAdapter(getContext(), exercises, mcType);
        viewpager = multiplechoice_item.findViewById(R.id.mc_view_pager);
        viewpager.setAdapter(mcExerciseAdapter);
        viewpager.addOnPageChangeListener(this);

        return multiplechoice_item;
    }

    Boolean first = true;

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        if (first && v == 0 && i1 == 0) {
            onPageSelected(0);
            first = false;
        }
    }

    @Override
    public void onPageSelected(int i) {
        View viewTag = viewpager.findViewWithTag("view" + viewpager.getCurrentItem());

        SurfaceView mSurfaceView = viewTag.findViewById(R.id.mcSurfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();

        setVideo();

        ImageView btnplay = viewTag.findViewById(R.id.playVideoSpelling);
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVideo();
            }
        });
    }

    private void setVideo() {

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
        try {
            mPlayer = MediaPlayer.create(getContext(), getContext().getResources().
                    getIdentifier(exercises.get(viewpager.getCurrentItem()).getVideoPath(),
                            "raw", getContext().getPackageName()));

            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnCompletionListener(this);

        } catch (NullPointerException e) {
            throw new NullPointerException(e.toString());
        }

        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (mPlayer != null) {
                    mPlayer.setDisplay(holder);
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                if (mPlayer != null) {
                    mPlayer.setDisplay(holder);
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mPlayer != null) {
                    mPlayer.release();
                    mPlayer = null;
                }
            }
        });
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if (mPlayer != null) {
            mPlayer.setDisplay(mSurfaceHolder);
            mPlayer.start();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
