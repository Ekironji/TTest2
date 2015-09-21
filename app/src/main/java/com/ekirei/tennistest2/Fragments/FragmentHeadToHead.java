package com.ekirei.tennistest2.Fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekirei.tennistest2.R;
import com.pascalwelsch.holocircularprogressbar.HoloCircularProgressBar;

/**
 * Created by Luigi on 17/08/2015.
 */
public class FragmentHeadToHead extends Fragment {

    private HoloCircularProgressBar mHoloCircularProgressBar;

    public FragmentHeadToHead() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_headtohead, container, false);

        mHoloCircularProgressBar = (HoloCircularProgressBar) rootView.findViewById(R.id.holoCircularProgressBar);
        mHoloCircularProgressBar.setMarkerEnabled(false);

//        int totMatch = player1.vittorie() + player2.vitorie();
        int totMatch = 25;
        if (totMatch == 0){
            animateCircularBar(mHoloCircularProgressBar, 0.50, false);
        } else {
//            double progressVal = ((double)  player1.vittorie()) / totMatch;
            double progressVal = ((double)  14) / totMatch;
            boolean reverse;
            /*if (player1.vittorie() >= player2.vittorie()) {
                reverse = false;
            } else {
                reverse = true;
            }*/
            reverse = false;
            animateCircularBar(mHoloCircularProgressBar, progressVal, reverse);
        }

        return rootView;
    }

    private void animateCircularBar(final HoloCircularProgressBar progressBar, double val, final boolean reverse) {
        final float progress = (float) val;
        final ObjectAnimator progressBarAnimator;
        if (reverse){
            progressBar.setProgress(progress);
            progressBarAnimator = ObjectAnimator.ofFloat(progressBar, "progress", 1);
        } else {
            progressBarAnimator = ObjectAnimator.ofFloat(progressBar, "progress", progress);
        }
        progressBarAnimator.setDuration(1000);

        /*progressBarAnimator.addListener(new AnimatorListener() {

            @Override
            public void onAnimationCancel(final Animator animation) {
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
//				progressBar.setProgress(progress);
            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
            }

            @Override
            public void onAnimationStart(final Animator animation) {
            }
        });*/

        progressBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                progressBar.setProgress((Float) animation.getAnimatedValue());
//				Log.i("valori", "progress: " +progress + " an: " + (Float) animation.getAnimatedValue());
                if (reverse) {
                    if (progress >= (Float) animation.getAnimatedValue())
                        progressBarAnimator.cancel();
                }
            }
        });
//		progressBar.setMarkerProgress(progress);
        if (reverse){
            progressBarAnimator.reverse();
        } else {
            progressBarAnimator.start();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}
