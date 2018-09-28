package com.parmar.amarjot.android_recipe_book_with_firebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment_local_recipe_list fragment = new fragment_local_recipe_list();
        fragmentTransaction.add(R.id.fragmentLayout_main, fragment);
        fragmentTransaction.commit();
    }
}
