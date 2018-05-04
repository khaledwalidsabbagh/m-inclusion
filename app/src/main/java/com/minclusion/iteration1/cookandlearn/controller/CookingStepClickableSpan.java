package com.minclusion.iteration1.cookandlearn.controller;

import android.content.Context;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.minclusion.iteration1.R;
import com.minclusion.iteration1.utils.QuickActionWindow;
import com.minclusion.iteration1.utils.Util;

/**
 * Created by cylic on 3/1/18.
 */

public class CookingStepClickableSpan extends ClickableSpan {
    private final String mText;
    private final String origionalText;
    private Context context;
    //color to be used with clickable spans
    private Integer color;

    public CookingStepClickableSpan(Context ctx, final String origionalText, final String text) {
        //swedish text
        this.origionalText = origionalText;
        //arabic text
        this.mText = text;
        this.context = ctx;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    @Override
    public void onClick(View textView) {

        QuickActionWindow actionWindow = new QuickActionWindow(context,
                textView, getRectangle(textView) );
        actionWindow.addString(mText, new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        actionWindow.addImage(ContextCompat.getDrawable(context, R.drawable.ic_play), new View.OnClickListener() {
            public void onClick(View v) {
                // play the music

                String name = Util.getResourceName(origionalText);

                int resID = context.getResources().getIdentifier(
                        name+"_audio",
                        "raw", context.getPackageName()
                );

                if(resID == 0){
                    resID = context.getResources().getIdentifier(
                            name, "raw", context.getPackageName()
                    );
                }

                //play an audio if the file is found
                if(resID != 0) {
                    try {
                        final MediaPlayer player = MediaPlayer.create(context, resID);
                        player.start();
                    }catch (Exception ex){
                        //if the resource cannot be played skip it

                    }
                }
            }
        });
        actionWindow.show();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);

        if(color != null)
            ds.setColor(color);
    }

    public Rect getRectangle(View widget) {
        TextView parentTextView = (TextView) widget;
        Rect parentTextViewRect = new Rect();

        // Initialize values for the computing of clickedText position
        SpannableString completeText = (SpannableString)(parentTextView).getText();
        Layout textViewLayout = parentTextView.getLayout();

        double startOffsetOfClickedText = completeText.getSpanStart(this);
        double endOffsetOfClickedText = completeText.getSpanEnd(this);
        double startXCoordinatesOfClickedText = textViewLayout.getPrimaryHorizontal((int)startOffsetOfClickedText);
        double endXCoordinatesOfClickedText = textViewLayout.getPrimaryHorizontal((int)endOffsetOfClickedText);

        // Get the rectangle of the clicked text
        int currentLineStartOffset = textViewLayout.getLineForOffset((int)startOffsetOfClickedText);
        int currentLineEndOffset = textViewLayout.getLineForOffset((int)endOffsetOfClickedText);
        boolean keywordIsInMultiLine = currentLineStartOffset != currentLineEndOffset;
        textViewLayout.getLineBounds(currentLineStartOffset, parentTextViewRect);

        // Update the rectangle position to his real position on screen
        int[] parentTextViewLocation = {0,0};
        parentTextView.getLocationOnScreen(parentTextViewLocation);

        double parentTextViewTopAndBottomOffset = (
                parentTextViewLocation[1] -
                        parentTextView.getScrollY() +
                        parentTextView.getCompoundPaddingTop()
        );
        parentTextViewRect.top += parentTextViewTopAndBottomOffset;
        parentTextViewRect.bottom += parentTextViewTopAndBottomOffset;

        parentTextViewRect.left += (
                parentTextViewLocation[0] +
                        startXCoordinatesOfClickedText +
                        parentTextView.getCompoundPaddingLeft() -
                        parentTextView.getScrollX()
        );
        parentTextViewRect.right = (int) (
                parentTextViewRect.left +
                        endXCoordinatesOfClickedText -
                        startXCoordinatesOfClickedText
        );

        int x = parentTextViewRect.left;

        //int x = (parentTextViewRect.left + parentTextViewRect.right) / 2;
        int y = parentTextViewRect.top;
        if (keywordIsInMultiLine) {
            x = parentTextViewRect.left;
        }

        Log.e("LOCATION", "location2:" + x + "," + y +
                " startXCoordinatesOfClickedText " + startXCoordinatesOfClickedText +
                "endXCoordinatesOfClickedText" + endXCoordinatesOfClickedText);

        return new Rect(x, y, x+1, y +1);
    }
}