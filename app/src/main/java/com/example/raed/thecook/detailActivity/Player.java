package com.example.raed.thecook.detailActivity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raed.thecook.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by raed on 4/29/18.
 */

public class Player extends Fragment implements ExoPlayer.EventListener {
    private static final String TAG = "Player";
    public static final String URI_KEY = "uri";
    private static final String POSITION_KEY = "position";
    private static final String PLAY_KEY = "play_when_ready";
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer player;
    private Uri videoUri;
    private long player_position;
    private boolean player_state = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_video_part, container, false);
        playerView = (SimpleExoPlayerView) view.findViewById(R.id.video_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            player_position = savedInstanceState.getLong(POSITION_KEY);
            player_state = savedInstanceState.getBoolean(PLAY_KEY);
        }

        if (getArguments().getString(URI_KEY)!= null) {
            videoUri = Uri.parse(getArguments().getString(URI_KEY));
            initializePlayer(videoUri);
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

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    private void initializePlayer (Uri uri) {
        if (player == null) {
            LoadControl loadControl = new DefaultLoadControl();
            TrackSelector trackSelector = new DefaultTrackSelector();
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            playerView.setPlayer(player);
            String userAgent = Util.getUserAgent(getContext(), "TheCook");
            MediaSource mediaSource = new ExtractorMediaSource(uri,
                    new DefaultDataSourceFactory(getContext(), userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(player_state);
            if (player_position != C.TIME_UNSET){
                player.seekTo(player_position);
            }

        }
    }

    private void releasePlayer () {
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Util.SDK_INT > 23) {
            initializePlayer(videoUri);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if(Util.SDK_INT < 24 || player == null) {
            initializePlayer(videoUri);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        player_position = player.getCurrentPosition();
        player_state = player.getPlayWhenReady();
        outState.putLong(POSITION_KEY, player_position);
        outState.putBoolean(PLAY_KEY, player_state);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23 || player != null) {
            releasePlayer();
            Log.d(TAG, "onStop: Called");
        }
    }
}
