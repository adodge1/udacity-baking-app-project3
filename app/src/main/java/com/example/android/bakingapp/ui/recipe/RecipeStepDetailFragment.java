package com.example.android.bakingapp.ui.recipe;




import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.GestureDetector;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.Step;
import com.example.android.bakingapp.utils.NetworkUtils;



import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepDetailFragment extends Fragment implements ExoPlayer.EventListener {
    // Keys for our saved instance state
    public static final String SELECTED_POSITION_KEY = "mPlayerPosition";
    public static final String KEY_STEP_OBJ = "step_obj";
    public static final String KEY_RECIPE_OBJ = "recipe_obj";

    private SimpleExoPlayerView mPlayerView;

    @BindView(R.id.better_media)
    LinearLayout mBetterMedia;
    @BindView(R.id.tv_step_description_title)
    TextView mTitleTv;
    @BindView(R.id.tv_step_description)
    TextView mDescriptionTv;
    @BindView(R.id.btn_prev_step)
    Button mPrevBtn;
    @BindView(R.id.btn_next_step)
    Button mNextBtn;
    @BindView(R.id.iv_stepVideoImage)
    ImageView mVideoImage;
    @BindView(R.id.loading_indicator_step_detail)
    ProgressBar mLoadingIndicator;

    @BindView(R.id.player_layout)
    LinearLayout mPlayerLayout;


    private SimpleExoPlayer mExoPlayer;
    private Uri mStepVideoUri;
    private long mPlayerPosition;


    private Recipe mSelectedRecipe ;
    private Step mSelectedStep;

    // To detect when the player flings the full screen player
    private GestureDetector mGestureDetector;


    // Empty constructor
    public RecipeStepDetailFragment() {
    }

    // Static factory method to initialize the fragment with the correct arguments
    public static RecipeStepDetailFragment newInstance(Recipe recipe, Step step) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_STEP_OBJ, step);
        args.putParcelable(KEY_RECIPE_OBJ, recipe);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.step_detail_fragment, container, false);
        ButterKnife.bind(this, view);
        mPlayerView = ButterKnife.findById(mPlayerLayout, R.id.video_exoplayer);


        // Clear any old views to avoid overlaying Fragment layouts
        if (container != null) {
            container.removeAllViews();
        }


        Bundle bundle = this.getArguments();

        if (bundle != null) {
            mSelectedStep = bundle.getParcelable(KEY_STEP_OBJ);


            mTitleTv.setText(mSelectedStep.getStepShortDescription());


            if(NetworkUtils.isNetworkAvailable(getContext())){

                // Check if there is an image to the step
                if (!mSelectedStep.getThumbnailUrl().equals("")) {
                    mLoadingIndicator.setVisibility(View.GONE);
                    mVideoImage.setVisibility(View.VISIBLE);

                    Picasso.with(getContext())
                            .load(mSelectedStep.getThumbnailUrl())
                            .placeholder(R.drawable.default_step)
                            .error(R.drawable.default_step)
                            .into(mVideoImage);
                }



                // Check if there is a video on the step array
                if (!mSelectedStep.getStepVideoUrl().equals("")) {
                    mStepVideoUri = Uri.parse(mSelectedStep.getStepVideoUrl());
                    initializePlayer(mStepVideoUri);

                        //https://developer.android.com/guide/topics/resources/runtime-changes
                        //If i flip the phone i need top tool bar to go away
                        if (!getResources().getBoolean(R.bool.isTablet)) {
                            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                                hideToolBar();
                                mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);


                            } else {
                                showToolBar();
                            }
                        }
                    }


                // If there's no video or image, hide the loading indicator
               if (mSelectedStep.getThumbnailUrl().equals("") && mSelectedStep.getStepVideoUrl().equals("")) {
                    mLoadingIndicator.setVisibility(View.GONE);
                   mVideoImage.setVisibility(View.VISIBLE);
                   Picasso.with(getContext())
                           .load(R.drawable.default_step)
                           .into(mVideoImage);
                }



            }else {
                mLoadingIndicator.setVisibility(View.GONE);
                NetworkUtils.createNoConnectionDialog(getContext());
            }



            // If the RecipeStep is the first or last, hide the Previous or Next button
            //and do not add the description because it is the same
            final int stepId = mSelectedStep.getStepId();
            if (stepId == 0){
                mPrevBtn.setVisibility(View.GONE);
            }else{
                mDescriptionTv.setText(mSelectedStep.getStepDescription());
            }
            if (bundle.getParcelable(KEY_RECIPE_OBJ) != null) {
                mSelectedRecipe = bundle.getParcelable(KEY_RECIPE_OBJ);
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mSelectedRecipe.getRecipeName());

                if (stepId == mSelectedRecipe.getRecipeSteps().size() - 1){
                    mNextBtn.setVisibility(View.GONE);
                }
            }

            // Open a fragment with the previous step
            mPrevBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stepId > 0) {
                        mSelectedStep = mSelectedRecipe.getRecipeSteps().get(stepId - 1);
                        launchNewFragment(mSelectedRecipe, mSelectedStep);
                    }
                }
            });

            // Open a fragment with the next step
            mNextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stepId < mSelectedRecipe.getRecipeSteps().size() - 1) {
                        mSelectedStep = mSelectedRecipe.getRecipeSteps().get(stepId + 1);
                        launchNewFragment(mSelectedRecipe, mSelectedStep);
                    }
                }
            });


        }


        return view;

    }



    // Create a new StepDetailFragment
    public void launchNewFragment(Recipe recipe, Step step){
        int layoutId;
        // Inflate the layout in half the screen if it's a tablet in landscape
       /* if (getResources().getBoolean(R.bool.isTablet) &&
             getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutId = R.id.recipe_step_container;
        } else { // Otherwise use the full screen*/
            layoutId = R.id.placeholder2;
        //}

        RecipeStepDetailFragment stepDetailFragment = new RecipeStepDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(KEY_STEP_OBJ, step);
        args.putParcelable(KEY_RECIPE_OBJ, recipe);

        stepDetailFragment.setArguments(args);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(layoutId, stepDetailFragment)
                .commit();
    }

    //Select Recipe Step Detail View - Landscape (Phone) Video takes up full screen so i need to hide the app native tool bar

    // Hide the app bar
    private void hideToolBar(){
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    // Show the app bar
    private void showToolBar(){
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }



    /* Player methods*/

    // Initialize the player
    private void initializePlayer(Uri vidUri){
        if (mExoPlayer == null){
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "ExoPlayer"));
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource videoSource = new ExtractorMediaSource(vidUri, dataSourceFactory, extractorsFactory, null, null);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mExoPlayer.addListener(this);


            if (mPlayerPosition != C.TIME_UNSET) {
                mExoPlayer.seekTo(mPlayerPosition);
            }
            mExoPlayer.prepare(videoSource);
            mExoPlayer.setPlayWhenReady(true);

            mPlayerView.setPlayer(mExoPlayer);
            mPlayerView.setKeepScreenOn(true);
        }
    }

    // Release the player but save the position
    @Override
    public void onPause() {
        super.onPause();
        if (mExoPlayer != null) {
            mPlayerPosition = mExoPlayer.getCurrentPosition();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    // Start the player
    @Override
    public void onResume() {
        super.onResume();
        if (mStepVideoUri != null) {
            initializePlayer(mStepVideoUri);
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }
    @Override // Required override method
    public void onDestroy() {
        super.onDestroy();
    }

    // The ExoPlayer video states
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case ExoPlayer.STATE_BUFFERING:
                break;
            case ExoPlayer.STATE_IDLE:
                break;
            case ExoPlayer.STATE_READY:
                // When the video is ready to play, make it visible
                mLoadingIndicator.setVisibility(View.GONE);
                mBetterMedia.setVisibility(View.VISIBLE);
                mPlayerLayout.setVisibility(View.VISIBLE);
                break;
            case ExoPlayer.STATE_ENDED:
                break;
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle(R.string.video_error_title);
        dialog.setMessage(R.string.video_error_body);
        dialog.setPositiveButton(R.string.positive_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog ad = dialog.create();
        ad.show();

    }


    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(SELECTED_POSITION_KEY, mPlayerPosition);
        outState.putParcelable(KEY_RECIPE_OBJ, mSelectedRecipe);
        outState.putParcelable(KEY_STEP_OBJ, mSelectedStep);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            if (savedInstanceState.containsKey(SELECTED_POSITION_KEY)) {
                mPlayerPosition = savedInstanceState.getLong(SELECTED_POSITION_KEY);
            }
            if (savedInstanceState.containsKey(KEY_RECIPE_OBJ)){
                mSelectedRecipe = savedInstanceState.getParcelable(KEY_RECIPE_OBJ);
            }
            if (savedInstanceState.containsKey(KEY_STEP_OBJ)){
                mSelectedStep = savedInstanceState.getParcelable(KEY_STEP_OBJ);
            }
        }
    }





}
