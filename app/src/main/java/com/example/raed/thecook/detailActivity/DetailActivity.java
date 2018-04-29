package com.example.raed.thecook.detailActivity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Ingredient;
import com.example.raed.thecook.data.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    public static final String EXTRA_STEP = "step";
    public static final String EXTRA_INGREDIENT = "ingredient";

    SimpleExoPlayerView playerView;
    SimpleExoPlayer player;
    List<Ingredient> ingredients;
    Step step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        playerView = (SimpleExoPlayerView)findViewById(R.id.video_view);

        Intent intent = getIntent();
        if (intent != null) {
            ingredients = intent.getParcelableArrayListExtra(EXTRA_INGREDIENT);
            step = intent.getParcelableExtra(EXTRA_STEP);
            String url = step.getVideoURL();
            Log.d(TAG, "onCreate: " + url);
//            initializePlayer(Uri.parse(url));
            FragmentManager manager = getSupportFragmentManager();
            Player player = new Player();
            Bundle bundle = new Bundle();
            bundle.putString("uri", step.getVideoURL());
            player.setArguments(bundle);
            manager.beginTransaction()
                    .replace(R.id.video_part_holder, player)
                    .commit();


            IngredientsDetails ingredientsDetails = new IngredientsDetails();
            bundle.putParcelableArrayList("ingredients", (ArrayList<Ingredient>)ingredients);
            ingredientsDetails.setArguments(bundle);
            manager.beginTransaction()
                    .replace(R.id.ingredient_list_holder, ingredientsDetails)
                    .commit();
        }

    }

    private void initializePlayer (Uri uri) {
        LoadControl loadControl = new DefaultLoadControl();
        TrackSelector trackSelector = new DefaultTrackSelector();
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
        playerView.setPlayer(player);
        String userAgent = Util.getUserAgent(this, "TheCook");
        MediaSource mediaSource = new ExtractorMediaSource(uri,
                new DefaultDataSourceFactory(this, userAgent),
                new DefaultExtractorsFactory(),
                null,
                null);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);

    }
}
