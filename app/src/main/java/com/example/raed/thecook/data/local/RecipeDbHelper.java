package com.example.raed.thecook.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import static com.example.raed.thecook.data.local.RecipeContract.RecipeEntry.TABLE_NAME;

/**
 * Created by raed on 5/9/18.
 */

public class RecipeDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int VERSION = 1;
    public RecipeDbHelper (Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_PLANTS_TABLE = "CREATE TABLE " + RecipeContract.RecipeEntry.TABLE_NAME + " (" +
                RecipeContract.RecipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RecipeContract.RecipeEntry.RECIPE_ID + " INTEGER NOT NULL, " +
                RecipeContract.RecipeEntry.RECIPE_NAME + " TEXT NOT NULL, " +
                RecipeContract.RecipeEntry.INGREDIENT + " TEXT NOT NULL, " +
                RecipeContract.RecipeEntry.MEASURE + " TEXT NOT NULL, " +
                RecipeContract.RecipeEntry.QUANTITY + " REAL NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_PLANTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipeContract.RecipeEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
