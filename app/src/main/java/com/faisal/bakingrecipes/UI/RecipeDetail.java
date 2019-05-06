package com.faisal.bakingrecipes.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.faisal.bakingrecipes.POJO.Recipe;
import com.faisal.bakingrecipes.POJO.Step;
import com.faisal.bakingrecipes.R;
import com.faisal.bakingrecipes.UI.Fragments.RecipeDetailMasterFragment;
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


public class RecipeDetail extends AppCompatActivity
        implements RecipeDetailMasterFragment.onClickListener {

    public static final String POSITION = "position";
    private RecipeDetailMasterFragment mFragment;

    private boolean mTwoPane;
    private Recipe mRecipe;
    private Step mStep;
    private ExoPlayer exoPlayer;
    private PlayerView videoView;
    private TextView mDescription;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);

        if (findViewById(R.id.tablet_linear) != null) {
            mTwoPane = true;
            videoView = findViewById(R.id.player);
            mDescription = findViewById(R.id.description);
            initializePlayer();
        }

        if (getIntent().hasExtra(MainActivity.RECIPE)) {
            mRecipe = getIntent().getParcelableExtra(MainActivity.RECIPE);
        }
        if (savedInstanceState == null) {
            setUpFragment();
        }

        if (savedInstanceState != null) {
            mPosition = savedInstanceState.getInt(POSITION);
        }

    }

    private void setUpFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(MainActivity.RECIPE, mRecipe);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (mFragment == null) {
            mFragment = new RecipeDetailMasterFragment();
            mFragment.setArguments(bundle);

            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, mFragment)
                    .commit();
        }
    }

    @Override
    public void onClick(View view) {
        mPosition = (int) view.getTag();

        if (mTwoPane) {
            mStep = mRecipe.getSteps().get(mPosition);
            playVideo(mStep);

        } else {
            Intent intent = new Intent(this, RecipeStepDetail.class);
            intent.putExtra(MainActivity.RECIPE, mRecipe);
            intent.putExtra(POSITION, mPosition);
            startActivity(intent);
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
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }

    private void playVideo(Step step) {
        String videoUrl = step.getVideoURL();
        String description = step.getDescription();

        if (!TextUtils.isEmpty(videoUrl)) {
            play(videoUrl);
        }

        mDescription.setText(description);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, mPosition);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mTwoPane) {
            initializePlayer();
            mStep = mRecipe.getSteps().get(mPosition);
            playVideo(mStep);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mTwoPane) {
            releasePlayer();
        }
    }

}
