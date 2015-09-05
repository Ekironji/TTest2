package com.ekirei.tennistest2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Luigi on 27/08/2015.
 */
public class PlayerBioActivity extends AppCompatActivity implements
        AppBarLayout.OnOffsetChangedListener{

    private String DEBUG_TAG = "PlayerBioActivity";

    public static final String EXTRA_NAME = "player_name";

    private CollapsingToolbarLayout collapsingToolbar;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private float maxOffset;
    private boolean valuesCalculatedAlready = false;
    private int firstAnimExecuted = 0;

    private ImageView avatarImageView;

    private ImageView playerStandImageView;

    TextView ranking_vertical_text;
    TextView ranking_value_text;

    private CollapseChangedListener collapseChangedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playerbio);

        Intent intent = getIntent();
        final String playerName = intent.getStringExtra(EXTRA_NAME);

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout.addOnOffsetChangedListener(this);

        avatarImageView = (ImageView) toolbar.getRootView().findViewById(R.id.toolbar_avatar);
        avatarImageView.setAlpha(0f);

        Glide.with(toolbar.getContext())
                .load(R.drawable.murray_head)
                .fitCenter()
                .into(avatarImageView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle(playerName);
        collapsingToolbar.setTitle("Andy Murray");

        loadBackdrop();

        ranking_vertical_text = (TextView) findViewById(R.id.text_ranking_string);
        ranking_vertical_text.setAlpha(0);
        ranking_value_text = (TextView) findViewById(R.id.text_ranking_number);
        ranking_value_text.setAlpha(0);

        AnimatorSet rankingValueAnimation = new AnimatorSet();
        rankingValueAnimation.playTogether(
                ObjectAnimator.ofFloat(ranking_value_text, View.ALPHA, 0, 1).setDuration(200),
                ObjectAnimator.ofFloat(ranking_value_text, View.TRANSLATION_X, -500, 0).setDuration(400)
        );
        AnimatorSet rankingStringAnimation = new AnimatorSet();
        rankingStringAnimation.playTogether(
                ObjectAnimator.ofFloat(ranking_vertical_text, View.ALPHA, 0, 1).setDuration(200),
                ObjectAnimator.ofFloat(ranking_vertical_text, View.TRANSLATION_X, -500, 0).setDuration(400)
        );

        AnimatorSet as = new AnimatorSet();
        as.setStartDelay(300);
        as.play(rankingValueAnimation).after(rankingStringAnimation);
        as.start();
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(R.drawable.british_flag).centerCrop().into(imageView);
//        .placeholder(R.drawable.loading_spinner)
        playerStandImageView = (ImageView) findViewById(R.id.backdrop2);
        Glide.with(this).load(R.drawable.murray_full15).fitCenter().into(playerStandImageView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        collapsingToolbar.setTitle("Andy Murray");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {

        Log.i(DEBUG_TAG, "appBarLayout= " + appBarLayout + " - offset= "+ offset);

        if (!valuesCalculatedAlready) {
            maxOffset = appBarLayout.getHeight() - toolbar.getHeight();
            valuesCalculatedAlready = true;
        }
        float expandedPercentage = 1 - (-offset / maxOffset);

//        Change Range: NewValue = (((OldValue - OldMin) * (NewMax - NewMin)) / (OldMax - OldMin)) + NewMin

        Log.i(DEBUG_TAG, "onOffsetChanged - expandedPercentage= " + expandedPercentage + " - offset= " + offset);

        if (expandedPercentage > 0.33) {
            Float alphaFirstAnimation = (((expandedPercentage - 0.33f) * (1f - 0f)) / (1f - 0.33f)) + 0f;
            playerStandImageView.setAlpha(alphaFirstAnimation);
            if (firstAnimExecuted > 5) {
                ranking_value_text.setAlpha(alphaFirstAnimation);
                ranking_vertical_text.setAlpha(alphaFirstAnimation);
            } else {
                firstAnimExecuted++;
            }
            avatarImageView.setAlpha(0f);
        } else if (expandedPercentage < 0.33) {
            Float alphaAvatar = (((expandedPercentage - 0.10245901f) * (1f - 0f)) / (0.33f - 0.10245901f)) + 0f;
            avatarImageView.setAlpha(1f - alphaAvatar);
            playerStandImageView.setAlpha(0f);
            ranking_value_text.setAlpha(0f);
            ranking_vertical_text.setAlpha(0f);
        }

        notifyListener(expandedPercentage);
    }

    private void notifyListener(float collapsedProgress) {
        if (collapseChangedListener != null) {
            collapseChangedListener.onCollapseChanged(collapsedProgress);
        }
    }

    public interface CollapseChangedListener {

        void onCollapseChanged(float collapsedProgress);
    }

    public void setCollapseChangedListener(CollapseChangedListener collapseChangedListener) {
        this.collapseChangedListener = collapseChangedListener;
    }
}
