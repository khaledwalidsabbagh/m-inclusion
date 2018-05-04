package com.minclusion.iteration1.utils;

/**
 * FARUQ: NOT RELEVANT TO PROBLEM
 * Hence, code is left not properly commented
 * <p>
 * QuickActionWindow
 *
 * @author ruqqq (ruqqq.sg)
 * @desc QuickAction popup window similar to Twitter/Google IO App. XML and Graphics taken from AOSP project. Some portions of code ported from AOSP.
 * <p>
 * Released under GPL License: http://www.gnu.org/licenses/gpl.html
 * Credits greatly appreciated ;)
 * <p>
 * Usage:
 * <p>
 * QuickActionWindow qa = new QuickActionWindow(this, v);
 * qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_view), "View", new OnClickListener() {
 * public void onClick(View v) {
 * // Your codes here
 * }
 * });
 * qa.show();
 */
/**
 * QuickActionWindow
 * @author ruqqq (ruqqq.sg)
 * @desc QuickAction popup window similar to Twitter/Google IO App. XML and Graphics taken from AOSP project. Some portions of code ported from AOSP.
 *
 * Released under GPL License: http://www.gnu.org/licenses/gpl.html
 * Credits greatly appreciated ;)
 *
 * Usage:
 *
 * QuickActionWindow qa = new QuickActionWindow(this, v);
 * qa.addItem(getResources().getDrawable(android.R.drawable.ic_menu_view), "View", new OnClickListener() {
 * 		public void onClick(View v) {
 *		  // Your codes here
 *		}
 * });
 * qa.show();
 *
 */

import java.net.MalformedURLException;

import com.minclusion.iteration1.R;
import com.minclusion.iteration1.utils.ImageThreadLoader.ImageLoadedListener;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class QuickActionWindow extends PopupWindow implements KeyEvent.Callback {
    private final Context mContext;
    private final LayoutInflater mInflater;
    private final WindowManager mWindowManager;
    View contentView;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mShadowHoriz;
    private int mShadowVert;
    private int mShadowTouch;
    private ImageView mArrowUp;
    private ImageView mArrowDown;
    /*private View mHeader;
        private HorizontalScrollView mTrackScroll;*/
    private ViewGroup mTrack;
    private Animation mTrackAnim;

    // These modifications make showing background for quickaction possible
    private LinearLayout quickactionWrapper;
    private LinearLayout quickactionHeader;
    /*private View mFooter;
        private View mFooterDisambig;
        private ListView mResolveList;
        private CheckBox mSetPrimaryCheckBox;*/
    private View mPView;
    private Rect mAnchor;

    // For lazy loading of icon
    private ImageThreadLoader imageLoader = new ImageThreadLoader();

    public QuickActionWindow(Context context, View pView, Rect rect) {
        super(context);
        mPView = pView;
        mAnchor = rect;
        mContext = context;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mInflater = ((Activity) mContext).getLayoutInflater();

        setContentView(R.layout.quickaction);



        mScreenWidth = mWindowManager.getDefaultDisplay().getWidth();
        mScreenHeight = mWindowManager.getDefaultDisplay().getHeight();
        setWindowLayoutMode(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Resources res = mContext.getResources();
        mShadowHoriz = res.getDimensionPixelSize(R.dimen.quickaction_shadow_horiz);
        mShadowVert = res.getDimensionPixelSize(R.dimen.quickaction_shadow_vert);
        mShadowTouch = res.getDimensionPixelSize(R.dimen.quickaction_shadow_touch);
        setWidth(mScreenWidth + mShadowHoriz + mShadowHoriz);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(0));
        mArrowUp = (ImageView) contentView.findViewById(R.id.arrow_up);
        mArrowDown = (ImageView) contentView.findViewById(R.id.arrow_down);
        mTrack = (ViewGroup) contentView.findViewById(R.id.quickaction);

        quickactionHeader =  contentView.findViewById(R.id.quickaction_header);
        quickactionWrapper =  contentView.findViewById(R.id.quickaction);
        setQuickActionWidth();


/*mTrackScroll = (HorizontalScrollView) contentView.findViewById(R.id.scroll);
        mFooter = contentView.findViewById(R.id.footer);
		mFooterDisambig = contentView.findViewById(R.id.footer_disambig);
		mResolveList = (ListView) contentView.findViewById(android.R.id.list);
		mSetPrimaryCheckBox = (CheckBox) contentView.findViewById(android.R.id.checkbox);*/
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
// Prepare track entrance animation
        mTrackAnim = AnimationUtils.loadAnimation(mContext, R.anim.quickaction);
        mTrackAnim.setInterpolator(new Interpolator() {
            public float getInterpolation(float t) {
// Pushes past the target area, then snaps back into place.
// Equation for graphing: 1.2-((x*1.6)-1.1)^2
                final float inner = (t * 1.55f) - 1.1f;
                return 1.2f - inner * inner;
            }
        });
    }

    public void setQuickActionWidth() {
        //make sure the width of quickaction (content) is as long as the one in the botton
//        if(quickactionWrapper.getLayoutParams().width < quickactionHeader.getLayoutParams().width){
//            Log.e("WIDTH_QUICKACTION", "Set to "+ quickactionWrapper.getLayoutParams().width +
//                    " FROM "+ quickactionHeader.getLayoutParams().width);
//            quickactionWrapper.getLayoutParams().width = quickactionHeader.getLayoutParams().width;
//        }
    }

    private void setContentView(int resId) {
        contentView = mInflater.inflate(resId, null);
        super.setContentView(contentView);
    }

    public void setIcon(String url) {
        contentView.findViewById(R.id.quickaction_icon).setVisibility(View.VISIBLE);
        final ImageView vImage = (ImageView) contentView.findViewById(R.id.quickaction_icon);
        Bitmap cachedImage = null;
        try {
            cachedImage = imageLoader.loadImage(url, new ImageLoadedListener() {
                public void imageLoaded(Bitmap imageBitmap) {
                    vImage.setImageBitmap(imageBitmap);
                    //Log.d("CachedImage", "Image successfully loaded: " + imageBitmap);
                }
            });
        } catch (MalformedURLException e) {
            //Log.e("CachedImage", "Bad remote image URL: " + url, e);
        }
        if (cachedImage != null) {
            setIcon(cachedImage);
        }
        setQuickActionWidth();
    }

    public void setIcon(Bitmap bm) {
        contentView.findViewById(R.id.quickaction_icon).setVisibility(View.VISIBLE);
        final ImageView vImage = (ImageView) contentView.findViewById(R.id.quickaction_icon);
        vImage.setImageBitmap(bm);
        setQuickActionWidth();
    }

    public void setIcon(Drawable d) {
        contentView.findViewById(R.id.quickaction_icon).setVisibility(View.VISIBLE);
        final ImageView vImage = (ImageView) contentView.findViewById(R.id.quickaction_icon);
        vImage.setImageDrawable(d);
        //make sure the width of quickaction (content) is as long as the one in the botton
        setQuickActionWidth();
    }

    public void setIcon(int resid) {
        setIcon(mContext.getResources().getDrawable(resid));
    }

    /**
     * Show the correct call-out arrow based on a {@link R.id} reference.
     */
    private void showArrow(int whichArrow, int requestedX) {
        final View showArrow = (whichArrow == R.id.arrow_up) ? mArrowUp : mArrowDown;
        final View hideArrow = (whichArrow == R.id.arrow_up) ? mArrowDown : mArrowUp;
// Dirty hack to get width, might cause memory leak
        final int arrowWidth = mContext.getResources().getDrawable(R.drawable.quickaction_arrow_up).getIntrinsicWidth();
        showArrow.setVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) showArrow.getLayoutParams();
        param.leftMargin = requestedX - arrowWidth / 2;
//Log.d("QuickActionWindow", "ArrowWidth: "+arrowWidth+"; LeftMargin for Arrow: "+param.leftMargin);
        hideArrow.setVisibility(View.INVISIBLE);
    }

    public void addItem(Drawable drawable, String text, OnClickListener l) {
        QuickActionItem view = (QuickActionItem) mInflater.inflate(R.layout.quickaction_item, mTrack, false);
        view.setChecked(false);
        view.setImageDrawable(drawable);
        view.setText(text);
        view.setOnClickListener(l);
        final int index = mTrack.getChildCount() - 1;
        mTrack.addView(view, index);

        setQuickActionWidth();
    }

    public void addImage(Drawable drawable, OnClickListener l) {
        QuickActionItem view = (QuickActionItem) mInflater.inflate(R.layout.quickaction_image_item,
                mTrack, false);
        view.setChecked(false);
        view.setImageDrawable(drawable);
        //view.setText(text);
        view.setOnClickListener(l);
        final int index = mTrack.getChildCount() - 1;
        mTrack.addView(view, index);

        setQuickActionWidth();
    }

    public void addString(String text, OnClickListener l) {
        QuickActionItem view = (QuickActionItem) mInflater.inflate(R.layout.quickaction_string,
                mTrack, false);
        view.setChecked(false);
        //view.setImageDrawable(drawable);
        view.setText(text);
        //view.setOnClickListener(l);
        final int index = mTrack.getChildCount() - 1;
        mTrack.addView(view, index);

        setQuickActionWidth();
    }

    public void addItem(int drawable, String text, OnClickListener l) {
        addItem(mContext.getResources().getDrawable(drawable), text, l);
    }

    public void addItem(Drawable drawable, int resid, OnClickListener l) {
        addItem(drawable, mContext.getResources().getString(resid), l);
    }

    public void addItem(int drawable, int resid, OnClickListener l) {
        addItem(mContext.getResources().getDrawable(drawable), mContext.getResources().getText(resid).toString(), l);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return false;
    }

    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
// TODO Auto-generated method stub
        return false;
    }

    private void onBackPressed() {
        dismiss();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
// TODO Auto-generated method stub
        return false;
    }

    public boolean onKeyMultiple(int keyCode, int count, KeyEvent event) {
// TODO Auto-generated method stub
        return false;
    }

    public void show() {
        show(mAnchor.centerX());
    }

    public void show(int requestedX) {
        setQuickActionWidth();

        super.showAtLocation(mPView, Gravity.NO_GRAVITY, 0, 0);
        // Calculate properly to position the popup the correctly based on height of popup
        if (isShowing()) {
            int x, y, windowAnimations;
            this.getContentView().measure(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final int blockHeight = this.getContentView().getMeasuredHeight();
            x = -mShadowHoriz;
            //Log.d("QuickActionWindow", "blockHeight: "+blockHeight);
            if (mAnchor.top > blockHeight) {
                // Show downwards callout when enough room, aligning bottom block
                // edge with top of anchor area, and adjusting to inset arrow.
                showArrow(R.id.arrow_down, requestedX);
                y = mAnchor.top - blockHeight;
                windowAnimations = R.style.QuickActionAboveAnimation;
            } else {
                // Otherwise show upwards callout, aligning block top with bottom of
                // anchor area, and adjusting to inset arrow.
                showArrow(R.id.arrow_up, requestedX);
                y = mAnchor.bottom;
                windowAnimations = R.style.QuickActionBelowAnimation;
            }
            //Log.d("QuickActionWindow", "X: "+x+"; Y: "+y+"; Width: "+mAnchor.width()+"; Center: "+mAnchor.centerX());
            setAnimationStyle(windowAnimations);
            mTrack.startAnimation(mTrackAnim);
            this.update(x, y, -1, -1);
        }

    }
}