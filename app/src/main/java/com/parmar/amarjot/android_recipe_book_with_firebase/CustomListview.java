package com.parmar.amarjot.android_recipe_book_with_firebase;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.mikhaellopez.circularimageview.CircularImageView;

public class CustomListview extends ArrayAdapter <String>{

    private String [] recipe_title;
    private String [] recipe_description;
    private Integer [] recipe_image_id;

    private Activity context;

    public CustomListview(@NonNull Context context, String [] breakfastname, String [] breakfastdescription, Integer [] breakfastid) {
        super(context, R.layout.custom_listview_layout, breakfastname);

        this.context= (Activity) context;
        this.recipe_title=breakfastname;
        this.recipe_description=breakfastdescription;
        this.recipe_image_id=breakfastid;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View r = convertView;
        ViewHolder viewHolder;
        if (r == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.custom_listview_layout, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) r.getTag();
        }

        viewHolder.textViewName.setText(recipe_title[position]);
        viewHolder.textViewDescription.setText(recipe_description[position]);
        viewHolder.imageView.setImageResource(recipe_image_id[position]);

        return r;
    }

    class ViewHolder {
        TextView textViewName;
        TextView textViewDescription;
        CircularImageView imageView;
        ViewHolder (View v) {
            textViewName = v.findViewById(R.id.textTitle);
            textViewDescription = v.findViewById(R.id.textDescription);
            imageView = v.findViewById(R.id.imageView);
            textViewName.setTextSize(20);
            textViewDescription.setTextSize(16);
        }
    }
}