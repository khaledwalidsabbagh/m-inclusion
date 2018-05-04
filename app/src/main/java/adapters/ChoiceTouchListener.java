package adapters;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cylic on 3/19/18.
 */

public class ChoiceTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            //view.setVisibility(View.INVISIBLE);

            // hide the view
            view.setAlpha(0);

            // don't allow dragging it anymore
            view.setOnTouchListener(null);
            view.startDrag(data, shadowBuilder, view, 0);
            return true;
        } else {
            return false;
        }
    }
}
