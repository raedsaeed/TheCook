package com.example.raed.thecook.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.raed.thecook.data.Ingredient;
import com.example.raed.thecook.data.Recipe;
import com.example.raed.thecook.data.Step;

/**
 * Created by raed on 4/6/18.
 */


@Database(entities = {Recipe.class, Ingredient.class, Step.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDoa getFullRecipeDoa();

    private static final String DB_NAME = "recipeDatabase.db";
    private static volatile RecipeDatabase instance;

    public static synchronized RecipeDatabase getInstance (Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static RecipeDatabase create (Context context) {
        return Room.databaseBuilder(context,
                RecipeDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
    }
}
