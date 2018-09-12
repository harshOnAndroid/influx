package com.harsh.influx.utils;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class AnimUtil {

    // slide the view from below itself to the current position
    public static void slideUp(View view){
        ((ViewGroup)view.getParent()).setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setTag(true);
    }

    // slide the view from its current position to below itself
    public static void slideDown(final View view){
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                ((ViewGroup)view.getParent()).setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animate);
        view.setTag(false);
        view.setVisibility(View.GONE);
        ((ViewGroup)view.getParent()).setVisibility(View.GONE);
    }

    public static void toggleSlide(View view) {
        boolean isUp = (boolean) view.getTag();
        if (isUp) {
            slideDown(view);
        } else {
            slideUp(view);
        }
//        isUp = !isUp;
//        view.setTag(isUp);
    }
}
