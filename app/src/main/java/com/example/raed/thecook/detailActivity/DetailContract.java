package com.example.raed.thecook.detailActivity;

import android.net.Uri;

import com.example.raed.thecook.data.Ingredient;

import java.util.List;

/**
 * Created by raed on 4/29/18.
 */

public interface DetailContract {
    interface View {
        void showVideo (Uri uri);
        void showIngredients (List<Ingredient> ingredients);
    }
}
