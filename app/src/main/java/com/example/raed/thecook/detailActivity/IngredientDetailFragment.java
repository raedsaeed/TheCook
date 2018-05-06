package com.example.raed.thecook.detailActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Ingredient;
import com.example.raed.thecook.data.Step;

import java.util.List;

import cz.intik.overflowindicator.OverflowPagerIndicator;
import cz.intik.overflowindicator.SimpleSnapHelper;

/**
 * Created by raed on 4/29/18.
 */

public class IngredientDetailFragment extends Fragment {
    private static final String TAG = "IngredientDetailFragment";
    private Context context;
    RecyclerView recyclerView;
    TextView stepDescription;
    IngredientAdapter adapter;
    List<Ingredient> ingredients;
    Step step;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_ingredient_part, container, false);
        recyclerView = view.findViewById(R.id.ingredients_list);
        stepDescription = view.findViewById(R.id.step_description);
        adapter = new IngredientAdapter(context);
        LinearLayoutManager horizontalManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalManager);
        recyclerView.setAdapter(adapter);
//        OverflowPagerIndicator indicator = view.findViewById(R.id.view_pager_indicator);
//        SimpleSnapHelper snapHelper = new SimpleSnapHelper(indicator);
//        indicator.attachToRecyclerView(recyclerView);
//        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        return view;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        ingredients = bundle.getParcelableArrayList("ingredients");
        step = bundle.getParcelable("step");
        if (step != null) {
            stepDescription.setText(step.getDescription());
            Log.d(TAG, "onViewCreated: " + step.getDescription());
        }else {
            stepDescription.setText("No Description");
        }
        adapter.loadIngredients(ingredients);
    }
}
