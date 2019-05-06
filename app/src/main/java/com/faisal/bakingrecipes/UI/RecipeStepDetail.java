package com.faisal.bakingrecipes.UI;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.faisal.bakingrecipes.POJO.Recipe;
import com.faisal.bakingrecipes.POJO.Step;
import com.faisal.bakingrecipes.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;


public class RecipeStepDetail extends AppCompatActivity {

    private final static String LOG_TAG = RecipeStepDetail.class.getName();
    private static final String POSITION = "position";

    private List<Step> mSteps;

    private int mPosition;
    private boolean mPortrait;

    private ExoPlayer exoPlayer;
    private PlayerView videoView;

    private TextView mDescription;
    private Button mNext;
    private Button mPrevious;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_step_detail);
        mPortrait = false;

        if (getIntent().hasExtra(MainActivity.RECIPE)) {
            Recipe mRecipe = getIntent().getParcelableExtra(MainActivity.RECIPE);
            mSteps = mRecipe.getSteps();
            mPosition = getIntent().getIntExtra(RecipeDetail.POSITION, 0);
        }

        if (findViewById(R.id.detail_view_description) != null) {
            mDescription = findViewById(R.id.detail_view_description);
            mNext = findViewById(R.id.next);
            mPrevious = findViewById(R.id.previous);
            mPortrait = true;
            setListeners();
        }

        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt(POSITION);
        }

        videoView = findViewById(R.id.playerView);
        initializePlayer();
        playVideo(mPosition);
    }

    private void playVideo(int position) {
        String videoUrl = mSteps.get(position).getVideoURL();
        String description = mSteps.get(position).getDescription();

        if (!TextUtils.isEmpty(videoUrl)) {
            play(videoUrl);
        }

        if (mPortrait) {
            mDescription.setText(description);
        }

    }

    private void playNext() {
        if (mPosition < mSteps.size() - 1) {
            mPosition++;
            playVideo(mPosition);
        }
    }

    private void playPrevious() {
        if (mPosition > 0) {
            mPosition--;
            playVideo(mPosition);
        }
    }

    private void initializePlayer() {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        RenderersFactory renderersFactory = new DefaultRenderersFactory(this);

        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, renderersFactory,
                trackSelector, loadControl);

        videoView.setPlayer(exoPlayer);
    }

    private void setListeners() {
        mNext.setOnClickListener(view -> playNext());

        mPrevious.setOnClickListener(view -> playPrevious());
    }

    private void play(String url) {

        String userAgent = Util.getUserAgent(this, getString(R.string.app_name));

        MediaSource mediaSource = new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(this, userAgent))
                .setExtractorsFactory(new DefaultExtractorsFactory())
                .createMediaSource(Uri.parse(url));

        exoPlayer.prepare(mediaSource);

        exoPlayer.setPlayWhenReady(true);
    }

    private void releasePlayer() {
        exoPlayer.release();
        exoPlayer = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, mPosition);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initializePlayer();
        playVideo(mPosition);
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }
}
