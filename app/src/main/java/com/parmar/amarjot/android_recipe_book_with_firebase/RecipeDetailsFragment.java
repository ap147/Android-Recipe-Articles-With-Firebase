package com.parmar.amarjot.android_recipe_book_with_firebase;

import android.content.Context;
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

        setRecipeDetails();
        setupSaveButton();
    }

    private void setupSaveButton() {

        final Button saveRecipe = getView().findViewById(R.id.buttonSaveRecipe);

        saveRecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveRecipe();
            }
        });
    }

    private void saveRecipe() {
        Bundle bundle = getActivity().getIntent().getExtras();
        String recipe_title = bundle.getString(getString(R.string.pass_recipe_title));

        toastMessage(recipe_title);

        // Make database query and save results into local DB.
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
