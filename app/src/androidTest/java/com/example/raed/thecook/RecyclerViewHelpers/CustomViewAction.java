package com.example.raed.thecook.RecyclerViewHelpers;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;

/**
 * Created by raed on 5/25/18.
 */

public class CustomViewAction {
    public static ViewAction onClickChildView (final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(RecyclerView.class);
            }

            @Override
            public String getDescription() {
                return "Click done";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }
}
