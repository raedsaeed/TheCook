package com.example.raed.thecook;

import com.example.raed.thecook.data.Recipe;

import java.util.List;

/**
 * Created by raed on 4/10/18.
 */

public interface MainContract {

    interface View {
        void showData (List<Recipe> recipes);
        void turnToLandscape();
    }

    interface Presenter {
        void checkLandScape ();
        void fetechData();
        void storData(List<Recipe> recipes);
        List<Recipe> getDataFromStorage ();
    }
}
