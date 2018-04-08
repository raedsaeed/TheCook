package com.example.raed.thecook.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.raed.thecook.data.Ingredient;
import com.example.raed.thecook.data.Recipe;
import com.example.raed.thecook.data.Step;

import java.util.List;


/**
 * Created by raed on 4/7/18.
 */

@Dao
public abstract class RecipeDoa {
    private static final String TAG = "RecipeDoa";

    public void addRecipes(List<Recipe> recipeList) {
        for (int i=0; i<recipeList.size(); i++) {
            insertRecipe(recipeList.get(i));
        }
    }

    private void insertRecipe (Recipe recipe) {
            List<Ingredient> ingredients = recipe.getIngredients();
            for (Ingredient ingredient :ingredients) {
                ingredient.setRecipeId(recipe.getId());
            }

            List<Step> steps = recipe.getSteps();
            for (Step step : steps) {
                step.setRecipeId(recipe.getId());
            }
        insertOneRecipe(recipe);
        insertMultipleIngredient(ingredients);
        insertMultipleSteps(steps);
    }

    public List<Recipe> getRecipes() {
        List<Recipe> recipes = getAllRecipes();
        for (Recipe recipe : recipes) {
            List<Ingredient> ingredients = getIngredientWithId(recipe.getId());
            recipe.setIngredients (ingredients);
            List<Step> steps = getAllStepsWithId(recipe.getId());
            recipe.setSteps(steps);
        }
        return recipes;
    }

    public List<Ingredient> getAllIngredients (int id) {
        return getIngredientWithId(id);
    }

    @Insert(onConflict = 1)
    abstract void inserOneIngredient(Ingredient ingredient);

    @Insert(onConflict = 1)
    abstract void insertMultipleIngredient(List<Ingredient> ingredientList);

    @Query("DELETE FROM ingredient WHERE recipeId=:id")
    abstract int deleteIngredient (int id);

    @Query("SELECT * FROM ingredient WHERE recipeId=:id")
    abstract List<Ingredient> getIngredientWithId(int id);



    @Insert(onConflict = 1)
    abstract long insertOneRecipe(Recipe recipe);

    @Insert(onConflict = 1)
    abstract void insertRecipes(List<Recipe> recipes);

    @Query("DELETE FROM recipes WHERE id=:id")
    abstract int deleteRecipeWithId(int id);

    @Query("SELECT * FROM recipes")
    abstract List<Recipe> getAllRecipes();


    @Insert(onConflict = 1)
    abstract long insertOneStep(Step step);

    @Insert(onConflict = 1)
    abstract void insertMultipleSteps(List<Step> steps);

    @Query("DELETE FROM steps WHERE recipeId =:id")
    abstract int deleteSteps (int id);

    @Query("SELECT * FROM steps WHERE recipeId=:id")
    abstract List<Step> getAllStepsWithId(int id);

}
