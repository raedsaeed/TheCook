package com.example.raed.thecook.mainActivity;

import com.example.raed.thecook.data.Recipe;
import com.example.raed.thecook.network.NetworkManager;

import java.util.List;

/**
 * Created by raed on 4/11/18.
 */

public class MainPresenter implements MainContract.Presenter, NetworkManager.CompletedRequest{

    private MainContract.View view;

    public MainPresenter (MainContract.View view) {
        this.view = view;
    }

    @Override
    public void checkLandScape() {

    }

    @Override
    public void fetechData() {
        NetworkManager.getInstance(this).getData();
    }

    @Override
    public void storData(List<Recipe> recipes) {

    }

    @Override
    public List<Recipe> getDataFromStorage() {
        return null;
    }

    @Override
    public void onCompletedRequest(List<Recipe> recipes) {
        view.showData(recipes);
    }
}
