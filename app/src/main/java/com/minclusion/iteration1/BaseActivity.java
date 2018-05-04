package com.minclusion.iteration1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.minclusion.iteration1.CommonDialogues.controller.dialogue.CategoryDialogueActivity;
import com.minclusion.iteration1.CommonDialogues.controller.game.ExerciseActivity;
import com.minclusion.iteration1.CommonDialogues.controller.LettersActivity;

import com.minclusion.iteration1.cookandlearn.controller.DishActivity;
import com.minclusion.iteration1.utils.UsageLogger;


/**
 * Created by Lisanu Tebikew Yallew on 1/22/18.
 * BaseActivity is the grand-parent of all activities in this application
 * It includes functionalities for App drawers and toolbars
 */

public class BaseActivity
        extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected Toolbar toolbar;
    public AppBarLayout appBarLayout;
    // app drawer
    public DrawerLayout mDrawerLayout;
    public ActionBarDrawerToggle mDrawerToggle;
    public DrawerLayout drawer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * initializes the applications app drawer and toolbar
     */
    protected void initializeToolBarAndAppDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.setExpanded(false);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        drawer = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {

            case R.id.home:
                finish(); // close this activity and return to preview activity (if there is any)

            case R.id.nav_vowels:
                Intent vIntent = new Intent(BaseActivity.this, LettersActivity.class);
                vIntent.putExtra("type", "vowel");
                startActivity(vIntent);
                UsageLogger.appendActivity(BaseActivity.this, "menu open BaseActivity.this, vowel grouping");
                break;

            case R.id.nav_consonants:
                Intent cIntent = new Intent(this, LettersActivity.class);
                cIntent.putExtra("type", "consonant");
                startActivity(cIntent);
                UsageLogger.appendActivity(this, "menu open activity, vowel grouping");
                break;

            case R.id.nav_multiplechoice:
                Intent mcIntent = new Intent(this, ExerciseActivity.class);
                mcIntent.putExtra("exercisetype", "vowel");
                startActivity(mcIntent);
                UsageLogger.appendActivity(this, "menu open activity, quiz");
                break;

            case R.id.nav_cmultiplechoice:
                Intent cmcIntent = new Intent(this, ExerciseActivity.class);
                cmcIntent.putExtra("exercisetype", "consonants");
                startActivity(cmcIntent);
                UsageLogger.appendActivity(this, "menu open activity, consonant quiz");
                break;


//            case R.id.nav_fillintheblank:
//                Intent fIntent = new Intent(this, FillInTheBlankExercise.class);
//                fIntent.putExtra("exercisetype", "fillintheblank");
//                startActivity(fIntent);
//                UsageLogger.appendActivity(this, "menu open activity, consonant quiz");
//                break;

            case R.id.nav_sendreport:
                UsageLogger.appendActivity(BaseActivity.this, "menu open BaseActivity.this, rend report");
                try {
                    startActivity(UsageLogger.emailReport(BaseActivity.this));
                    BaseActivity.this.finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(BaseActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
                ;
                break;

            case R.id.nav_intro:
                Intent introIntent = new Intent(BaseActivity.this, Intro.class);
                startActivity(introIntent);
                break;
            case R.id.nav_dialogue:
                Intent dIntent = new Intent(this, CategoryDialogueActivity.class);
                startActivity(dIntent);
                UsageLogger.appendActivity(this, "menu open activity, consonant quiz");
                break;

//            case R.id.nav_disable_tutorial:
//                // reset the settings so that the user can see the tutorial again
//                // TODO this MUST be configured as an appliaiton setting
//                MaterialShowcaseView.resetAll(BaseActivity.this);
//                break;
//
            case R.id.nav_cook_and_learn:
                Intent cookingIntent = new Intent(BaseActivity.this, DishActivity.class);
                startActivity(cookingIntent);
                UsageLogger.appendActivity(BaseActivity.this, "menu open BaseActivity.this, cook and learn");
                break;

            default:
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
