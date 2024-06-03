package com.example.memorytilegame;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TouchInterceptorView extends View {

    public TouchInterceptorView(Context context) {
        super(context);
    }

    public TouchInterceptorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchInterceptorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Intercept all touch events
        return true;
    }
}
