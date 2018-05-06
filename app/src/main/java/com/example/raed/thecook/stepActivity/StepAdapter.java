package com.example.raed.thecook.stepActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Ingredient;
import com.example.raed.thecook.data.Step;
import com.example.raed.thecook.detailActivity.DetailActivity;
import com.example.raed.thecook.detailActivity.IngredientDetailFragment;
import com.example.raed.thecook.detailActivity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raed on 4/14/18.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepsViewHolder> {
    private static final String TAG = "StepAdapter";
    private Context context;
    private List<Step> stepList;
    private List<Ingredient> ingredients;
    private boolean tabTwoPane = false;

    public StepAdapter(Context context) {
        this.context = context;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.step_item, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        Step step = stepList.get(position);
        if (step != null) {
            holder.stepDescription.setText(step.getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        return (stepList != null) ? stepList.size() : 0;
    }

    public void loadSteps(List<Step> steps) {
        this.stepList = steps;
        notifyDataSetChanged();
    }

    public void setMode(boolean isTwoPane) {
        this.tabTwoPane = isTwoPane;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stepDescription;

        public StepsViewHolder(View itemView) {
            super(itemView);
            stepDescription = (itemView).findViewById(R.id.step_short_description);
            stepDescription.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (tabTwoPane) {
                int position = getAdapterPosition();
                Step step = stepList.get(position);

                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                Player player = new Player();
                Bundle bundle = new Bundle();
                bundle.putString("uri", step.getVideoURL());
                player.setArguments(bundle);
                manager.beginTransaction()
                        .replace(R.id.tablet_land_player_holder, player)
                        .commit();


                IngredientDetailFragment ingredientsDetails = new IngredientDetailFragment();
                bundle.putParcelableArrayList("ingredients", (ArrayList<Ingredient>) ingredients);
                bundle.putParcelable("step", step);
                ingredientsDetails.setArguments(bundle);
                manager.beginTransaction()
                        .replace(R.id.table_land_ingredient_holder, ingredientsDetails)
                        .commit();
            }else {
                int position = getAdapterPosition();
                Step step = stepList.get(position);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_STEP, step);
                intent.putParcelableArrayListExtra(DetailActivity.EXTRA_INGREDIENT, (ArrayList<Ingredient>) ingredients);
                context.startActivity(intent);
            }
        }
    }
}
