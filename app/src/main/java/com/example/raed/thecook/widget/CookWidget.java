package com.example.raed.thecook.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.raed.thecook.R;
import com.example.raed.thecook.detailActivity.DetailActivity;
import com.example.raed.thecook.mainActivity.MainActivity;

import java.util.Objects;

import static com.example.raed.thecook.data.local.RecipeContract.RecipeEntry.RECIPE_ID;

/**
 * Implementation of App Widget functionality.
 */
public class CookWidget extends AppWidgetProvider {
    private static final String TAG = "CookWidget";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, int recipeId) {

        Log.d(TAG, "updateAppWidget: called"+recipeId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.cook_widget);
//        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent intent = new Intent(context, IngredientListService.class);
        intent.putExtra(RECIPE_ID, recipeId);
        views.setRemoteAdapter(R.id.ingredient_list_widget, intent);
        views.setEmptyView(R.id.ingredient_list_widget, R.id.appwidget_text);

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

    }

    public static void updateTheCookWidget (Context context, AppWidgetManager appWidgetManager,
                                            int [] appWidgetIds, int recipeId) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeId);
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

