package com.example.sahil.ashish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sahil on 3/5/2018.
 */


public class fblogin  extends AppCompatActivity {
    JSONObject response, profile_pic_data, profile_pic_url;
    Intent tutor,student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("userProfile");
        Log.w("Jsondata", jsondata);
        //  TextView user_name = (TextView) findViewById(R.id.textViewName);


        //   TextView user_email = (TextView) findViewById(R.id.textViewEmail);
/*
        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.customaction, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Buyer/Seller..?");
*/
    /* CircleImageView imageButton = (CircleImageView) findViewById(R.id.imageButton);


        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Signout success!",
                        Toast.LENGTH_LONG).show();
                LoginManager.getInstance().logOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });*/

        //  mActionBar.setCustomView(mCustomView);
        //    mActionBar.setDisplayShowCustomEnabled(true);
        try {
            response = new JSONObject(jsondata);

            tutor=new Intent(fblogin.this,tutoractivity.class);
            tutor.putExtra("name",response.get("name").toString());
            tutor.putExtra("email",response.get("email").toString());
            tutor.putExtra("type",intent.getStringExtra("type"));
            student=new Intent(fblogin.this,studentactivity.class);
            student.putExtra("name",response.get("name").toString());
            student.putExtra("email",response.get("email").toString());
            student.putExtra("type",intent.getStringExtra("type"));
            profile_pic_data = new JSONObject(response.get("picture").toString());
            profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
           /* Glide.with(this)     //profile
                    .load(profile_pic_url.getString("url"))
                    .into(imageButton);*/
            tutor.putExtra("pic", profile_pic_url.getString("url"));
            student.putExtra("pic", profile_pic_url.getString("url"));
            //use glide for photo use
        } catch(Exception e){
            e.printStackTrace();
        }
        String type=intent.getStringExtra("type").trim();
        if(type.equals("Tutor"))
            tutoractivity();
        else if(type.equals("Student"))
            studentactivity();


    }
    public void studentactivity( ) {
        startActivity(student);
    }

    public void tutoractivity( ) {
        startActivity(tutor);
    }
}