package com.example.raed.thecook.stepActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Step;

import java.util.List;

/**
 * Created by raed on 4/14/18.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepsViewHolder>{
    private static final String TAG = "StepAdapter";
    private Context context;
    private List<Step> stepList;

    public StepAdapter (Context context) {
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
        if (step!=null) {
            holder.stepDescription.setText(step.getShortDescription());
        }
    }

    @Override
    public int getItemCount() {
        return (stepList!=null) ? stepList.size() : 0;
    }

    public void loadSteps (List<Step> steps) {
        this.stepList = steps;
        notifyDataSetChanged();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder {
        TextView stepDescription;
        public StepsViewHolder(View itemView) {
            super(itemView);
            stepDescription = (itemView).findViewById(R.id.step_description);
        }
    }
}
