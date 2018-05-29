package com.example.raed.thecook.stepActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.raed.thecook.R;
import com.example.raed.thecook.SimpleIdlingResource;
import com.example.raed.thecook.data.Recipe;

public class StepActivity extends AppCompatActivity {
    private static final String TAG = "StepActivity";
    public static String EXTRA_RECIPE = "recipe_object";
    private SimpleIdlingResource idlingResource;
    private Recipe recipe;
    Intent intent;

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

        intent = getIntent();
        recipe = intent.getParcelableExtra(EXTRA_RECIPE);

        if (findViewById(R.id.land_step_list_holder) != null) {
            showInLandscape();
        }else if (findViewById(R.id.tablet_land_step_holder) != null) {
            showInTabletLand();
        } else{
            showInPortrait();
        }
    }

    private void showInLandscape() {
        FragmentManager manager = getSupportFragmentManager();
        StepFragment fragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StepFragment.RECIPE_KEY, recipe);
        bundle.putInt(StepFragment.MODE, StepFragment.LANDSCAPE_MODE);
        fragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.land_step_list_holder, fragment)
                .commit();
    }

    private void showInPortrait () {
        FragmentManager manager = getSupportFragmentManager();
        StepFragment fragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StepFragment.RECIPE_KEY, recipe);
        bundle.putInt(StepFragment.MODE, StepFragment.PORTRAIT_MODE);
        fragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.step_list_holder, fragment)
                .commit();

    }

    private void showInTabletLand () {
        FragmentManager manager = getSupportFragmentManager();
        StepFragment fragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StepFragment.RECIPE_KEY, recipe);
        bundle.putInt(StepFragment.MODE, StepFragment.TABLET_LAND_MODE);
        fragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.tablet_land_step_holder, fragment)
                .commit();
    }

    public SimpleIdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }
}
