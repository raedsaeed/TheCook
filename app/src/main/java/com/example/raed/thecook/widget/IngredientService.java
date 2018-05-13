package com.example.raed.thecook.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Recipe;

import static com.example.raed.thecook.data.local.RecipeContract.RecipeEntry.RECIPE_ID;

/**
 * Created by raed on 5/10/18.
 */

public class IngredientService extends IntentService {
    public static final String ACTION_UPDATE_WIDGET = "com.example.raed.thecook.widgent.ACTION_UPDATE_WIDGET";

    public IngredientService () {
        super("IngredientService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            if (ACTION_UPDATE_WIDGET.equals(intent.getAction())){
                if (intent.getParcelableExtra(RECIPE_ID)!=null) {
                    handleActionUpdate((Recipe) intent.getParcelableExtra(RECIPE_ID));
                }else {
                    handleActionUpdate();
                }
            }
        }
    }

    public static void startActionUpdateWidget(Context context, Recipe recipe) {
        Intent updateIntent = new Intent(context, IngredientService.class);
        updateIntent.putExtra(RECIPE_ID, recipe);
        updateIntent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(updateIntent);
    }

    public static void startActionUpdateWidget (Context context) {
        Intent updateIntent = new Intent(context, IngredientService.class);
        updateIntent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(updateIntent);
    }
    public void handleActionUpdate (Recipe recipeId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, CookWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredient_list_widget);
        CookWidget.updateTheCookWidget(this, appWidgetManager, appWidgetIds, recipeId);
    }

    public void handleActionUpdate () {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int [] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, CookWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredient_list_widget);
        CookWidget.updateTheCookWidget(this, appWidgetManager, appWidgetIds);
    }
}
