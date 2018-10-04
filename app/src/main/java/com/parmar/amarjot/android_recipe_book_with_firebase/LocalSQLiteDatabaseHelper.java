package com.parmar.amarjot.android_recipe_book_with_firebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalSQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "LocalRecipes";
    private static final String COL0 = "ID";
    private static final String COL1 = "recipeName";
    private static final String COL2 = "recipeDescription";
    private static final String COL3 = "recipeCategory";
    private static final String COL4 = "recipeIngredients";
    private static final String COL5 = "recipeDirections";
    private static final String COL6 = "recipeImageID";

    public LocalSQLiteDatabaseHelper(Context context) {

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

    public boolean addRecipe(Recipe recipe) {

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

    public boolean deleteRecipe(String recipeName) {

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COL1 + "=\"" + recipeName + "\"", null);

        if (result == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Recipe getRecipe(String recipeName)
    {
        Recipe recipe = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + "=" +"\""+ recipeName +"\"" ;
        Cursor data = db.rawQuery(query, null);


        while(data.moveToNext()) {
            String recipeNamee = data.getString(1);
            String recipeDescription = data.getString(2);
            String recipeCategory = data.getString(3);
            String recipeIngredients = data.getString(4);
            String recipeDirections = data.getString(5);
            String recipeImageID = data.getString(6);

            recipe = new Recipe(recipeNamee, recipeDescription, recipeCategory, recipeIngredients, recipeDirections, recipeImageID);
            return recipe;
        }
        return recipe;
    }

    public Cursor getRecipes(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor recipes = db.rawQuery(query, null);
        return recipes;
    }

    public boolean recipeExists(String recipeName) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT EXISTS(SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + "=" +"\""+ recipeName +"\")";
        Cursor data = db.rawQuery(query, null);

        while(data.moveToNext()) {
            int dataExists = data.getInt(0);
            if (dataExists == 0) {
                return false;
            } else {
                return true;
            }
        }

        return true;
    }

}