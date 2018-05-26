package com.example.raed.thecook;

import android.app.Instrumentation;
import android.os.Bundle;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.raed.thecook.RecyclerViewHelpers.RecyclerViewMatcher;
import com.example.raed.thecook.data.Recipe;
import com.example.raed.thecook.mainActivity.MainActivity;
import com.example.raed.thecook.mainActivity.RecipeFragment;
import com.example.raed.thecook.stepActivity.StepActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by raed on 5/25/18.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    private SimpleIdlingResource idlingResource;

    private List<Recipe> recipeList = new ArrayList<>();

    public RecyclerViewMatcher withRecyclerView (int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(RESULT_OK, null));
    }
    
    @Before
    public void registerIdlingResource () {
        idlingResource = intentsTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Before
    public void checkFragment () {
        Recipe recipe = new Recipe(0, "Test pie", 8, null);
        recipeList.add(recipe);
        Bundle bundle = new Bundle();

        if (intentsTestRule.getActivity().findViewById(R.id.recipe_part_holder) != null) {
            bundle.putParcelableArrayList("recipeList", (ArrayList<Recipe>) recipeList);
            bundle.putBoolean("isTablet", false);
            RecipeFragment recipeFragment = new RecipeFragment();
            recipeFragment.setArguments(bundle);
            intentsTestRule.getActivity().getSupportFragmentManager()
                    .beginTransaction().replace(R.id.recipe_part_holder, recipeFragment)
                    .commit();
        }else {
            bundle.putParcelableArrayList("recipeList", (ArrayList<Recipe>) recipeList);
            bundle.putBoolean("isTablet", true);
            RecipeFragment recipeFragment = new RecipeFragment();
            intentsTestRule.getActivity().getSupportFragmentManager()
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

    @Test
    public void clickSendRecipes () {
        onView(withRecyclerView(R.id.recipe_list).atPositionOnView(0, R.id.recipe_name))
                .perform(click());

        intended(allOf(
                hasExtra(StepActivity.EXTRA_RECIPE, recipeList.get(0))));
    }

    @After
    public void unRegisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(idlingResource);
        intentsTestRule.getActivity().finish();
    }

}
