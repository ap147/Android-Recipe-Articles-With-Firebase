package com.parmar.amarjot.android_recipe_book_with_firebase;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class fragment_local_recipe_list extends Fragment {

    ListView list;

    String[] recipe_title;
    String [] recipe_description;
    Integer [] recipe_image_id;

    private RecipeSQLiteDatabaseHelper localDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local_recipe_list, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        localDB = new RecipeSQLiteDatabaseHelper(getContext(), "localRecipes");
        int amountOfRecipes = localDB.getRecipeCount();
        recipe_title = new String[amountOfRecipes];
        recipe_description = new String[amountOfRecipes];
        recipe_image_id = new Integer[amountOfRecipes];

        pullDataFromDB();
        setupList();
    }

    protected void pullDataFromDB() {
        Cursor data = localDB.getRecipes();

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

    protected void setupList () {

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

    protected void displayRecipeDetails(int position) {
        Intent intent = new Intent(getActivity(), DisplayRecipe.class);
        Bundle recipe_details = new Bundle();
        recipe_details.putString(getString(R.string.pass_recipe_title), recipe_title[position]);
        recipe_details.putString("recipe_type", "online");
        recipe_details.putInt(getString(R.string.pass_recipe_image), recipe_image_id[position]);
        intent.putExtras(recipe_details);
        startActivity(intent);
        getActivity().overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right);
    }
}
