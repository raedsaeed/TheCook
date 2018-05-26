package com.example.raed.thecook;

import android.app.Instrumentation;
import android.os.Bundle;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;

import com.example.raed.thecook.RecyclerViewHelpers.RecyclerViewMatcher;
import com.example.raed.thecook.data.Recipe;
import com.example.raed.thecook.data.Step;
import com.example.raed.thecook.detailActivity.DetailActivity;
import com.example.raed.thecook.stepActivity.StepActivity;
import com.example.raed.thecook.stepActivity.StepFragment;

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
 * Created by raed on 5/26/18.
 */

@RunWith(AndroidJUnit4.class)
public class StepActivityTest {
    @Rule
    public IntentsTestRule<StepActivity> intentsTestRule = new IntentsTestRule<>(StepActivity.class);


    private SimpleIdlingResource idlingResource;

    private Recipe recipe;

    public RecyclerViewMatcher withRecyclerView (int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Before
    public void registerIdlingResource () {
        idlingResource = intentsTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(RESULT_OK, null));
    }

    @Before
    public void checkFragment() {
        recipe = createTestRecipe();
        if (intentsTestRule.getActivity().findViewById(R.id.land_step_list_holder) != null) {
            showInLandscape();
        }else if (intentsTestRule.getActivity().findViewById(R.id.tablet_land_step_holder) != null) {
            showInTabletLand();
        } else{
            showInPortrait();
        }
    }

    @Test
    public void checkRecyclerViewItems () {
        onView(withRecyclerView(R.id.step_list).atPositionOnView(0, R.id.step_short_description))
                .check(matches(withText(recipe.getSteps().get(0).getShortDescription())));

        onView(withRecyclerView(R.id.step_list).atPositionOnView(0, R.id.step_short_description))
                .perform(click());
    }

    @Test
    public void clickSendSteps() {
        onView(withRecyclerView(R.id.step_list).atPositionOnView(0, R.id.step_short_description))
                .perform(click());

        intended(allOf(
                hasExtra(DetailActivity.EXTRA_STEP, recipe.getSteps().get(0))));

    }

    @After
    public void unRegisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    private void showInLandscape() {
        FragmentManager manager = intentsTestRule.getActivity().getSupportFragmentManager();
        StepFragment fragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StepFragment.RECIPE_KEY, recipe);
        bundle.putInt(StepFragment.MODE, StepFragment.LANDSCAPE_MODE);
        fragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.land_step_list_holder, fragment)
                .commit();
    }

    private void showInPortrait () {
        FragmentManager manager = intentsTestRule.getActivity().getSupportFragmentManager();
        StepFragment fragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StepFragment.RECIPE_KEY, recipe);
        bundle.putInt(StepFragment.MODE, StepFragment.PORTRAIT_MODE);
        fragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.step_list_holder, fragment)
                .commit();

    }

    private void showInTabletLand () {
        FragmentManager manager = intentsTestRule.getActivity().getSupportFragmentManager();
        StepFragment fragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(StepFragment.RECIPE_KEY, recipe);
        bundle.putInt(StepFragment.MODE, StepFragment.TABLET_LAND_MODE);
        fragment.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.tablet_land_step_holder, fragment)
                .commit();
    }

    private Recipe createTestRecipe () {
        Step step = new Step(0, "Short description", "Description", null, null);
        List<Step> steps = new ArrayList<>();
        steps.add(step);

        Recipe recipe = new Recipe(0, "Nutella pie", null, steps, 8, null);

        return recipe;
    }
}
