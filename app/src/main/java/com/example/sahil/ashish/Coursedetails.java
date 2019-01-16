package com.example.sahil.ashish;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by sahil on 3/8/2018.
 */

public class Coursedetails extends AppCompatActivity {
Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_course_details);
         final Intent i=getIntent();
        TextView course_name, course_description,course_duration,name_email,course_category;
        ImageView  course_uri=(ImageView)findViewById(R.id.imageView2);
        course_name=(TextView)findViewById(R.id.course_name);
        course_description=(TextView)findViewById(R.id.course_description);
        course_duration=(TextView)findViewById(R.id.course_duration);
        name_email=(TextView)findViewById(R.id.name_email);

        course_category=(TextView)findViewById(R.id.course_category);
        RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.simpleRatingBar); // initiate a rating bar
        float rate=Float.parseFloat(i.getStringExtra("rating"));
        final float[] newrate = new float[1];
        newrate[0]=rate;
        simpleRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                newrate[0] =rating;
            }
        });
        newrate[0]=(rate+newrate[0])/2;


        course_name.setText(i.getStringExtra("course_name"));
        Toast.makeText(this,"heiejd"+i.getStringExtra("course_name"),Toast.LENGTH_SHORT).show();
        String d="Description: "+i.getStringExtra("course_description");
        course_description.setText(d);
        d="Course by: "+i.getStringExtra("name")+", "+i.getStringExtra("email");
        name_email.setText(d);
        d="Course duration;(Days): "+i.getStringExtra("course_duration");
        course_duration.setText(d);
        d="Tutor: "+i.getStringExtra("name");
        d="Category: "+i.getStringExtra("course_category");
        course_category.setText(d);
        Glide.with(this)     //profile
                .load(Uri.parse(i.getStringExtra("course_uri")))
                .into(course_uri);
        final Button b=(Button)findViewById(R.id.btn);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final DatabaseReference dr= FirebaseDatabase.getInstance().getReference("courses");
                dr.child( i.getStringExtra("KEY_ID")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        course = snapshot.getValue(Course.class);
                        dr.child( i.getStringExtra("KEY_ID")).removeValue();
                        course.setRating(Float.toString(newrate[0]));
                        course.setEnrolledby(i.getStringExtra("studentemail"));
                        dr.child( i.getStringExtra("KEY_ID")).setValue(course);

                        //prints "Do you have data? You'll love Firebase."
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                Toast.makeText(getApplicationContext(),"Course added",Toast.LENGTH_SHORT).show();
              b.setEnabled(false);
            }
        });
    }
}
