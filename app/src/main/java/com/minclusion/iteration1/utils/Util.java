package com.minclusion.iteration1.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Layout;
import android.text.SpannableString;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.minclusion.iteration1.Intro;
import com.minclusion.iteration1.CommonDialogues.controller.game.ExerciseActivity;
import com.minclusion.iteration1.R;
import com.minclusion.iteration1.CommonDialogues.controller.LettersActivity;
import com.minclusion.iteration1.cookandlearn.controller.DishActivity;

/**
 * Created by Lisanu Tebikew on 1/21/18.
 */

public class Util {

    public static final Integer NON_EXISTING_ID = -12098;

    /*
     * handleNavigation: handles navigations within the app drawer
     * @param activity: the calling activity displaing the app drawer
     * @param mDrawerLayout: the drawer layout
     * @param id: the selected item from the drawer menu
     * @ returns boolean: true if the navigation is handled properly
     */
    public static boolean handleNavigation(Activity activity, DrawerLayout mDrawerLayout , Integer id) {
        switch (id) {

            case R.id.nav_vowels:
                Intent vIntent = new Intent(activity, LettersActivity.class);
                vIntent.putExtra("type", "vowel");
                activity.startActivity(vIntent);
                UsageLogger.appendActivity(activity, "menu open activity, vowel grouping");
                break;

            case R.id.nav_consonants:
                Intent cIntent = new Intent(activity, LettersActivity.class);
                cIntent.putExtra("type", "consonant");
                activity.startActivity(cIntent);
                UsageLogger.appendActivity(activity, "menu open activity, vowel grouping");
                break;

            case R.id.nav_multiplechoice:
                Intent mcIntent = new Intent(activity, ExerciseActivity.class);
                activity.startActivity(mcIntent);
                UsageLogger.appendActivity(activity, "menu open activity, quiz");
                break;
            case R.id.nav_sendreport:
                UsageLogger.appendActivity(activity, "menu open activity, rend report");
                try {
                    activity.startActivity(UsageLogger.emailReport(activity));
                    activity.finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
                ;
                break;

            case R.id.nav_intro:
                Intent introIntent = new Intent(activity, Intro.class);
                activity.startActivity(introIntent);
                break;
            case R.id.nav_dialogue:
                UsageLogger.appendActivity(activity, "menu open activity, dialogues");
                break;
//            case R.id.nav_disable_tutorial:
//                // reset the settings so that the user can see the tutorial again
//                // TODO this MUST be configured as an appliaiton setting
//                MaterialShowcaseView.resetAll(activity);
//                break;
//
            case R.id.nav_cook_and_learn:
                Intent cookingIntent = new Intent(activity, DishActivity.class);
                activity.startActivity(cookingIntent);
                UsageLogger.appendActivity(activity, "menu open activity, cook and learn");
                break;

            default:
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public static String getResourceName(String swedishName){
        if(swedishName == null || swedishName.equals(""))
            return "";
        String name = swedishName.toLowerCase();
        name = name.replace("ä", "a");
        name = name.replace("å", "a");
        name = name.replace("ö", "o");
        name = name.replace(" ", "_");

        return name;
    }
}
