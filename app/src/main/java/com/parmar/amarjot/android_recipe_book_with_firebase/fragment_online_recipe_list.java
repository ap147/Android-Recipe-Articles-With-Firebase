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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.json.JSONObject;

public class fragment_online_recipe_list extends Fragment {

    // Holds recipes fetched from temp local db.
    ListView list;
    String currentFilter;

    // Used by custom list to display recipes
    String[] recipe_title, recipe_description;
    Integer [] recipe_image_id;

    // Used to temporarily hold recipes retrieved from firebase
    private RecipeSQLiteDatabaseHelper onlineDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_online_recipe_list, container, false);
    }

    // Sets up temp local database
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        onlineDB = new RecipeSQLiteDatabaseHelper(getContext(), getString(R.string.online_db));
        MainActivity activity = (MainActivity) getActivity();
        currentFilter = activity.getCurrentFilter();
        pullDataFromFirebase();
    }

    // Creates a list full of recipes
    protected void setupList (String filter) {

        loadArrays(filter);

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

    // Fetches recipe article data from firebase and stores in a temp local db
    protected void pullDataFromFirebase() {

        onlineDB.clearDatabase();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference(getString(R.string.firebase_db));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren())
                {
                    JSONObject obj;
                    try {
                        obj = new JSONObject(singleSnapshot.getValue().toString());

                        String recipeName = obj.getString(getString(R.string.firebase_recipe_name));
                        String recipeDescription = obj.getString(getString(R.string.firebase_recipe_description));
                        String recipeCategory = obj.getString(getString(R.string.firebase_recipe_category));
                        String recipeArticle = ((MainActivity)getActivity()).getArticle(obj.getString(getString(R.string.firebase_recipe_article)));
                        String recipeImageID = obj.getString(getString(R.string.firebase_recipe_imageID));

                        Recipe recipe = new Recipe(recipeName, recipeDescription, recipeCategory, recipeArticle, recipeImageID, "false");
                        onlineDB.addRecipe(recipe);
                        setupList(currentFilter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getCode());
            }
        });
    }

    // Takes recipes from temp local database and stores it in arrays
    protected void loadArrays(String filter) {
        Cursor data = onlineDB.getRecipes(filter);

        int amountOfRecipes = data.getCount();
        recipe_title = new String[amountOfRecipes];
        recipe_description = new String[amountOfRecipes];
        recipe_image_id = new Integer[amountOfRecipes];

        int count = 0;
        while (data.moveToNext()) {
            recipe_title[count] = data.getString(1);
            recipe_description[count] = ((MainActivity)getActivity()).getDescription(data.getString(2));
            recipe_image_id[count] = ((MainActivity)getActivity()).getImageID(data.getString(5));
            count++;
        }
    }

    // When user clicks on a recipe, takes them to a new activity, displaying more information
    // about recipe
    protected void displayRecipeDetails(int position) {
        Intent intent = new Intent(getActivity(), DisplayRecipe.class);
        Bundle recipe_details = new Bundle();
        recipe_details.putString(getString(R.string.pass_recipe_title), recipe_title[position]);
        recipe_details.putString(getString(R.string.pass_recipe_type), getString(R.string.recipe_type_online) );

        recipe_details.putInt(getString(R.string.pass_recipe_image), recipe_image_id[position]);
        intent.putExtras(recipe_details);
        startActivity(intent);
        getActivity().overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right);
    }
}