package com.minclusion.iteration1.CommonDialogues.controller.dialogue;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.minclusion.iteration1.BaseActivity;
import com.minclusion.iteration1.R;

import java.util.List;

import adapters.StatementListAdapter;

import com.minclusion.iteration1.utils.MediaHelper;

import db.Statement;

import com.minclusion.iteration1.utils.UsageLogger;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class DailyDialogueActivity extends BaseActivity {
    private List<Statement> sentences;
    private int index, statementCounter = 0;
    public GridView gridViewDialogues;
    private MediaPlayer mPlayer;
    private ToggleButton start;
    private MediaHelper mHelper;
    private String[] dialogueTitles;

    //properties promoted to class level to add some walkthough tutorial steps
    private Button next, previous;
    private SeekBar speed_seekbar;
    private int[] dialogueIds, currentId;
    private String toolbarTitle, videoUri;
    private BottomBar mBottomBar;
    private Intent moreOnDialogueIntent;
    private boolean isPracticeClicked = false;
    private String[] videoUris;
    private Integer menuTabSwitcher = 1;
    private static Integer PROGRESS = 3;
    private static final String SHOWCASE_ID = "minclusion walkthrough";

    public ToggleButton getStart() {
        return start;
    }

    public void setStart(ToggleButton start) {
        this.start = start;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        dialogueTitles = intent.getStringArrayExtra("DialogueTitles");
        index = (int) intent.getSerializableExtra("Index");
        dialogueIds = intent.getIntArrayExtra("DialogueIds");
        videoUris = intent.getStringArrayExtra("videoUris");
        currentId = new int[]{(int) intent.getSerializableExtra("Index")};
        int dialogueId = dialogueIds[currentId[0]];

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_dialogue);

        gridViewDialogues = findViewById(R.id.gridViewDailyDialog);
        toolbarTitle = dialogueTitles[index];
        videoUri = videoUris[index];
        mBottomBar = BottomBar.attach(this, savedInstanceState);
        setBottomBar();

        initializeToolBarAndAppDrawer();

        next = findViewById(R.id.button_next);
        previous = findViewById(R.id.button_previous);
        speed_seekbar = findViewById(R.id.speed_seekBar1);
        start = findViewById(R.id.button_start);
        mHelper = new MediaHelper();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            speed_seekbar.setVisibility(View.INVISIBLE);
        } else {
            speed_seekbar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY));
            speed_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                    PROGRESS = progresValue;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
        }

        sentences = Statement.getStatementsInDialogue(dialogueId);
        if (sentences == null) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
            alertbox.setMessage("لا يوجد المزيد من المحادثات");
        } else {
            gridViewDialogues.setAdapter(new StatementListAdapter(this, sentences, DailyDialogueActivity.this, false));
            registerForContextMenu(gridViewDialogues);
        }
        previous.setVisibility(View.INVISIBLE);

        start.setOnClickListener(startButtonClickHandler);
        next.setOnClickListener(nextButtonClickListener);
        previous.setOnClickListener(previousButtonClickListener);

        // show a walkthrough tutorial
        // showWalkthrough();
    }

    /***
     * Event Listener method for the previous button
     */
    View.OnClickListener previousButtonClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            resetView();
            mBottomBar = new BottomBar(DailyDialogueActivity.this);
            // setBottomBar();
            if (currentId[0] >= 1) {
                currentId[0] = currentId[0] - 1;
                index--;
                next.setVisibility(View.VISIBLE);
                toolbarTitle = dialogueTitles[index];
                videoUri = videoUris[index];
                populateStatements();
            }
            if (currentId[0] == 0) {
                previous.setVisibility(View.INVISIBLE);
            }
        }
    };

    /***
     * Event Listener method for the next button
     */
    View.OnClickListener nextButtonClickListener = new View.OnClickListener() {

        public void onClick(View v) {
            resetView();
            mBottomBar = new BottomBar(DailyDialogueActivity.this);

            //  setBottomBar();
            if (currentId[0] + 1 < dialogueIds.length) {
                previous.setVisibility(View.VISIBLE);
                currentId[0] = currentId[0] + 1;
                index++;
                toolbarTitle = dialogueTitles[index];
                videoUri = videoUris[index];
                populateStatements();
            }
            if (currentId[0] + 1 == dialogueIds.length) {
                next.setVisibility(View.INVISIBLE);
            }
        }
    };
    /***
     * Event Listener method for the start button
     */
    View.OnClickListener startButtonClickHandler = new View.OnClickListener() {
        public void onClick(View v) {
            UsageLogger.appendActivity(v.getContext(), "playing all statements in dialogue id, " + dialogueIds[currentId[0]]);
            boolean playChecked = ((ToggleButton) v).isChecked();
            if (playChecked) {
                playNext();

            } else if (!playChecked) { //pause is pressed
                if (mPlayer.isPlaying())
                    mPlayer.pause();
                else
                    mPlayer.release();
            }
        }
    };

    public void resetView() {
        gridViewDialogues = findViewById(R.id.gridViewDailyDialog);
        if (mPlayer != null) {
            mPlayer.release();
        }
        statementCounter = 0; //reseting the playlist index to the beginning
        start.setChecked(false);
    }

    public void populateStatements() {
        initializeToolBarAndAppDrawer();
        setBottomBar();
        sentences = Statement.getStatementsInDialogue(dialogueIds[currentId[0]]);
        toolbarTitle = dialogueTitles[index];
        gridViewDialogues.setAdapter(new StatementListAdapter(DailyDialogueActivity.this, sentences,
                DailyDialogueActivity.this, false));
    }

    private void setBottomBar() {
        mBottomBar.setItemsFromMenu(R.menu.menu_bottom_bar, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(int menuItemId) {

                if (menuItemId == R.id.bottomBarMore) {
                    moreOnDialogueIntent = new Intent(DailyDialogueActivity.this, MoreOnDialogueActivity.class);
                    moreOnDialogueIntent.putExtra("DialogueTitle", toolbarTitle);
                    moreOnDialogueIntent.putExtra("videoUri", videoUri);
                    Log.e("msg", "More tab pressed");
                    DailyDialogueActivity.this.startActivity(moreOnDialogueIntent);

                } else if (menuItemId == R.id.bottomBarPractice)

                {
                    isPracticeClicked = true;
                    gridViewDialogues.setAdapter(new StatementListAdapter(DailyDialogueActivity.this, sentences,
                            DailyDialogueActivity.this, isPracticeClicked));

                    //iterate through all statements and hide highlighted words
                } else if (menuItemId == R.id.bottomBarBack)
                {

                }
            }
            @Override
            public void onMenuTabReSelected(int menuItemId) {

                Log.e("msg ", "menu tab reselected");
                if (menuItemId == R.id.bottomBarMore) {
                    moreOnDialogueIntent = new Intent(DailyDialogueActivity.this, MoreOnDialogueActivity.class);
                    moreOnDialogueIntent.putExtra("DialogueTitle", toolbarTitle);
                    moreOnDialogueIntent.putExtra("videoUri", videoUri);
                    DailyDialogueActivity.this.startActivity(moreOnDialogueIntent);
                } else if (menuItemId == R.id.bottomBarPractice) {
                    if (menuTabSwitcher % 2 == 0) {
                        isPracticeClicked = true;
                    } else {
                        isPracticeClicked = false;
                    }
                    menuTabSwitcher++;
                    gridViewDialogues.setAdapter(new StatementListAdapter(DailyDialogueActivity.this, sentences,
                            DailyDialogueActivity.this, isPracticeClicked));

                    //iterate through all statements and hide highlighted words

                }
            }
        });

    }

    @Override
    public void initializeToolBarAndAppDrawer() {

        toolbarTitle = dialogueTitles[index];
        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(toolbarTitle);
        appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.setExpanded(false);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#28b2b6")));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        drawer = findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void playNext() {

        if (statementCounter < sentences.size()) {
            final Statement stmt = sentences.get(statementCounter);
            mPlayer = MediaPlayer.create(DailyDialogueActivity.this, getResources().getIdentifier(stmt.getSoundPath(), "raw", getPackageName()));
            MediaHelper.playAtSpeed(mPlayer, getProgress());
            if (statementCounter < sentences.size()) {
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                        statementCounter = statementCounter + 1;
                        playNext();
                    }
                });
            }
        } else {
            statementCounter = 0;
            mPlayer.release();
            start.setChecked(false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else
                    Toast.makeText(this, "طلب الإذن مرفوض!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mPlayer != null) {
            mPlayer.release();
        }
    }

    public int getProgress() {
        return PROGRESS;
    }

    /***
     * showTutorial: Gives the user a nice walkthrough in the application
     */
    private void showWalkthrough() {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);

        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
            }
        });

        sequence.setConfig(config);

        //sequence.addSequenceItem(mButtonOne, "This is button one", "GOT IT");

        sequence.addSequenceItem(
                new MaterialShowcaseView.Builder(this)
                        .setTarget(start)
                        .setDismissText("متابعة")
                        .setContentText("لتشغيل المحادثة الحالية")
                        .withCircleShape()
                        .build()
        );

        // add another step of the tutorial for the previous button, if the button is visible
        if (next != null & next.getVisibility() != View.INVISIBLE) {
            sequence.addSequenceItem(
                    new MaterialShowcaseView.Builder(this)
                            .setTarget(next)
                            .setDismissText("مفهوم")
                            .setContentText("للذهاب إلى المحادثة التالية")
                            .withCircleShape()
                            .build()
            );
        }

        // add another step of the tutorial for the previous button, if the button is visible
        if (previous != null & previous.getVisibility() != View.INVISIBLE) {
            sequence.addSequenceItem(
                    new MaterialShowcaseView.Builder(this)
                            .setTarget(previous)
                            .setDismissText("مفهوم")
                            .setContentText("للذهاب إلى المحادثة السابقة")
                            .withCircleShape()
                            .build()
            );
        }

        // add another step of the tutorial for the previous button
        // add another step of the tutorial for the previous button, if the button is visible
        if (speed_seekbar != null & speed_seekbar.getVisibility() != View.INVISIBLE) {
            sequence.addSequenceItem(
                    new MaterialShowcaseView.Builder(this)
                            .setTarget(speed_seekbar)
                            .setDismissText("مفهوم")
                            .setContentText("حرك للتحكم بسرعة الصوت")
                            .withRectangleShape()
                            .build()
            );
        }

        // reset the settings so that the user can see the tutorial again
        // TODO this MUST be configured as an appliaiton setting
        //MaterialShowcaseView.resetSingleUse(this, SHOWCASE_ID);

        //start the guide
        sequence.start();
    }
}
