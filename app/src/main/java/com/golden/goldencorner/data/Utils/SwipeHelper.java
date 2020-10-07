package com.golden.goldencorner.data.Utils;


import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * Created by Jerry on 4/18/2018.
 */

public class SwipeHelper extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {

    // Minimal x and y axis swipe distance.
    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;

    // Maximal x and y axis swipe distance.
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;

    // Source activity that display message in text view.
//    private Context mContext = null;
//    public Context getContext() {
//        return mContext;
//    }
    //public static OnSwipeGesture onSwipeGesture;
    public static SwipeHelper getInstance(@NonNull View view, @NonNull OnSwipeGestureListener mSwipeListener) {
        return new SwipeHelper(view , mSwipeListener);
        /*if (onSwipeGesture == null)
            onSwipeGesture = new OnSwipeGesture(view, mSwipeListener);
        return onSwipeGesture;*/
    }
    private GestureDetector gestureDetector = null;
    private SwipeHelper(@NonNull View view, @NonNull OnSwipeGestureListener mSwipeListener) {
        if (mSwipeListener == null)
            new IllegalArgumentException("Swipe Gesture Listener Can't be null");
        //this.mContext = mContext;
        this.mSwipeListener = mSwipeListener;
        view.setOnTouchListener(this);
        gestureDetector = new GestureDetector(view.getContext(), this);
    }

    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX, float distanceY) {
        return super.onScroll(event1, event2, distanceX, distanceY);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    /* This method is invoked when a swipe gesture happened. */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // Get swipe delta value in x axis.
        float deltaX = e1.getX() - e2.getX();
        // Get swipe delta value in y axis.
        float deltaY = e1.getY() - e2.getY();
        // Get absolute value.
        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);
        // Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe
        if((deltaXAbs >= MIN_SWIPE_DISTANCE_X) && (deltaXAbs <= MAX_SWIPE_DISTANCE_X)) {
            if(deltaX > 0) {
                mSwipeListener.onSwipeLeft();
            } else {
                mSwipeListener.onSwipeRight();
            }
        }
        if((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y)) {
            if(deltaY > 0) {
                mSwipeListener.onSwipeUp();
            } else {
                mSwipeListener.onSwipeDown();
            }
        }
        return true;
    }

    // Invoked when single tap screen.
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        mSwipeListener.onClicked();
        return true;
    }
    // Invoked when double tap screen.
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        mSwipeListener.onDoubleClicked();
        return true;
    }

    public OnSwipeGestureListener mSwipeListener;
    public interface OnSwipeGestureListener {
        void onDoubleClicked();
        void onClicked();
        //void onScroll();
        void onSwipeUp();
        void onSwipeDown();
        void onSwipeRight();
        void onSwipeLeft();
    }

}