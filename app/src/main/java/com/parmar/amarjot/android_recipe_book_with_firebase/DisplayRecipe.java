package com.parmar.amarjot.android_recipe_book_with_firebase;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class DisplayRecipe extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);

        setupActionbar();
        setupFragment();

    }

    public void setupFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction recipe_details = fragmentManager.beginTransaction();
        Fragment details = new RecipeDetailsFragment();

        recipe_details.replace(R.id.fragmentRecipeDetails, details);
        recipe_details.commit();
    }

    private void setupActionbar() {

        Bundle bundle = getIntent().getExtras();
        String recipe_title = bundle.getString(getString(R.string.pass_recipe_title));

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle(recipe_title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
