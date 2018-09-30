package com.parmar.amarjot.android_recipe_book_with_firebase;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecipeDetailsFragment extends Fragment{

    private LocalSQLiteDatabaseHelper localDB;
    private OnlineSQLiteDatabaseHelper onlineDB;

    private String recipe_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recipe_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // This may not be good practice, https://stackoverflow.com/questions/11387740/where-how-to-getintent-getextras-in-an-android-fragment
        int recipe_image = getActivity().getIntent().getExtras().getInt("recipe_image");
        ImageView img = getView().findViewById(R.id.imageView);
        img.setImageResource(recipe_image);

        initilize();
    }

    private void initilize() {
        Bundle bundle = getActivity().getIntent().getExtras();
        recipe_title = bundle.getString(getString(R.string.pass_recipe_title));
        localDB = new LocalSQLiteDatabaseHelper(getContext());
        onlineDB = new OnlineSQLiteDatabaseHelper(getContext());

        setRecipeDetails();
        setupSaveButton();
    }

    private void setupSaveButton() {

        final Button saveRecipeButton = getView().findViewById(R.id.buttonSaveRecipe);
        final Button shareRecipeButton = getView().findViewById(R.id.buttonShareRecipe);


        if (localDB.recipeExists(recipe_title)) {
            saveRecipeButton.setText("Saved");
        }

        saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (localDB.recipeExists(recipe_title)) {
                    unSaveRecipe();
                    saveRecipeButton.setText("Save");
                }
                else {
                    saveRecipe();
                    saveRecipeButton.setText("Saved");
                }
            }
        });

        shareRecipeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shareRecipe();
            }
        });

    }

    private void shareRecipe() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");

        String msgToShare = recipe_title;
        sendIntent.putExtra(Intent.EXTRA_TEXT, msgToShare);

        startActivity(Intent.createChooser(sendIntent, "Share using"));
    }

    private boolean unSaveRecipe() {

        boolean result = localDB.deleteEntry(recipe_title);

        if (result) {
            toastMessage("Recipe Unsaved Succesfully");
        }
        else {
            toastMessage("An error occured");
        }

        return result;
    }

    private void saveRecipe() {

        Cursor data = onlineDB.getRecipe(recipe_title);

        Recipe recipe;

        while(data.moveToNext()) {
            String recipeName = data.getString(1);
            String recipeDescription = data.getString(2);
            String recipeCategory = data.getString(3);
            String recipeIngredients = data.getString(4);
            String recipeDirections = data.getString(5);
            String recipeImageID = data.getString(6);

            recipe = new Recipe(recipeName, recipeDescription, recipeCategory, recipeIngredients, recipeDirections, recipeImageID);

            if (localDB.addData(recipe)) {
                toastMessage("Recipe Saved Succesfully");
            }
            else {
                toastMessage("An error occured");
            }
        }
    }

    // Loads recipe details (ingredients, directions)
    private void setRecipeDetails() {

        TextView ingredient_1 = getView().findViewById(R.id.TextView_Recipe_Ingredient1);
        TextView ingredient_2 = getView().findViewById(R.id.TextView_Recipe_Ingredient2);
        TextView ingredient_3 = getView().findViewById(R.id.TextView_Recipe_Ingredient3);
        TextView ingredient_4 = getView().findViewById(R.id.TextView_Recipe_Ingredient4);
        TextView ingredient_5 = getView().findViewById(R.id.TextView_Recipe_Ingredients5);

        ingredient_1.setText(getString(R.string.ingredient1));
        ingredient_2.setText(getString(R.string.ingredient2));
        ingredient_3.setText(getString(R.string.ingredient3));
        ingredient_4.setText(getString(R.string.ingredient4));
        ingredient_5.setText(getString(R.string.ingredient5));

        TextView direction_1 = getView().findViewById(R.id.TextView_Recipe_Directions1);
        TextView direction_2 = getView().findViewById(R.id.TextView_Recipe_Directions2);
        TextView direction_3 = getView().findViewById(R.id.TextView_Recipe_Directions3);
        TextView direction_4 = getView().findViewById(R.id.TextView_Recipe_Directions4);
        TextView direction_5 = getView().findViewById(R.id.TextView_Recipe_Directions5);

        direction_1.setText(getString(R.string.direction1));
        direction_2.setText(getString(R.string.direction2));
        direction_3.setText(getString(R.string.direction3));
        direction_4.setText(getString(R.string.direction4));
        direction_5.setText(getString(R.string.direction5));
    }

    public void toastMessage(String msg){

        Context context = getContext().getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
