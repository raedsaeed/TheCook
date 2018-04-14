package com.example.raed.thecook.stepActivity;

import com.example.raed.thecook.data.Step;

import java.util.List;

/**
 * Created by raed on 4/14/18.
 */

public interface StepContract {
    interface View {
        void showSteps(List<Step> steps);
    }

    interface Presenter {

    }
}
