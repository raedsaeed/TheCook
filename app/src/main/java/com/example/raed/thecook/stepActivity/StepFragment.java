package com.example.raed.thecook.stepActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Recipe;

/**
 * Created by raed on 5/4/18.
 */

public class StepFragment extends Fragment {
    private static final String TAG = "StepFragment";
    public static final String MODE = "mode";
    public static final String LAND_MODE_KEY = "landscape";
    public static final String PORTRAIT_MODE_KEY = "portrait";
    public static final String TABLET_MODE = "tabletMode";
    public static final String RECIPE_KEY = "recipe";

    public static final int PORTRAIT_MODE = 0;
    public static final int LANDSCAPE_MODE = 1;
    public static final int TABLET_LAND_MODE = 2;
    RecyclerView recyclerView;
    StepAdapter adapter;
    Context context;

    public StepFragment () {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.steps_part, container, false);
        recyclerView = view.findViewById(R.id.step_list);
        adapter = new StepAdapter(context);
        Bundle bundle = getArguments();
        if (bundle.getInt(MODE) == LANDSCAPE_MODE){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);

        } else if (bundle.getInt(MODE) == TABLET_LAND_MODE) {
            adapter.setMode(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        } else if (bundle.getInt(MODE) == PORTRAIT_MODE){
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        Recipe recipe = bundle.getParcelable(RECIPE_KEY);
        if (recipe != null) {
            adapter.loadSteps(recipe.getSteps());
            adapter.setIngredients(recipe.getIngredients());
            adapter.loadRecipe(recipe);
        }
    }
}
