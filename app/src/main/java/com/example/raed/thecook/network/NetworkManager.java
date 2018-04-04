package com.example.raed.thecook.network;

import android.content.ComponentCallbacks;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.raed.thecook.data.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by raed on 4/4/18.
 */

public class NetworkManager implements Callback<List<Recipe>>{
    private static final String TAG = "NetworkManager";

    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private static Retrofit retrofit;
    private static ApiService apiService;
    private CompletedRequest request;

    private NetworkManager (CompletedRequest request) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        this.request = request;
    }

    public static NetworkManager getInstance (CompletedRequest completedRequest){
        return new NetworkManager(completedRequest);
    }

    public void getData () {
        Call<List<Recipe>> response = apiService.getRecipes();
        response.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
        List<Recipe> recipeList= response.body();
        request.onCompletedRequest(recipeList);
    }

    @Override
    public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
        Log.d(TAG, "onFailure: Called " + t.getMessage());
    }

    public interface CompletedRequest {
        void onCompletedRequest (List<Recipe> recipes);
    }
}
