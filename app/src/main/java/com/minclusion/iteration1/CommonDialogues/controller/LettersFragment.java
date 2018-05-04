package com.minclusion.iteration1.CommonDialogues.controller;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.minclusion.iteration1.R;

import java.io.IOException;
import java.util.ArrayList;

import adapters.letterAdapter;
import db.Consonant;
import db.Word;

public class LettersFragment extends Fragment implements View.OnClickListener, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    private GridView gvWords;
    private String letterName, letterType, letterImg, titleWord, description = "";
    private Integer letterId;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceView mSurfaceView;
    private Spinner dropdown;
    private String vowelType = "short";
    private ArrayList<Word> words = null;
    private MediaPlayer mPlayer;
    private String vowelNameinPath = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab_letter, container, false);

        Button playVowel = (Button) rootView.findViewById(R.id.playVowel);
        playVowel.setOnClickListener(this);

        final TextView vowelTxt = (TextView) rootView.findViewById(R.id.vowelType);
        letterId = getArguments().getInt("letter");
        letterType = getArguments().getString("letterType");
        letterImg = getArguments().getString("letterImage");
        titleWord = getArguments().getString("titleWord");
        description = getArguments().getString("description");

        gvWords = (GridView) rootView.findViewById(R.id.gvVowel);

        dropdown = (Spinner) rootView.findViewById(R.id.vowelTypes);
        String[] items = new String[]{getResources().getString(R.string.shortVowel), getResources().getString(R.string.longVowel)};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        letterName = getArguments().getString("letterName");
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (dropdown.getSelectedItemPosition()) {
                    case 1:
                        vowelType = "long";
                        break;
                    case 0:
                        vowelType = "short";
                        break;
                }
                setGridviewAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        vowelTxt.setText(getResources().getString(R.string.listenVowel) + " " + letterName);

        if (letterType.equals("vowel")) {
            initializeVideo(rootView);
            words = Word.getWordsPerLetter(letterName);
        } else {
            initializeImage(rootView);
            words = Consonant.getAllInWords(Integer.toString(letterId));
        }
        setGridviewAdapter();

        return rootView;
    }

    public void setGridviewAdapter() {
        gvWords.setAdapter(new letterAdapter(getContext(), words, letterId, letterName, vowelType, letterType));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onClick(View v) {
        initializeVideo(v);
        playVideo();
    }

    public void initializeImage(View v) {
        SurfaceView video = (SurfaceView) v.findViewById(R.id.vSurfaceView);
        LinearLayout playlayout = (LinearLayout) v.findViewById(R.id.playlayout);
        Spinner spinner = (Spinner) v.findViewById(R.id.vowelTypes);

        spinner.setVisibility(View.GONE);
        playlayout.setVisibility(View.GONE);
        video.setVisibility(View.GONE);

        TextView consWord = (TextView) v.findViewById(R.id.consonantWord);
        TextView consDesc = (TextView) v.findViewById(R.id.consonantDescription);
        ImageView consonantImg = (ImageView) v.findViewById(R.id.consonantImg);

        consWord.setText(titleWord.toLowerCase());
        consDesc.setText(description);

        int id = getResources().getIdentifier(letterImg, "raw", getContext().getPackageName());
        consonantImg.setImageResource(id);

    }

    public void initializeVideo(View v) {
        TextView consWord = (TextView) v.findViewById(R.id.consonantWord);
        TextView consDesc = (TextView) v.findViewById(R.id.consonantDescription);

        if (consWord != null && consDesc != null) {
            consWord.setVisibility(View.GONE);
            consDesc.setVisibility(View.GONE);
        }

        try {
            switch (letterName) {
                case "ä":
                    vowelNameinPath = "ae";
                    break;
                case "å":
                    vowelNameinPath = "aa";
                    break;
                case "ö":
                    vowelNameinPath = "oe";
                    break;
                default:
                    vowelNameinPath = letterName;
            }
            vowelNameinPath = vowelNameinPath + "_" + vowelType;

            mSurfaceView = (SurfaceView) v.findViewById(R.id.vSurfaceView);
            mSurfaceHolder = mSurfaceView.getHolder();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playVideo() {
        if (mPlayer != null)
            mPlayer.release();

        mPlayer = MediaPlayer.create(getContext(), getResources().getIdentifier(vowelNameinPath, "raw", getActivity().getPackageName()));
        mPlayer.setDisplay(mSurfaceHolder);
        mPlayer.start();

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mPlayer.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mPlayer.setDisplay(mSurfaceHolder);
        mPlayer = MediaPlayer.create(getContext(), getResources().getIdentifier(vowelNameinPath, "raw", getActivity().getPackageName()));
        try {
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mPlayer.setOnPreparedListener((MediaPlayer.OnPreparedListener) getContext());
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}

