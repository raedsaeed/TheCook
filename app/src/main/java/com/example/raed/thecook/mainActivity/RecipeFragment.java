package com.example.raed.thecook.mainActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Recipe;

import java.util.List;

/**
 * Created by raed on 5/5/18.
 */

public class RecipeFragment extends Fragment{
    private static final String TAG = "RecipeFragment";
    Context context;
    RecyclerView recyclerView;
    RecipeAdapter adapter;
    MainPresenter presenter;
    public RecipeFragment () {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recipe_part, container, false);
        recyclerView = view.findViewById(R.id.recipe_list);
        Bundle bundle = getArguments();
        adapter = new RecipeAdapter(context);

        if (bundle.getBoolean("isTablet")) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int noOfColumns = (int) (dpWidth / 180);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, noOfColumns);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);
        }else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        List<Recipe> recipeList = bundle.getParcelableArrayList("recipeList");
        adapter.loadData(recipeList);
    }

}
