package com.DivineInspiration.experimenter.Activity.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

/*
no clikcing: https://stackoverflow.com/questions/30425786/how-to-click-views-behind-a-toolbar/62691616#62691616
missing contructers: https://stackoverflow.com/questions/9054894/custom-surfaceview-causing-nosuchmethodexception
 */
public class NonClickableToolbar extends Toolbar {
    public NonClickableToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NonClickableToolbar(@NonNull Context context) {
        super(context);
    }

    public NonClickableToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}