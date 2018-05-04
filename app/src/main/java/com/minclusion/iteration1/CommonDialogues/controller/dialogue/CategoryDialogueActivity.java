package com.minclusion.iteration1.CommonDialogues.controller.dialogue;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.minclusion.iteration1.BaseActivity;
import com.minclusion.iteration1.CommonDialogues.controller.LettersActivity;
import com.minclusion.iteration1.CommonDialogues.controller.game.ExerciseActivity;
import com.minclusion.iteration1.CommonDialogues.controller.game.FillInTheBlankActivity;
import com.minclusion.iteration1.Intro;
import com.minclusion.iteration1.R;

import java.lang.reflect.Field;
import java.util.List;
import db.Category;

import com.minclusion.iteration1.cookandlearn.controller.DishActivity;
import com.minclusion.iteration1.utils.UsageLogger;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;
import uk.co.deanwild.materialshowcaseview.shape.RectangleShape;
import uk.co.deanwild.materialshowcaseview.target.Target;

public class CategoryDialogueActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private List<Category> categories;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    DrawerLayout drawer;

    private View navButton;

    NavigationView navigationView;

    //a unique id for application walkthrough
    private static final String SHOWCASE_ID = "minclusion walkthrough category";
    private static final String SHOWCASE_ID2 = "minclusion walkthrough category2";
    private static final String SHOWCASE_MENU_ID = "MENU_SHOW_CASE";
    private static final String SHOWCASE_ID3 = "MENU_SHOW_CASE_3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_dialogue);

        drawer = findViewById(R.id.drawer_layout);
        tabLayout = findViewById(R.id.tabs);
        // Set up the ViewPager with the sections adapter.
        mViewPager =  findViewById(R.id.viewpager);

        initToolbar();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initDrawer();

        //query the categorites from datastore

        categories = Category.getAll();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                // optional
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                // optional
                @Override
                public void onPageSelected(int position) {
                    Toast.makeText(getApplicationContext(), "Tab # Selected ", Toast.LENGTH_SHORT).show();
                    //its here that i need to show the tutorial

                    ShowcaseConfig config = new ShowcaseConfig();
                    config.setDelay(500); // half second between each showcase view


                    MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(CategoryDialogueActivity.this, SHOWCASE_ID);

                    sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
                        @Override
                        public void onShow(MaterialShowcaseView itemView, int position) {
                            Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
                        }
                    });

                    sequence.setConfig(config);

                    // show a walkthrough for the first element displayed
                    MaterialShowcaseView.Builder builder = new MaterialShowcaseView.Builder(CategoryDialogueActivity.this)
                            .setTarget(mViewPager)
                            .singleUse(SHOWCASE_ID)
                            .setDismissText("OK")
                            .setContentText("Select a dialog from these")
                            .setTargetTouchable(true)
                            .setDismissOnTargetTouch(true)
                            .withRectangleShape();

                    //se the highlight as rectanle
                    //builder.setShape(new RectangleShape(width, 20) );

                    // add a walkthrough step
                    sequence.addSequenceItem(
                                builder.build()
                    );

                    // reset the settings so that the user can see the tutorial again
                    // TODO this MUST be configured as an appliaiton setting
                    MaterialShowcaseView.resetSingleUse(getApplicationContext(), SHOWCASE_ID);

                    //start the guide
                    sequence.start();
                }

                // optional
                @Override
                public void onPageScrollStateChanged(int state) {
                }
            }
        );
        */
    }

    /**
     * Gets an ImageButton with DrawerArrowDrawable as the drawable's superclass in the Toolbar
     * ( which should be the Navigation Hamburger Button )
     *
     * @param toolbar the application's toolbar
     * @return The Navigation Drawer toggle button (View)
     */
    private ImageButton getNavButtonView(Toolbar toolbar) {
        try {
            Class<?> toolbarClass = Toolbar.class;
            //get a reference to the mNavButtonView object
            Field navButtonField = toolbarClass.getDeclaredField("mNavButtonView");
            navButtonField.setAccessible(true);
            // get the object
            ImageButton navButtonView = (ImageButton) navButtonField.get(toolbar);
            //return the drawer toggle button
            return navButtonView;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // if there was an exception trying to acccess the navigation drawer toggle button
        // return null
        return null;
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Minclusion");
        setSupportActionBar(toolbar);
    }

    public void initDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mDrawerToggle.syncState();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerToggle.syncState();
/*
                //on drawer opened show a tutorial about the available options
                navigationView.post(new Runnable() {
                    @Override
                    public void run() {
                        //Configure the tutotiral to be displayed in 0.1 seconds duration
                        ShowcaseConfig config = new ShowcaseConfig();
                        config.setDelay(100); // half second between each showcase view
                        config.setShape(new RectangleShape(100, 100));


                        MaterialShowcaseSequence sequence =
                                new MaterialShowcaseSequence(CategoryDialogueActivity.this, SHOWCASE_ID3);
                        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
                            @Override
                            public void onShow(MaterialShowcaseView itemView, int position) {
                                Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
                            }
                        });
                        sequence.setConfig(config);


                        //extract the drawer menu
                        Menu navMenu = navigationView.getMenu();

                        // add a walkthrough to the first menu item
                        final View vowelsView = navMenu.findItem(R.id.nav_vowels).getActionView();
                        sequence.addSequenceItem(addWalkThroughToMenuItem(vowelsView, "This is your menu. You can select one item from it"));

//                        //add a walkthrough to the second menu iem
//                        final View multipleChoiceMenuItem = navMenu.findItem(R.id.nav_multiplechoice).getActionView();
//                        sequence.addSequenceItem(addWalkThroughToMenuItem(multipleChoiceMenuItem, "Click here to Play multiplechoice games"));

                        //add a walkthrough to the third menu iem
//                        final View dialogueMenuItem = navMenu.findItem(R.id.nav_dialogue).getActionView();
//                        sequence.addSequenceItem(addWalkThroughToMenuItem(dialogueMenuItem, "Click here to practice dialogues"));

//
//                        //add a walkthrough to the fourth menu iem
//                        final View aboutTheAppMenuItem = navMenu.findItem(R.id.nav_intro).getActionView();
//                        sequence.addSequenceItem(addWalkThroughToMenuItem(aboutTheAppMenuItem, "Click here to see information about the application"));
//
//                        //add a walkthrough to the fifth menu iem
//                        final View sendReportMenuItem = navMenu.findItem(R.id.nav_sendreport).getActionView();
//                        sequence.addSequenceItem(addWalkThroughToMenuItem(sendReportMenuItem, "Click here to send information about the application"));
//
//                        //add a walkthrough to the sixth menu iem
//                        final View cookAndLearnMenuItem = navMenu.findItem(R.id.nav_cook_and_learn).getActionView();
//                        sequence.addSequenceItem(addWalkThroughToMenuItem(cookAndLearnMenuItem, "Click here to learn swedish cooking"));
//
//                        //add a walkthrough to the sixth menu iem
//                        final View disableTutorial = navMenu.findItem(R.id.nav_disable_tutorial).getActionView();
//                        sequence.addSequenceItem(addWalkThroughToMenuItem(disableTutorial, "Click here to disable the tutorial"));


                        // TODO this MUST be configured as an appliaiton setting
                        //MaterialShowcaseView.resetSingleUse(getApplicationContext(), SHOWCASE_ID3);


                        //start the guide
                        sequence.start();



                    }

                });
*/
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawer.setDrawerListener(mDrawerToggle);
    }


    private MaterialShowcaseView addWalkThroughToMenuItem(final View v, String content) {

        // if the menu item is found add a walkthrough for it
        if (v != null) {
            //create a target for the menu
            Target vowelsMenuItemLoc = new Target() {
                @Override
                public Rect getBounds() {
                    Point p = getPoint();
                    return new Rect(
                            p.x - navigationView.getWidth() / 2,
                            p.y - v.getHeight() / 2 + 100,
                            p.x + navigationView.getWidth() / 2,
                            p.y + v.getHeight() / 2 + 100);
                }

                @Override
                public Point getPoint() {

                    int[] location = new int[2];
                    v.getLocationOnScreen(location);
                    return new Point(location[0] / 2, location[1]);
                }
            };

            // add a walk through tutorial for the tabs (emergency and everyday languages)
            // show a walkthrough for the first element displayed
            MaterialShowcaseView.Builder builder =
                    new MaterialShowcaseView.Builder(CategoryDialogueActivity.this)
                            .setTarget(v)
                            .setContentText(content)
                            .setTargetTouchable(false)
                            .withRectangleShape()
                            .setDismissText("OK");

            MaterialShowcaseView showCase = builder.build();
            showCase.setTarget(vowelsMenuItemLoc);

            return showCase;
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
        showInitialWalkThrough();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    //TODO use the BaseActivity's method instead
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_vowels:
                Intent vIntent = new Intent(this, LettersActivity.class);
                vIntent.putExtra("type", "vowel");
                startActivity(vIntent);
                UsageLogger.appendActivity(this, "menu open activity, vowel grouping");
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

            case R.id.nav_fillintheblank:
                Intent fIntent = new Intent(this, FillInTheBlankActivity.class);
                fIntent.putExtra("exercisetype", "fillintheblank");
                startActivity(fIntent);
                UsageLogger.appendActivity(this, "menu open activity, consonant quiz");
                break;

            case R.id.nav_sendreport:
                UsageLogger.appendActivity(this, "menu open activity, rend report");
                try {
                    startActivity(UsageLogger.emailReport(this));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                }
                ;
                break;

            case R.id.nav_intro:
                Intent introIntent = new Intent(this, Intro.class);
                startActivity(introIntent);
                break;
            case R.id.nav_dialogue:
                UsageLogger.appendActivity(this, "menu open activity, dialogues");
                break;
//            case R.id.nav_disable_tutorial:
//                // reset the settings so that the user can see the tutorial again
//                // TODO this MUST be configured as an appliaiton setting
//                MaterialShowcaseView.resetAll(this);
//                break;
//
            case R.id.nav_cook_and_learn:
                Intent cookingIntent = new Intent(this, DishActivity.class);
                startActivity(cookingIntent);
                UsageLogger.appendActivity(this, "menu open activity, cook and learn");
                break;

            default:
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("categoryId", categories.get(position).getId());
            CategoryFragment categoryFragment = new CategoryFragment();
            categoryFragment.setArguments(bundle);
            return categoryFragment;
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categories.get(position).getAr();
        }
    }

//    public void onResume() {
//        super.onResume();
//
//
//        ShowcaseConfig config = new ShowcaseConfig();
//        config.setDelay(500); // half second between each showcase view
//
//        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(CategoryDialogueActivity.this, SHOWCASE_MENU_ID);
//
//        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
//            @Override
//            public void onShow(MaterialShowcaseView itemView, int position) {
//                Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        sequence.setConfig(config);
//
//        //get the fragments defined in the ViewPager
//        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//        for(Fragment f:fragments){
//
//            // add a walk through tutorial for the burger
//            if (f != null) {
//                sequence.addSequenceItem(f.getView(),
//                        "Tutorial for tab",
//                        "Tutorial for tab",
//                        "GOT IT");
//            }
//
////            final Target viewTarget = new ViewTarget(fragments.get(0).getId(),
////                    CategoryDialogueActivity.this);
////            getActivity().findViewById(R.id.homescr_levelbrain).post(new Runnable() {
////                @Override
////                public void run() {
////                    new ShowcaseView.Builder(getActivity(), false)
////                            .setTarget(viewTarget)
////                            .setContentTitle("Doing vowel increases level")
////                            .hideOnTouchOutside()
////                            .setContentText("Levelling unlocks new things to play with")
////                            .build();
////                }
////            });
//        }
//
//        // reset the settings so that the user can see the tutorial again
//        // TODO this MUST be configured as an appliaiton setting
//        MaterialShowcaseView.resetSingleUse(this, SHOWCASE_ID);
//
//        //start the guide
//        sequence.start();
//
//
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
//        return true;
//    }

    /***
     * showTutorial: Gives the user a nice walkthrough in the application
     */
//    @Override
//    public void onResume() {
//        super.onResume();
    private void showInitialWalkThrough() {


        findViewById(tabLayout.getId()).post(new Runnable() {
            @Override
            public void run() {
                //its here that i need to show the tutorial
                ShowcaseConfig config = new ShowcaseConfig();
                config.setDelay(100); // half second between each showcase view
                config.setShape(new RectangleShape(0, 0));


                MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(CategoryDialogueActivity.this, SHOWCASE_ID2);
                sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
                    @Override
                    public void onShow(MaterialShowcaseView itemView, int position) {
                        //Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
                    }
                });
                sequence.setConfig(config);

                // add a tutorial for burger icon now
                navButton = getNavButtonView(toolbar);
                // add a walk through tutorial for the tabs (emergency and everyday languages)
                // show a walkthrough for the first element displayed
                MaterialShowcaseView.Builder buttonTutorialBuilder =
                        new MaterialShowcaseView.Builder(CategoryDialogueActivity.this)
                                .setTarget(navButton)
                                .setContentText(R.string.openDrawer)
                                .setDismissOnTouch(false)
                                .setTargetTouchable(true)
                                .withRectangleShape();
                if (navButton != null) {
                    sequence.addSequenceItem(buttonTutorialBuilder.build());
                }

                //start the guide
                sequence.start();

            }

        });

    }

    private MaterialShowcaseView create(Activity activity, View view, int content,
                                        String id, Integer width, Integer height) {
        MaterialShowcaseView.Builder builder = new MaterialShowcaseView.Builder(this)
                .setTarget(view)
                .setDismissText("GOT IT")
                //.setDismissTextColor(Tools.getThemeColor(activity, R.attr.colorPrimary))
                .setMaskColour(Color.argb(150, 0, 0, 0))
                .setContentText(content)
                .setDismissOnTouch(true)
                .setDelay(0); // optional but starting animations immediately in onCreate can make them choppy

        if (width != null) {
            builder.setShape(new RectangleShape(width, height));
        }

        if (id != null)
            builder.singleUse(id); // provide a unique ID used to ensure it is only shown once

        MaterialShowcaseView showcaseView = builder.build();
        return showcaseView;
    }

}

