package com.example.raed.thecook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raed.thecook.data.Recipe;

import java.util.List;

/**
 * Created by raed on 4/10/18.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeItemViewHolder> {
    private List<Recipe> recipeList;
    private Context context;

    public RecipeAdapter (Context context) {
        this.context = context;
    }

    @Override
    public RecipeItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new RecipeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeItemViewHolder holder, int position) {
        if (recipeList != null) {
            Recipe recipe = recipeList.get(position);
            holder.recipeName.setText(recipe.getName());
        }else {
            holder.recipeName.setText("No Recipes Found");
        }
    }

    @Override
    public int getItemCount() {
        return (recipeList == null) ? 1 : recipeList.size();
    }

    public void loadData (List<Recipe> recipes) {
        this.recipeList = recipes;
        notifyDataSetChanged();
    }

    class RecipeItemViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        ImageView recipeImage;

        public RecipeItemViewHolder(View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeName = itemView.findViewById(R.id.recipe_name);
        }
    }
}
