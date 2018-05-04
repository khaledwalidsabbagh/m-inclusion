package com.minclusion.iteration1.CommonDialogues.controller.dialogue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;

import com.minclusion.iteration1.R;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;

/**
 * Created by khaled1 on 2018-03-19.
 */

public class MoreOnDialogueActivity extends YouTubeBaseActivity implements View.OnClickListener {

    private YouTubePlayerView youTubePlayerView;
    //private YouTubePlayer.OnInitializedListener onInitializedListener;

    private String videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_on_dialogues);

        Intent intent = getIntent();
        String dialogueTitle = intent.getStringExtra("DialogueTitle");
        videoUri = intent.getStringExtra("videoUri");
        TextView txtDialogueTitle = findViewById(R.id.txtDialogueTitle);
        TextView txtClickOnTheFilm = findViewById(R.id.txtClickOnTheFilm);
        youTubePlayerView = findViewById(R.id.youtube_player);
        ImageView btnPlay = findViewById(R.id.youtubePlayBtn);

        txtDialogueTitle.setText(dialogueTitle);
        txtClickOnTheFilm.append(dialogueTitle);



        //initialize the youtube player to play a specific video when a user clicks the play
        //button
        initializePlayer();

        //add a listener for the play button
        btnPlay.setOnClickListener(MoreOnDialogueActivity.this);

    }

    public void initializePlayer(){
        youTubePlayerView.initialize(new YouTubePlayerInitListener() {
            @Override
            public void onInitSuccess(final YouTubePlayer initializedYouTubePlayer) {
                initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady() {
                        initializedYouTubePlayer.loadVideo(videoUri, 0);
                    }
                });
            }
        }, true);

    }


    @Override
    public void onClick(View view) {
        initializePlayer();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
