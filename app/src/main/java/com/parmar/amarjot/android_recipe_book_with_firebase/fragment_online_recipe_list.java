package com.parmar.amarjot.android_recipe_book_with_firebase;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class fragment_online_recipe_list extends Fragment {

    ListView list;

    String [] recipe_title, recipe_description;
    Integer [] recipe_image_id;

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

        setupList();
    }

    protected void setupList () {

        loadArray(); // pullDataFromFirebase

        list= getView().findViewById(R.id.listView);
        CustomListview customListview = new CustomListview(getContext(), recipe_title, recipe_description, recipe_image_id);
        list.setAdapter(customListview);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //displayRecipeDetails(i);
            }
        });
    }

    protected void pullDataFromFirebase() {
        // Get Data from Firebase, Store in mysql, Display in list
    }

//    protected void displayRecipeDetails(int position) {
//        Intent intent = new Intent(getActivity(), DisplayMessageActivity.class);
//        Bundle recipe_details = new Bundle();
//        recipe_details.putString(getString(R.string.pass_recipe_title), recipe_title[position]);
//        recipe_details.putInt(getString(R.string.pass_recipe_image), recipe_image_id[position]);
//        intent.putExtras(recipe_details);
//        startActivity(intent);
//        getActivity().overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right);
//    }

    protected void loadArray () {
        recipe_title = new String[]{"hi", "x", "hi", "x", "hi", "x"};
        recipe_description = new String[]{"Hello", "x", "hi", "x", "hi", "x"};
        recipe_image_id = new Integer[]{R.drawable.ic_baseline_arrow_back_24px, R.drawable.ic_home_black_24dp, R.drawable.ic_baseline_arrow_back_24px, R.drawable.ic_home_black_24dp, R.drawable.ic_baseline_arrow_back_24px, R.drawable.ic_home_black_24dp};
    }
}