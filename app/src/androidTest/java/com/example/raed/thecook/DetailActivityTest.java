package com.example.raed.thecook;

import android.app.Activity;
import android.os.Bundle;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;

import com.example.raed.thecook.RecyclerViewHelpers.RecyclerViewMatcher;
import com.example.raed.thecook.data.Ingredient;
import com.example.raed.thecook.detailActivity.DetailActivity;
import com.example.raed.thecook.detailActivity.IngredientDetailFragment;
import com.example.raed.thecook.detailActivity.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by raed on 5/27/18.
 */

@RunWith(AndroidJUnit4.class)
public class DetailActivityTest {
    @Rule
    public ActivityTestRule<DetailActivity> activityTestRule = new ActivityTestRule<>(DetailActivity.class);

    private SimpleIdlingResource idlingResource;
    private String fakeUrl = "http://something.come";
    private List<Ingredient> ingredients;

    public RecyclerViewMatcher withRecyclerView (int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }


    @Before
    public void checkOrientation () {
        Activity activity = activityTestRule.getActivity();
        ingredients = createFakeIngredient();
        if (activity.findViewById(R.id.land_player_holder) != null) {
            showInLandScape();
        }else {
            showInPortrait();
        }
    }

    @Before
    public void setIdlingResource () {
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void checkIngredientList() {
        onView(withRecyclerView(R.id.ingredients_list).atPositionOnView(0, R.id.quantity))
                .check(matches(withText("3.0")));

        onView(withRecyclerView(R.id.ingredients_list).atPositionOnView(0, R.id.measure))
                .check(matches(withText("Cup")));

        onView(withRecyclerView(R.id.ingredients_list).atPositionOnView(0, R.id.ingredient))
                .check(matches(withText("Sugar")));
    }

    @After
    public void unRegisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

    private void showInLandScape() {
        FragmentManager manager = activityTestRule.getActivity().getSupportFragmentManager();
        Player player = new Player();
        Bundle bundle = new Bundle();
        bundle.putString("uri", fakeUrl);
        player.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.land_player_holder, player)
                .commit();
    }

    private void showInPortrait (){
        FragmentManager manager = activityTestRule.getActivity().getSupportFragmentManager();
        Player player = new Player();
        Bundle bundle = new Bundle();
        bundle.putString("uri", fakeUrl);
        player.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.video_part_holder, player)
                .commit();


        IngredientDetailFragment ingredientsDetails = new IngredientDetailFragment();
        bundle.putParcelableArrayList("ingredients", (ArrayList<Ingredient>) ingredients);
        ingredientsDetails.setArguments(bundle);
        manager.beginTransaction()
                .replace(R.id.ingredient_list_holder, ingredientsDetails)
                .commit();
    }

    private List<Ingredient> createFakeIngredient () {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(3, "Cup", "Sugar"));
        ingredients.add(new Ingredient(2, "Cup", "Milk"));
        ingredients.add(new Ingredient(4, "Cup", "Oil"));
        ingredients.add(new Ingredient(1, "Kilo", "Meat"));

        return ingredients;
    }
}
