package com.example.raed.thecook.detailActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Ingredient;
import com.example.raed.thecook.data.Recipe;
import com.example.raed.thecook.data.Step;
import com.example.raed.thecook.data.local.RecipeContract;
import com.example.raed.thecook.widget.IngredientService;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DetailActivity";
    public static final String EXTRA_STEP = "step";
    public static final String EXTRA_INGREDIENT = "ingredient";
    public static final String EXTRA_RECIPE_NAME = "recipe";

    List<Ingredient> ingredients;
    Step step;
    Recipe recipe;
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

        if (findViewById(R.id.fab) != null) {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(this);
        }

        intent = getIntent();
        if (intent != null) {
            if (findViewById(R.id.land_player_holder) != null) {
                showInLandScape();
            } else {
                showInPortrait();
            }
             recipe = intent.getParcelableExtra(EXTRA_RECIPE_NAME);
            actionBar.setTitle(recipe.getName());
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
        bundle.putParcelable("step", step);
        ingredientsDetails.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.ingredient_list_holder, ingredientsDetails)
                .commit();
    }

    @Override
    public void onClick(View view) {
        int id = recipe.getId();
        for (Ingredient ingredient : ingredients) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(RecipeContract.RecipeEntry.INGREDIENT, ingredient.getIngredient());
            contentValues.put(RecipeContract.RecipeEntry.MEASURE, ingredient.getMeasure());
            contentValues.put(RecipeContract.RecipeEntry.QUANTITY, ingredient.getQuantity());
            contentValues.put(RecipeContract.RecipeEntry.RECIPE_ID, id);
            contentValues.put(RecipeContract.RecipeEntry.RECIPE_NAME, recipe.getName());
            getContentResolver().insert(RecipeContract.RecipeEntry.CONTENT_URI, contentValues);
        }
        IngredientService.startActionUpdateWidget(this, recipe);
        Toast.makeText(this, "Added to favourite ", Toast.LENGTH_SHORT).show();
    }
}
