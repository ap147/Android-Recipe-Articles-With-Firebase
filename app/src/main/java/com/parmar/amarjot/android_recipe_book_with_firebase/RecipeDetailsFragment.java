package com.parmar.amarjot.android_recipe_book_with_firebase;

import android.content.Context;
import android.content.Intent;
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

    private RecipeSQLiteDatabaseHelper localDB;
    private RecipeSQLiteDatabaseHelper onlineDB;

    private Recipe recipe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recipe_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // This may not be good practice, https://stackoverflow.com/questions/11387740/where-how-to-getintent-getextras-in-an-android-fragment
        int recipe_image = getActivity().getIntent().getExtras().getInt(getString(R.string.pass_recipe_image));
        ImageView img = getView().findViewById(R.id.imageView);
        img.setImageResource(R.drawable.canaloni);//recipe_image);

        initilize();
    }

    private void initilize() {
        Bundle bundle = getActivity().getIntent().getExtras();
        String recipe_title = bundle.getString(getString(R.string.pass_recipe_title));
        String recipe_type = bundle.getString(getString(R.string.pass_recipe_type));

        localDB = new RecipeSQLiteDatabaseHelper(getContext(), getString(R.string.local_db));

        if (recipe_type.equals(getString(R.string.recipe_type_local))) {
            recipe = localDB.getRecipe(recipe_title);
        }
        else {
            onlineDB = new RecipeSQLiteDatabaseHelper(getContext(), getString(R.string.online_db));
            recipe = onlineDB.getRecipe(recipe_title);
        }

        setRecipeDetails();
        setupSaveButton();
        setupShareButton();
    }

    private void setupSaveButton() {

        final Button saveRecipeButton = getView().findViewById(R.id.buttonSaveRecipe);
        String recipeName = recipe.getName();
        if (localDB.recipeExists(recipeName)) {
            saveRecipeButton.setText("Saved");
        }

        saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (localDB.recipeExists(recipe.getName())) {
                    unSaveRecipe();
                    saveRecipeButton.setText("Save");
                }
                else {
                    saveRecipe();
                    saveRecipeButton.setText("Saved");
                }
            }
        });
    }

    private void setupShareButton() {

        final Button shareRecipeButton = getView().findViewById(R.id.buttonShareRecipe);

        shareRecipeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shareRecipe();
            }
        });
    }

    private void shareRecipe() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");

        String msgToShare = recipe.getName() + "\n" + recipe.getDescription() + ", "
                + recipe.getCategory() + ", " + recipe.getArticle();
        sendIntent.putExtra(Intent.EXTRA_TEXT, msgToShare);

        startActivity(Intent.createChooser(sendIntent, "Share using"));
    }

    private void saveRecipe() {

        if (localDB.addRecipe(recipe)) {
            toastMessage("Recipe Saved Succesfully");
        }
        else {
            toastMessage("An error occured");
        }
    }

    private boolean unSaveRecipe() {

        boolean result = localDB.deleteRecipe(recipe.getName());

        if (result) {
            toastMessage("Recipe Unsaved Succesfully");
        }
        else {
            toastMessage("An error occured");
        }

        return result;
    }

    // Loads recipe details (ingredients, directions)
    private void setRecipeDetails() {

        TextView category = getView().findViewById(R.id.labelCategory);
        category.setText(recipe.getCategory());

        TextView article = getView().findViewById(R.id.labelArticle);
        article.setText(recipe.getArticle());
    }

    private void toastMessage(String msg){

        Context context = getContext().getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
