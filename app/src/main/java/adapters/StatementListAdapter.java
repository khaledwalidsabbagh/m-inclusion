package adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.minclusion.iteration1.CommonDialogues.controller.dialogue.DailyDialogueActivity;
import com.minclusion.iteration1.CommonDialogues.controller.PhoneticActivity;
import com.minclusion.iteration1.R;

import java.util.List;

import db.Statement;
import db.Word;

import com.minclusion.iteration1.utils.MediaHelper;

import entities.Permission;

import com.minclusion.iteration1.utils.UsageLogger;

public class StatementListAdapter extends ArrayAdapter<Statement> {
    private Context context;
    private List<Statement> statements;
    private MediaHelper mHelper;
    private Permission permission;
    private DailyDialogueActivity dailyDialogueActivity;
    private MediaPlayer mPlayer;
    private ToggleButton soundwavePlay, soundwaveRecord, recordPractice;
    private TextView textViewStatement;
    private SeekBar mLength;
    private ImageView soundwaveReplay, replayPractice;
    private int mProgress;
    private boolean isGranted, isPractice;
    private Statement globalStatement;

    public StatementListAdapter(Context context, List<Statement> statements, DailyDialogueActivity dailyDialogueActivity, boolean isPractice) {
        super(context, R.layout.daily_dialogue_list, statements);
        this.context = context;
        this.statements = statements;
        this.dailyDialogueActivity = dailyDialogueActivity;
        this.isPractice = isPractice;
    }

    private void recordStatement(View v, Statement statement) {

        permission = new Permission(context);
        isGranted = permission.grantWriteAudioPermission();

        boolean recordingChecked;
        recordingChecked = ((ToggleButton) v).isChecked();
        if (isGranted) {
            if (recordingChecked) {
                mHelper.startRecording(statement.getId());

            } else {
                mHelper.stopRecording();
                soundwaveReplay.setVisibility(View.VISIBLE);
            }

        } else {
            Toast.makeText(context, "تم رفض منح إحدى الصلاحيات تبعا لرغبة المستخدم", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View gerneralView = layoutInflater.inflate(R.layout.daily_dialogue_list, parent, false);
        final Statement statement = statements.get(position);
        globalStatement = statement;
        textViewStatement = gerneralView.findViewById(R.id.textViewStatement);
        recordPractice = gerneralView.findViewById(R.id.recordPractice);
        textViewStatement.setText(Html.fromHtml(statement.getSe(isPractice)));
        replayPractice = gerneralView.findViewById(R.id.replayPractice);
        mPlayer = MediaPlayer.create(gerneralView.getContext(), gerneralView.getResources().getIdentifier(statement.getSoundPath(),
                "raw", gerneralView.getContext().getPackageName()));

        mHelper = new MediaHelper();

        LinearLayout rowStmt = gerneralView.findViewById(R.id.rowStatement);
        LinearLayout rowOper = gerneralView.findViewById(R.id.rowOperations);
        LinearLayout rowPractice = gerneralView.findViewById(R.id.rowPractice);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) rowPractice.getLayoutParams();


        ImageView listenImageView = gerneralView.findViewById(R.id.playSmt);
        listenImageView.setBackgroundResource(R.drawable.ic_volume_up);

        ToggleButton translate = gerneralView.findViewById(R.id.translateSmt);
        translate.setBackgroundResource(R.drawable.translate2);

        if (isPractice) {
            //if(statement contains a highlighted word) then display button
            if (statement.getSe(false).contains("font"))
                recordPractice.setVisibility(View.VISIBLE);
        }

        if (position % 2 == 0) {
            textViewStatement.setBackgroundResource(R.drawable.bubble_left);
            textViewStatement.measure(0, 0);

            rowStmt.setGravity(Gravity.START);
            rowOper.setGravity(Gravity.START);
             rowPractice.setGravity(Gravity.START);

            int marginLeft = textViewStatement.getMeasuredWidth() ;
            params.setMargins(marginLeft/2, 10, 0 , 0);
            rowPractice.setLayoutParams(params);

        } else {
            textViewStatement.setBackgroundResource(R.drawable.bubble_right);
            textViewStatement.measure(0, 0);

            rowStmt.removeView(textViewStatement);
            rowOper.setGravity(Gravity.END);
            rowStmt.setGravity(Gravity.END);
            rowPractice.setGravity(Gravity.END);
            rowStmt.addView(textViewStatement);
            int marginRight = textViewStatement.getMeasuredWidth();
            params.setMargins(0, 10, marginRight - 375 , 0);
            rowPractice.setLayoutParams(params);


        }


        listenImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer != null && mPlayer.isPlaying()) {
                    mPlayer.stop();
                    mPlayer.release();
                }
                mPlayer = MediaPlayer.create(gerneralView.getContext(), gerneralView.getResources().getIdentifier(statement.getSoundPath(), "raw", gerneralView.getContext().getPackageName()));
                MediaHelper.playAtSpeed(mPlayer, dailyDialogueActivity.getProgress());
            }
        });


        recordPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean recordingChecked;
                recordingChecked = ((ToggleButton) view).isChecked();

                if (recordingChecked) {
                    mHelper.startRecording(statement.getId());

                } else {
                    mHelper.stopRecording();
                    textViewStatement = gerneralView.findViewById(R.id.textViewStatement);
                    textViewStatement.setText(Html.fromHtml(statement.getSe(false)));
                    replayPractice = gerneralView.findViewById(R.id.replayPractice);
                    replayPractice.setVisibility(View.VISIBLE);
                }

            }
        });
        replayPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHelper.mRecorder == null) {  // ensuring that recording is off
                    mHelper.replayRecording(statement.getId()); //replay recorded soundwave
                } else {
                    Toast.makeText(context, "الرجاء أيقاف التسجيل قبل السماع الى ما سجلته", Toast.LENGTH_SHORT).show();
                }
            }
        });
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewStatement = gerneralView.findViewById(R.id.textViewStatement);
                textViewStatement.setText("");
                if (((ToggleButton) v).isChecked()) {
                    UsageLogger.appendActivity(v.getContext(), "translating an individual statement. Id: " + statement.getId());
                    textViewStatement.setText(statement.getAr());
                } else {
                    textViewStatement.setText(Html.fromHtml(statement.getSe(false)));
                }
            }
        });

        final GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            LayoutInflater li = LayoutInflater.from(context);
            View pronounciationView = li.inflate(R.layout.pronounciation_popup, null);
            final LinearLayout feedbackControlLayout = (LinearLayout) pronounciationView.findViewById(R.id.feedbackControls);
            final TextView textViewSelectedStatement = (TextView) pronounciationView.findViewById(R.id.textViewselectedStatement);

            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                ToggleButton s = dailyDialogueActivity.getStart();
                if (s.isChecked()) {
                    s.setChecked(false);
                    dailyDialogueActivity.setStart(s);
                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(pronounciationView);

                textViewSelectedStatement.setText(Html.fromHtml(statement.getSe(false)));
                textViewSelectedStatement.append("\n" + statement.getAr());

                alertDialogBuilder.setPositiveButton(getContext().getResources().getString(R.string.closeWindow),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Do nothing here because we override this button later to change the close behaviour.
                            }
                        });

                alertDialogBuilder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled()) {
                            stopRecording();
                            UsageLogger.appendActivity(getContext(), "dismissed popup statement id, " + statement.getId());
                            dialog.dismiss();
                            ((ViewGroup) pronounciationView.getParent()).removeView(pronounciationView);
                            return true;
                        }
                        return false;
                    }
                });

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                alertDialog.setCanceledOnTouchOutside(false);
                final WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(alertDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                alertDialog.getWindow().setAttributes(lp);

                final Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
                positiveButton.setLayoutParams(positiveButtonLL);

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stopRecording();
                        UsageLogger.appendActivity(v.getContext(), "dismissed popup statement id, " + statement.getId());
                        alertDialog.dismiss();
                        ((ViewGroup) pronounciationView.getParent()).removeView(pronounciationView);
                    }
                });

                mPlayer = MediaPlayer.create(gerneralView.getContext(), gerneralView.getResources().getIdentifier(statement.getSoundPath(), "raw", gerneralView.getContext().getPackageName()));
                soundwavePlay = pronounciationView.findViewById(R.id.soundwavePlay_btn);
                soundwaveRecord = pronounciationView.findViewById(R.id.soundwaveRecord);
                soundwaveReplay = pronounciationView.findViewById(R.id.soundwaveReplay);
                soundwaveReplay.setVisibility(View.INVISIBLE);

                mLength = pronounciationView.findViewById(R.id.recordingLength);
                mLength.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                mLength.getThumb().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);


                textViewSelectedStatement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isPractice = false;
                        if (mHelper.mRecorder == null) {
                            if (Word.getWord(statement.getId()) != null) {
                                Intent intent = new Intent(context, PhoneticActivity.class);
                                intent.putExtra("Word", statement.getId());
                                context.startActivity(intent);
                                UsageLogger.appendActivity(v.getContext(), "opened word for statement id, " + statement.getId());
                            } else {
                                Toast.makeText(context, "لا يوجد ملف فيديو مربوط بهذه الجملة", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "الرجاء أيقاف التسجيل قبل الانتقال الى مشاهدة الفيديو", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                soundwavePlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        switchFeedbackControls(feedbackControlLayout, false, v);
                        UsageLogger.appendActivity(v.getContext(), "clicked play/plause in popup for statement id, " + statement.getId());
                        if (((ToggleButton) v).isChecked()) {
                            mLength.setMax(mPlayer.getDuration());
                            MediaHelper.playAtSpeed(mPlayer, dailyDialogueActivity.getProgress());
                            UsageLogger.appendActivity(getContext(), "play, " + statement.getAr());

                            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    mLength.setProgress(0);
                                    mPlayer.seekTo(0);
                                    ((ToggleButton) v).setChecked(false);
                                    switchFeedbackControls(feedbackControlLayout, true, v);
                                }
                            });
                            final Handler handler = new Handler();
                            gerneralView.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (mPlayer == null) {
                                        gerneralView.removeCallbacks(this);
                                        ((ToggleButton) v).setChecked(false);
                                        mLength.setProgress(0);
                                    } else if (mProgress < mLength.getMax() - 100) {
                                        mProgress = mPlayer.getCurrentPosition();
                                        mLength.setProgress(mProgress);
                                        handler.postDelayed(this, 100);
                                    } else {
                                        gerneralView.removeCallbacks(this);
                                        mLength.setProgress(0);
                                        mProgress = 0;
                                    }
                                }
                            });
                        } else if (mPlayer.isPlaying()) {
                            mPlayer.pause();
                        }
                    }
                });

                soundwaveReplay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        UsageLogger.appendActivity(v.getContext(), "replay both in popup for statement id, " + statement.getId());
                        if (mHelper.mRecorder == null) {  // ensuring that recording is off
                            if (mPlayer != null) {
                                switchFeedbackControls(feedbackControlLayout, false, v);
                                soundwaveReplay.setEnabled(false);
                                mHelper.replayRecording(mPlayer);
                                UsageLogger.appendActivity(getContext(), "playback, " + statement.getAr());
                                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        mLength.setProgress(0);
                                        mp.seekTo(0);
                                        switchFeedbackControls(feedbackControlLayout, true, v);
                                        soundwaveReplay.setEnabled(true);
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(context, "الرجاء أيقاف التسجيل قبل السماع الى ما سجلته", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                soundwaveRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recordStatement(v, globalStatement);
                        switchFeedbackControls(feedbackControlLayout, false, v);

                    }
                });

                mLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (mPlayer != null && fromUser) {
                            UsageLogger.appendActivity(getContext(), "change playback speed in popup for statement id, " + statement.getId());
                            mProgress = progress;
                            if (progress >= seekBar.getMax())
                                mPlayer.seekTo(0);
                            else
                                mPlayer.seekTo(progress);
                        }
                    }
                });
                return true;
            }
        });
        textViewStatement.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        return gerneralView;
    }

    private void stopRecording() {
        if (mHelper.mRecorder != null) {
            mHelper.stopRecording();
        }
        if (mPlayer != null) {
            mPlayer.stop();
        }
        soundwavePlay.setChecked(false);
        mLength.setProgress(0);
    }

    private void switchFeedbackControls(LinearLayout mLayout, boolean switcher, View v) {
        for (int i = 0; i < mLayout.getChildCount(); i++) {
            View view = mLayout.getChildAt(i);

            if (v.getId() != view.getId()) { //disabling all controls except the triggering one
                view.setEnabled(switcher);
            }
        }
    }

    @Override
    public int getCount() {
        return statements.size();
    }

}


