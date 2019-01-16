package com.example.sahil.ashish;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CourseAdapter extends ArrayAdapter{
    Context c;
    ArrayList<Course> courses;

    public CourseAdapter(Context c, ArrayList<Course> courses) {
        super(c, R.layout.list_row,courses);
        this.c = c;
        this.courses=courses;
      //  this.products = products;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int i) {
        return courses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view= LayoutInflater.from(c).inflate(R.layout.list_row,viewGroup,false);
        }

        Course s= (Course) this.getItem(i);

        TextView course_name = (TextView)view.findViewById(R.id.course_name); // title
        TextView course_description = (TextView)view.findViewById(R.id.course_description); // artist name
        TextView course_duration = (TextView)view.findViewById(R.id.course_duration); // duration
        TextView course_category = (TextView)view.findViewById(R.id.category);
        ImageView course_uri=(ImageView)view.findViewById(R.id.list_image);
      /*/////// ;*/////
        RatingBar simpleRatingBar = (RatingBar) view.findViewById(R.id.simpleRatingBar);
        simpleRatingBar.setRating(Float.parseFloat(s.getRating()));
        //BIND
        String smh="days";
     course_name.setText(s.getCourse_name().toString());
     course_description.setText(s.getCourse_description().toString());
     course_duration.setText(s.getCourse_duration().toString()+" days");
     course_category.setText("Category: "+s.getCourse_category());
        Glide.with(getContext())     //profile
                .load(s.getCourse_uri())
                .into(course_uri);
        // ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
// Load image, decode it to Bitmap and display Bitmap in ImageView (or any other view
//	which implements ImageAware interface)
      /*  imageLoader.displayImage(s.getImageUri(), img);
// Load image, decode it to Bitmap and return Bitmap to callback
        imageLoader.loadImage(s.getImageUri(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // Do whatever you want with Bitmap
            }
        });
       ////////////*/
        // name.setText("abffba");
        //  price.setText("ajkfa");
//img.setImageResource(R.drawable.buy);
        //img.setImageURI(Uri.parse(s.getImageUri()));

        //img.setImageURI(Uri.parse(s.getImageUri()));


        return view;
    }
}