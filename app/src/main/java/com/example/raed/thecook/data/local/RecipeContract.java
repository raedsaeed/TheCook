package com.example.raed.thecook.data.local;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by raed on 5/9/18.
 */

public class RecipeContract {
    public static final String RECIPES_PATH = "recipes";

    public static final String CONTENT_AUTHORITY = "com.example.raed.thecook";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class RecipeEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(RECIPES_PATH).build();

        public static final String TABLE_NAME = "recipes";

        public static final String RECIPE_NAME = "recipe_name";
        public static final String RECIPE_ID = "id";
        public static final String QUANTITY = "quantity";
        public static final String MEASURE = "measure";
        public static final String INGREDIENT = "ingredient";

    }

}
