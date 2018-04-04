package com.example.raed.thecook.network;

import com.example.raed.thecook.data.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by raed on 4/4/18.
 */

public interface ApiService {
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
