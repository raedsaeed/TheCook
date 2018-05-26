package com.example.raed.thecook.mainActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.raed.thecook.R;
import com.example.raed.thecook.SimpleIdlingResource;
import com.example.raed.thecook.data.Recipe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, View.OnClickListener{
    private static final String TAG = "MainActivity";
    RecipeAdapter adapter;
    MainPresenter presenter;
    boolean tabletMode = false;
    private SimpleIdlingResource idlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presenter = new MainPresenter(this);
        if (findViewById(R.id.tablet_recipe_part_holder) != null) {
            tabletMode = true;
        }
        presenter.fetechData();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (checkNetwork()) {
//            presenter.fetechData();
//
//        }else {
//            findViewById(R.id.recipe_list).setVisibility(View.GONE);
//            findViewById(R.id.connection_image).setOnClickListener(this);
//            findViewById(R.id.connection_image).setVisibility(View.VISIBLE);
//            findViewById(R.id.connection_text).setVisibility(View.VISIBLE);
//        }
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
    public void showData(List<Recipe> recipes) {
        Log.d(TAG, "showData: Got data");
        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("recipeList", (ArrayList<Recipe>) recipes);
        bundle.putBoolean("isTablet", tabletMode);
        recipeFragment.setArguments(bundle);

        if (!tabletMode) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_part_holder, recipeFragment)
                    .commit();
        }else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.tablet_recipe_part_holder, recipeFragment)
                    .commit();
        }
    }

    @Override
    public void turnToLandscape() {

    }

    private boolean checkNetwork () {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.connection_image:
//                if (checkNetwork()) {
//                    presenter.fetechData();
//                    findViewById(R.id.recipe_list).setVisibility(View.VISIBLE);
//                    findViewById(R.id.connection_image).setVisibility(View.GONE);
//                    findViewById(R.id.connection_text).setVisibility(View.GONE);
//                }
//                break;
//                default:
//                    throw new IllegalArgumentException("Wrong Id");
//
//        }
    }

    public SimpleIdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }
}
