package com.parmar.amarjot.android_recipe_book_with_firebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OnlineSQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "OnlineRecipes";
    private static final String COL0 = "ID";
    private static final String COL1 = "recipeName";
    private static final String COL2 = "recipeDescription";
    private static final String COL3 = "recipeCategory";
    private static final String COL4 = "recipeIngredients";
    private static final String COL5 = "recipeDirections";
    private static final String COL6 = "recipeImageID";

    public OnlineSQLiteDatabaseHelper(Context context) {

        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " " +  COL1 + " TEXT," + " " +  COL2 + " TEXT," + " " +  COL3 +  " TEXT," + " " +
                COL4 + " TEXT," + " " +  COL5 + " TEXT," + " " +  COL6 + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor getRecipe(String recipeName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + "=" +"\""+ recipeName +"\"" ;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean addData(Recipe recipe) {

        Recipe addRecipe = recipe;
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, addRecipe.getName());
        contentValues.put(COL2, addRecipe.getDescription());
        contentValues.put(COL3, addRecipe.getCategory());
        contentValues.put(COL4, addRecipe.getIngredients());
        contentValues.put(COL5, addRecipe.getDirections());
        contentValues.put(COL6, addRecipe.getImageID());
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+TABLE_NAME;
        db.execSQL(clearDBQuery);
    }
}