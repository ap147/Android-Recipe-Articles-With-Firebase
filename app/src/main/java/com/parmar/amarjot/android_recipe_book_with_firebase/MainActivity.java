package com.parmar.amarjot.android_recipe_book_with_firebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            // Creating fragmentA and placing it on left side
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    System.out.println("Home Button Clicked");
                    fragment_local_recipe_list localFragment = new fragment_local_recipe_list();
                    fragmentTransaction.replace(R.id.fragmentLayout_main, localFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    System.out.println("Online Button Clicked");
                    fragment_online_recipe_list onlineFragment = new fragment_online_recipe_list();
                    fragmentTransaction.replace(R.id.fragmentLayout_main, onlineFragment);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleIncomingRecipe(intent); // Handle text being sent
            }
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment_local_recipe_list fragment = new fragment_local_recipe_list();
        fragmentTransaction.add(R.id.fragmentLayout_main, fragment);
        fragmentTransaction.commit();
    }

    private void handleIncomingRecipe(Intent intent) {
        String recipeData = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (recipeData != null) {

            String data [] = recipeData.split("#");

            if(data.length == 4)
            {
                String recipeName = data[0];
                String recipeDescription = data[1];
                String recipeCategory= data[2];
                String recipeArticle = data[3];
                String recipeImageID= "2131165277";

                Recipe newRecipe = new Recipe(recipeName, recipeDescription, recipeCategory, recipeArticle, recipeImageID);

                RecipeSQLiteDatabaseHelper localDB = new RecipeSQLiteDatabaseHelper(this, "localRecipes");
                localDB.addRecipe(newRecipe);
            }
            else {
                toastMessage("Unable to add recipe.");
            }

        }
    }

    private void toastMessage(String msg){

        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
