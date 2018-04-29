package com.example.raed.thecook.detailActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Ingredient;

import java.util.List;

/**
 * Created by raed on 4/29/18.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientItemHolder> {
    private static final String TAG = "IngredientAdapter";
    private List<Ingredient> ingredientList;
    private Context context;

    public IngredientAdapter (Context context) {
        this.context = context;
    }

    @Override
    public IngredientItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent, false);
        return new IngredientItemHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientItemHolder holder, int position) {
        if (ingredientList != null) {
            Ingredient ingredient = ingredientList.get(position);
            holder.textView.setText(ingredient.getIngredient());
        }else {
            holder.textView.setText("No Ingredient Found");
        }
    }

    @Override
    public int getItemCount() {
        return (ingredientList != null)? ingredientList.size() : 1;
    }

    public void loadIngredients (List<Ingredient> ingredients) {
        this.ingredientList = ingredients;
        notifyDataSetChanged();
    }

    class IngredientItemHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public IngredientItemHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.quntity);
        }
    }
}
