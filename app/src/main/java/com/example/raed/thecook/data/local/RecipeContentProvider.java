package com.example.raed.thecook.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.raed.thecook.data.Recipe;

/**
 * Created by raed on 5/9/18.
 */

public class RecipeContentProvider extends ContentProvider {
    public static final int RECIPES = 100;
    public static final int RECIPES_WITH_ID = 101;
    private UriMatcher uriMatcher = buildUriMatcher();

    private UriMatcher buildUriMatcher () {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(RecipeContract.CONTENT_AUTHORITY, RecipeContract.RECIPES_PATH, RECIPES);
        uriMatcher.addURI(RecipeContract.CONTENT_AUTHORITY, RecipeContract.RECIPES_PATH + "/#", RECIPES_WITH_ID);
        return uriMatcher;
    }

    private RecipeDbHelper dbHelper;
    private Context context;

    @Override
    public boolean onCreate() {
        context = getContext();
        dbHelper = new RecipeDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        final SQLiteDatabase database = dbHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);
        Cursor cursor;
        switch (match) {
            case RECIPES:
                cursor = database.query(RecipeContract.RecipeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case RECIPES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                cursor = database.query(RecipeContract.RecipeEntry.TABLE_NAME,
                        projection,
                        "id = ?",
                        new String [] {id},
                        null,
                        null,
                        sortOrder);
                break;
                default:
                    throw  new UnsupportedOperationException("Unknown Uri " + uri.toString());
        }
        cursor.setNotificationUri(context.getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case RECIPES :
                long id = database.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(RecipeContract.RecipeEntry.CONTENT_URI, id);
                }else {
                    throw new android.database.SQLException("Failed to start new row");
                }
                break;
                default:
                    throw new UnsupportedOperationException("Unknown Uri" + uri.toString());
        }
        context.getContentResolver().notifyChange(uri,null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int recipeDeleted;
        switch (match) {
            case RECIPES_WITH_ID :
                String id = uri.getPathSegments().get(1);
                recipeDeleted = database.delete(RecipeContract.RecipeEntry.TABLE_NAME,
                        "id = ?",
                        new String [] {id});
                break;
                default:
                    throw new UnsupportedOperationException("Unknown Uri" + uri.toString());
        }
        if (recipeDeleted != 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return recipeDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
