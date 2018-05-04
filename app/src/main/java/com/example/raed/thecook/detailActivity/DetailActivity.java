package com.example.raed.thecook.detailActivity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Ingredient;
import com.example.raed.thecook.data.Step;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";
    public static final String EXTRA_STEP = "step";
    public static final String EXTRA_INGREDIENT = "ingredient";

    List<Ingredient> ingredients;
    Step step;
    boolean isTwoPane = false;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        intent = getIntent();
        if (intent != null) {
            if (findViewById(R.id.land_player_holder) != null) {
                showInLandScape();
            } else {
                showInPortrait();
            }

        }

    }

    private void showInLandScape() {
        step = intent.getParcelableExtra(EXTRA_STEP);
        FragmentManager manager = getSupportFragmentManager();
        Player player = new Player();
        Bundle bundle = new Bundle();
        bundle.putString("uri", step.getVideoURL());
        player.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.land_player_holder, player)
                .commit();
    }

    private void showInPortrait (){
        ingredients = intent.getParcelableArrayListExtra(EXTRA_INGREDIENT);
        step = intent.getParcelableExtra(EXTRA_STEP);
        FragmentManager manager = getSupportFragmentManager();
        Player player = new Player();
        Bundle bundle = new Bundle();
        bundle.putString("uri", step.getVideoURL());
        player.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.video_part_holder, player)
                .commit();


        IngredientDetailFragment ingredientsDetails = new IngredientDetailFragment();
        bundle.putParcelableArrayList("ingredients", (ArrayList<Ingredient>) ingredients);
        ingredientsDetails.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.ingredient_list_holder, ingredientsDetails)
                .commit();
    }
}
