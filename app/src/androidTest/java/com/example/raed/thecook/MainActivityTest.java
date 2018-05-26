package com.example.raed.thecook;

import android.os.Bundle;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.raed.thecook.RecyclerViewHelpers.CustomViewAction;
import com.example.raed.thecook.RecyclerViewHelpers.RecyclerViewMatcher;
import com.example.raed.thecook.data.Recipe;
import com.example.raed.thecook.mainActivity.MainActivity;
import com.example.raed.thecook.mainActivity.RecipeFragment;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by raed on 5/25/18.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private static final String TAG = "MainActivityTest";
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private SimpleIdlingResource idlingResource;

    private List<Recipe> recipeList = new ArrayList<>();

    public RecyclerViewMatcher withRecyclerView (int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Before
    public void registerIdlingResource () {
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Before
    public void checkFragment () {
        Recipe recipe = new Recipe(0, "Test pie", 8, null);
        recipeList.add(recipe);
        Bundle bundle = new Bundle();

        if (activityTestRule.getActivity().findViewById(R.id.recipe_part_holder) != null) {
            bundle.putParcelableArrayList("recipeList", (ArrayList<Recipe>) recipeList);
            bundle.putBoolean("isTablet", false);
            RecipeFragment recipeFragment = new RecipeFragment();
            recipeFragment.setArguments(bundle);
            activityTestRule.getActivity().getSupportFragmentManager()
                    .beginTransaction().replace(R.id.recipe_part_holder, recipeFragment)
                    .commit();
        }else {
            bundle.putParcelableArrayList("recipeList", (ArrayList<Recipe>) recipeList);
            bundle.putBoolean("isTablet", true);
            RecipeFragment recipeFragment = new RecipeFragment();
            activityTestRule.getActivity().getSupportFragmentManager()
                    .beginTransaction().replace(R.id.tablet_recipe_part_holder, recipeFragment)
                    .commit();
        }

    }

    @Test
    public void isRecyclerViewClickable () {
        onView(withRecyclerView(R.id.recipe_list).atPositionOnView(0, R.id.recipe_name))
                .check(matches(withText("Test pie")));

        onView(withRecyclerView(R.id.recipe_list).atPosition(0))
                .perform(click());
    }

    @After
    public void unRegisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(idlingResource);
        activityTestRule.getActivity().finish();
    }

}
