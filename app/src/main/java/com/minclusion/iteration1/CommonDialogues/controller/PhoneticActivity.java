package com.minclusion.iteration1.CommonDialogues.controller;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.minclusion.iteration1.BaseActivity;
import com.minclusion.iteration1.R;

import javax.annotation.Nullable;

import com.minclusion.iteration1.utils.MediaHelper;
import db.Word;
import com.minclusion.iteration1.utils.UsageLogger;

public class PhoneticActivity extends BaseActivity implements SurfaceHolder.Callback, ActivityCompat.OnRequestPermissionsResultCallback {
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private MediaPlayer mPlayer;
    private String videoPath;
    private TextView wordTextview;
    private Toolbar toolbar;
    private Word word;
    private int turn = 0;
    private int speed = 5;
    private SeekBar speed_videoseekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonetic);
        initializeToolBarAndAppDrawer();
        wordTextview = (TextView) findViewById(R.id.wordTextview);

        mSurfaceView = (SurfaceView) findViewById(R.id.phoneticSurfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        speed_videoseekbar = (SeekBar) findViewById(R.id.speed_videoSeekBar);

        speed_videoseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                speed = progresValue;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        Intent intent = getIntent();
        word = Word.getWord(intent.getIntExtra("Word", 0));
        wordTextview.setText(word.getSe());
        videoPath = word.getVideoPath();
        UsageLogger.appendActivity(this, "selected word id, " + word.getId());
        if (videoPath != null) {
            int soundwaveId = getResources().getIdentifier(videoPath, "raw", getPackageName());
            mPlayer = MediaPlayer.create(PhoneticActivity.this, soundwaveId);
        } else
            Toast.makeText(this, "ليس هناك فيديو مرفق بهذه الجملة", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mPlayer != null) {
            // Fix aspect ratio
            int surfaceView_Width = mSurfaceView.getWidth();
            int surfaceView_Height = mSurfaceView.getHeight();
            float video_Width = mPlayer.getVideoWidth();
            float video_Height = mPlayer.getVideoHeight();
            float ratio_width = surfaceView_Width/video_Width;
            float ratio_height = surfaceView_Height/video_Height;
            float aspectratio = video_Width/video_Height;
            ViewGroup.LayoutParams layoutParams = mSurfaceView.getLayoutParams();
            if (ratio_width > ratio_height){
                layoutParams.width = (int) (surfaceView_Height * aspectratio);
                layoutParams.height = surfaceView_Height;
            } else {
                layoutParams.width = surfaceView_Width;
                layoutParams.height = (int) (surfaceView_Width / aspectratio);
            }
            mSurfaceView.setLayoutParams(layoutParams);
            mPlayer.setDisplay(holder);
            play(null);
        }
    }

    public void play(@Nullable View v) {
        MediaHelper mHelper = new MediaHelper();
        if (mHelper.playAtSpeed(mPlayer, speed - 2) == -1) {
            speed_videoseekbar.setVisibility(View.INVISIBLE);
        }
        UsageLogger.appendActivity(this, "played word id, " + word.getId());
    }

    public void onClick(View v) {
        UsageLogger.appendActivity(this, "changed word language, " + word.getId());
        if (turn % 2 == 0)
            wordTextview.setText(word.getAr());
        else
            wordTextview.setText(word.getSe());
        turn++;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mPlayer.release();
        speed_videoseekbar.setProgress(0);
    }

    public void createToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("كلمة مع حرف صوتي");
        setSupportActionBar(toolbar);
    }

 }
