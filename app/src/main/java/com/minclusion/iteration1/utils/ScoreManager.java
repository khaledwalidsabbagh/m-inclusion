package com.minclusion.iteration1.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.minclusion.iteration1.R;

/**
 * Created by khaled1 on 2018-02-13.
 */

public class ScoreManager {
    private Context context;

    public ScoreManager(Context context)
    {
        this.context = context;
    }

    public void saveScore(int score) {
        SharedPreferences sharedPref = context.getSharedPreferences("userScore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String storedScore = sharedPref.getString("score", "0");
        if (score >= Integer.parseInt(storedScore)) {
            editor.putString("score", Integer.toString(score));
        }
        editor.apply();
    }

    public String updateScore() {
//        scoreTxt.setText(getResources().getString(R.string.currentScore) + " " + Integer.toString(score));
//        saveScore();

        SharedPreferences sharedPref = context.getSharedPreferences("userScore", Context.MODE_PRIVATE);
        String score = sharedPref.getString("score", "0");

        return score;
//        TextView highScore = (TextView) findViewById(R.id.highScoreTxt);
//        highScore.setText(getResources().getString(R.string.highestScore) + score);
    }

}
