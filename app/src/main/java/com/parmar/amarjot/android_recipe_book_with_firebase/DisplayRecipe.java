package com.parmar.amarjot.android_recipe_book_with_firebase;

import android.content.pm.ActivityInfo;
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(recipe_title);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_white_24px);
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
