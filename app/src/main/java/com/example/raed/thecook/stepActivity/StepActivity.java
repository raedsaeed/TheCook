package com.example.raed.thecook.stepActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Ingredient;
import com.example.raed.thecook.data.Recipe;
import com.example.raed.thecook.data.Step;
import com.example.raed.thecook.detailActivity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.example.raed.thecook.stepActivity.StepFragment.IS_LAND;

public class StepActivity extends AppCompatActivity {
    private static final String TAG = "StepActivity";
    public static String EXTRA_RECIPE = "recipe_object";

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.land_step_list_holder) != null) {
            showInLandscape();
        }else {
            showInPortrait();
        }
    }

    private void showInLandscape() {
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra(EXTRA_RECIPE);
        FragmentManager manager = getSupportFragmentManager();
        StepFragment fragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("steps_list", (ArrayList<Step>) recipe.getSteps());
        bundle.putParcelableArrayList("ingredients_list", (ArrayList<Ingredient>) recipe.getIngredients());
        bundle.putBoolean(StepFragment.IS_LAND, true);
        fragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.land_step_list_holder, fragment)
                .commit();
    }

    private void showInPortrait () {
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra(EXTRA_RECIPE);
        FragmentManager manager = getSupportFragmentManager();
        StepFragment fragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("steps_list", (ArrayList<Step>) recipe.getSteps());
        bundle.putParcelableArrayList("ingredients_list", (ArrayList<Ingredient>) recipe.getIngredients());
        bundle.putBoolean(StepFragment.IS_LAND, false);
        fragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.step_list_holder, fragment)
                .commit();

    }
}
