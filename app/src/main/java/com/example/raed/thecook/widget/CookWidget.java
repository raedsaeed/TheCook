package com.example.raed.thecook.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Recipe;
import com.example.raed.thecook.data.local.RecipeContract;
import com.example.raed.thecook.detailActivity.DetailActivity;
import com.example.raed.thecook.mainActivity.MainActivity;
import com.example.raed.thecook.stepActivity.StepActivity;

import java.util.Objects;

import static android.provider.BaseColumns._ID;
import static com.example.raed.thecook.data.local.RecipeContract.RecipeEntry.RECIPE_ID;
import static com.example.raed.thecook.data.local.RecipeContract.RecipeEntry.RECIPE_NAME;

/**
 * Implementation of App Widget functionality.
 */
public class CookWidget extends AppWidgetProvider {
    private static final String TAG = "CookWidget";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, Recipe recipe) {

        Log.d(TAG, "updateAppWidget: called"+recipe.getId());
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.cook_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent intent = new Intent(context, IngredientListService.class);
        intent.putExtra(RECIPE_ID, recipe.getId());
        views.setTextViewText(R.id.appwidget_text, recipe.getName());
        views.setRemoteAdapter(R.id.ingredient_list_widget, intent);
        views.setEmptyView(R.id.appwidget_text, R.id.empty_widget);

        Intent detailIntent = new Intent(context, StepActivity.class);
        detailIntent.putExtra(StepActivity.EXTRA_RECIPE, recipe);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void updateFirstWidget (Context context, AppWidgetManager appWidgetManager,
                                           int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.cook_widget);
        Log.d(TAG, "updateFirstWidget: called");
        String selection = _ID+"=?";
        String [] selectionArgs = new String[] {"1"};
        Cursor cursor = context.getContentResolver().query(RecipeContract.RecipeEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        Log.d(TAG, "updateFirstWidget: " + cursor.getCount());
//        String recipeName = cursor.getString(cursor.getColumnIndex(RECIPE_NAME));

        Intent intent = new Intent(context, IngredientListService.class);
        intent.putExtra(RECIPE_ID, -1);
        views.setTextViewText(R.id.appwidget_text, "Recipes");
        views.setRemoteAdapter(R.id.ingredient_list_widget, intent);
        views.setEmptyView(R.id.appwidget_text, R.id.empty_widget);

        Intent detailIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, detailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        IngredientService.startActionUpdateWidget(context);
        Log.d(TAG, "onUpdate: called");
        IngredientService.startActionUpdateWidget(context);

    }

    public static void updateTheCookWidget (Context context, AppWidgetManager appWidgetManager,
                                            int [] appWidgetIds, Recipe recipe) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
        }
    }

    public static void updateTheCookWidget (Context context, AppWidgetManager appWidgetManager,
                                            int [] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateFirstWidget(context, appWidgetManager, appWidgetId);
        }
    }
    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

