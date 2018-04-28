package com.example.raed.thecook.stepActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Recipe;
import com.example.raed.thecook.data.Step;

import java.util.List;

public class StepActivity extends AppCompatActivity implements StepContract.View{
    public static String EXTRA_RECIPE = "recipe_object";

    private Recipe recipe;
    private StepAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        adapter = new StepAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.step_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra(EXTRA_RECIPE);
        showSteps(recipe.getSteps());
        getSupportActionBar().setTitle(recipe.getName());
    }

    @Override
    public void showSteps(List<Step> steps) {
        adapter.loadSteps(steps);
        adapter.setIngredients(recipe.getIngredients());
    }
}
