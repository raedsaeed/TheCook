package com.example.raed.thecook.widget;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.raed.thecook.R;
import com.example.raed.thecook.data.Ingredient;
import com.example.raed.thecook.data.Recipe;
import com.example.raed.thecook.data.local.RecipeContract;
import com.example.raed.thecook.mainActivity.MainActivity;

import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.example.raed.thecook.data.local.RecipeContract.RecipeEntry.RECIPE_ID;

/**
 * Created by raed on 5/10/18.
 */

public class IngredientListService extends RemoteViewsService {
    private static final String TAG = "IngredientListService";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new ListViewRemoteViewFactory(getApplicationContext(), intent.getIntExtra(RECIPE_ID, 0));

    }

    class ListViewRemoteViewFactory implements RemoteViewsFactory {
        private static final String TAG = "ListViewRemoteFacotry";
        private Context context;
        private Cursor cursor;
        private int recipeId = -1;

        public ListViewRemoteViewFactory (Context context, int recipeId) {
            this.context = context;
            this.recipeId = recipeId;
        }

        @Override
        public void onCreate() {
            Log.d(TAG, "onCreate: Called");
        }

        @Override
        public void onDataSetChanged() {
            if (recipeId == -1){
                cursor = context.getContentResolver().query(RecipeContract.RecipeEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
            }else {
                String selection = RECIPE_ID + "=?";
                String[] selectionArgs = new String[]{String.valueOf(recipeId)};
                Log.d(TAG, "onDataSetChanged: " + recipeId);
                cursor = context.getContentResolver().query(RecipeContract.RecipeEntry.CONTENT_URI,
                        null,
                        selection,
                        selectionArgs,
                        null);
            }
        }

        @Override
        public void onDestroy() {
            if (cursor != null) {
                cursor.close();
            }
        }

        @Override
        public int getCount() {
            return (cursor != null)? cursor.getCount() : 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            if (cursor == null || cursor.getCount() == 0) {
                return null;
            }
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
            if (cursor.moveToPosition(i)) {
                String ingredient = cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeEntry.INGREDIENT));
                remoteViews.setTextViewText(R.id.ingredient_widget_item, ingredient);
            }

            Intent fillIntent = new Intent();
            fillIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            remoteViews.setOnClickFillInIntent(R.layout.widget_item, fillIntent);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
