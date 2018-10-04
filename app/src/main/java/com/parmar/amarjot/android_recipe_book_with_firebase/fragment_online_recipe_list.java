package com.parmar.amarjot.android_recipe_book_with_firebase;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fragment_online_recipe_list extends Fragment {

    ListView list;

    String[] recipe_title;
    String [] recipe_description;
    Integer [] recipe_image_id;

    private RecipeSQLiteDatabaseHelper onlineDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_online_recipe_list, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        onlineDB = new RecipeSQLiteDatabaseHelper(getContext(), "onlineRecipes");
        recipe_title = new String[6];
        recipe_description = new String[6];
        recipe_image_id = new Integer[6];

        pullDataFromFirebase();
    }

    protected void setupList () {

        loadArrays();

        list= getView().findViewById(R.id.listView);
        CustomListview customListview = new CustomListview(getContext(), recipe_title, recipe_description, recipe_image_id);
        list.setAdapter(customListview);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayRecipeDetails(i);
            }
        });
    }

    protected void pullDataFromFirebase() {

        onlineDB.clearDatabase();
        // Get a reference to our posts
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("recipes");
        // Attach a listener to read the data at our posts reference
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren())
                {
                    // How to parse JSON : //http://theoryapp.com/parse-json-in-java/
                    JSONObject obj;
                    try {
                        obj = new JSONObject(singleSnapshot.getValue().toString());

                        System.out.println("- JSON OBJECT : " + obj.toString());

                        String recipeName = obj.getString("name");
                        String recipeDescription = obj.getString("name");
                        String recipeCategory = obj.getString("category");
                        String recipeIngredients = obj.getString("name");
                        String recipeDirections = obj.getString("name");
                        String recipeImageID = obj.getString("imageID");

                        Recipe recipe = new Recipe(recipeName, recipeDescription, recipeCategory, recipeIngredients, recipeDirections, recipeImageID);
                        onlineDB.addRecipe(recipe);
                        setupList();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    protected void displayRecipeDetails(int position) {
        Intent intent = new Intent(getActivity(), DisplayRecipe.class);
        Bundle recipe_details = new Bundle();
        recipe_details.putString(getString(R.string.pass_recipe_title), recipe_title[position]);
        recipe_details.putInt(getString(R.string.pass_recipe_image), recipe_image_id[position]);
        intent.putExtras(recipe_details);
        startActivity(intent);
        getActivity().overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right);
    }

    protected void loadArrays () {
        Cursor data = onlineDB.getRecipes();

        int count = 0;
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            recipe_title[count] = data.getString(1);
            recipe_description[count] = data.getString(2);
            recipe_image_id[count] = Integer.parseInt(data.getString(6));
            System.out.println(recipe_title[count]);
            count++;
        }
    }
}