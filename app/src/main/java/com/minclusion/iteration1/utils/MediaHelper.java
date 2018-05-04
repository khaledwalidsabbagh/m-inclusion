package com.minclusion.iteration1.utils;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.PlaybackParams;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public final class MediaHelper {
    public MediaRecorder mRecorder;
    public MediaPlayer mPlayer;
    private String recordingPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecorder";


    public void startRecording(int tagId) {
        recordingPath = recordingPath + Integer.toString(tagId) + ".3gpp";
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
        File rFile = new File(recordingPath);
        if (rFile.exists()) {
            rFile.delete(); //if recording exists then delete
        }
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB); // the audio encoder. How will the audio be encoded
        mRecorder.setOutputFile(recordingPath);
        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("msg: ", e.toString());
        }

    }


    public void replayRecording(MediaPlayer next) {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(recordingPath);
            mPlayer.prepare();
            mPlayer.setNextMediaPlayer(next);
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replayRecording(int tagId) {
        mPlayer = new MediaPlayer();
        recordingPath = recordingPath + Integer.toString(tagId) + ".3gpp";
        try {
            mPlayer.setDataSource(recordingPath);
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int playAtSpeed(MediaPlayer mPlayer, int speed) {
        if (mPlayer != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mPlayer.setPlaybackParams(new PlaybackParams().setSpeed((speed * 0.1f) + 0.7f));
                mPlayer.start();
                return 1;
            } else {
                mPlayer.start();
                return -1; // APK version doesnt support setSpeed
            }
        } else
            return 0;
    }

    public void stopRecording() {

        if (mRecorder != null) {

            try {
                mRecorder.stop();
                mRecorder.release();
                mRecorder = null;
                //    recordingPath = null;

            } catch (Exception e) {
                Log.e("msg", e.toString());
            }
        }
    }

}
