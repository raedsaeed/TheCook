package com.example.raed.thecook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.raed.thecook.data.Ingredient;
import com.example.raed.thecook.data.Recipe;
import com.example.raed.thecook.data.local.RecipeDatabase;
import com.example.raed.thecook.network.NetworkManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NetworkManager.CompletedRequest{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NetworkManager manager = NetworkManager.getInstance(this);
        manager.getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCompletedRequest(List<Recipe> recipes) {
        RecipeDatabase.getInstance(this).getFullRecipeDoa().addRecipes(recipes);

        List<Ingredient> ingredients = RecipeDatabase.getInstance(this).getFullRecipeDoa().getAllIngredients(2);

        for (int i=0; i<ingredients.size(); i++) {
                Log.d(TAG, "onCompletedRequest: " + ingredients.get(i).getIngredient());
        }

        List<Recipe> recipeList = RecipeDatabase.getInstance(this).getFullRecipeDoa().getRecipes();
        for (Recipe recipe :recipeList) {
            for (int i=0; i<recipe.getSteps().size(); i++){
                Log.d(TAG, "onCompletedRequest: " + recipe.getSteps().get(i).getDescription());
            }
        }
    }
}
