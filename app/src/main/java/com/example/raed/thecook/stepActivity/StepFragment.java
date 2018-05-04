package com.example.raed.thecook.stepActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Ingredient;
import com.example.raed.thecook.data.Step;

import java.util.List;

/**
 * Created by raed on 5/4/18.
 */

public class StepFragment extends Fragment {
    private static final String TAG = "StepFragment";
    public static final String IS_LAND = "landscape";
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
        if (bundle.getBoolean(IS_LAND)){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);

        } else {
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
        List<Step> steps = bundle.getParcelableArrayList("steps_list");
        List<Ingredient> ingredients = bundle.getParcelableArrayList("ingredients_list");
        adapter.loadSteps(steps);
        adapter.setIngredients(ingredients);
    }
}
