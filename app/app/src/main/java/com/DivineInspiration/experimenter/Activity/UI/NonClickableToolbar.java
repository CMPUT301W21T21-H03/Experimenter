package com.DivineInspiration.experimenter.Activity.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

public class NonClickableToolbar extends Toolbar {

    /*
    no clicking: https://stackoverflow.com/questions/30425786/how-to-click-views-behind-a-toolbar/62691616#62691616
    missing constructors: https://stackoverflow.com/questions/9054894/custom-surfaceview-causing-nosuchmethodexception
     */

    /**
     * Constructor
     * @param context
     * context of toolbar
     * @param attrs
     * @param defStyleAttr
     */
    public NonClickableToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Constructor
     * @param context
     */
    public NonClickableToolbar(@NonNull Context context) {
        super(context);
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public NonClickableToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * When toolbar is touched
     * @param ev
     * motion event
     * @return
     * if it has been touched
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}