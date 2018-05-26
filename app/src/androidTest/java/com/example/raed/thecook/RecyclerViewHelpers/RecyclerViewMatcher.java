package com.example.raed.thecook.RecyclerViewHelpers;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by raed on 5/25/18.
 */

public class RecyclerViewMatcher {
    private final int rvId;
    private static final String TAG = "RecyclerViewMatcher";

    public RecyclerViewMatcher(int rvId) {
        this.rvId = rvId;
    }

    public Matcher<View> atPosition (int position) {
        return atPositionOnView(position, -1);
    }

    public Matcher<View> atPositionOnView (final int position, final int viewId) {
        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            View childView;

            @Override
            protected boolean matchesSafely(View item) {
                this.resources = item.getResources();
                if (childView == null) {
                    RecyclerView recyclerView = item.getRootView().findViewById(rvId);
                    if (recyclerView != null && recyclerView.getId() == rvId) {
                        childView = recyclerView.getChildAt(position);
                    }
                    else {
                        return false;
                    }
                }

                if (viewId == -1) {
                    return item == childView;
                } else {
                    View v = childView.findViewById(viewId);
                    return item == v;
                }
            }

            @Override
            public void describeTo(Description description) {
                String idDescription = Integer.toString(rvId);
                if (this.resources != null) {
                    idDescription = this.resources.getResourceName(rvId);
                }
                description.appendText("with id : " + idDescription);
            }
        };
    }
}
