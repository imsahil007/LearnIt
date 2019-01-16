package com.example.sahil.ashish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sahil on 3/5/2018.
 */

public class googlelogin extends AppCompatActivity {
    FirebaseAuth mAuth;
    Intent tutor,student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        TextView textName, textEmail;

        mAuth = FirebaseAuth.getInstance();

        //   textName = findViewById(R.id.textViewName);
        // textEmail = findViewById(R.id.textViewEmail);


        FirebaseUser user = mAuth.getCurrentUser();

        tutor=new Intent(googlelogin.this,tutoractivity.class);
        student=new Intent(googlelogin.this,studentactivity.class);


       try{     student.putExtra("name",user.getDisplayName());
                tutor.putExtra("name",user.getDisplayName());
                tutor.putExtra("email",user.getEmail());
                tutor.putExtra("type",intent.getStringExtra("type"));}
                catch (Exception E){
                            student.putExtra("name",intent.getStringExtra("email"));
                            tutor.putExtra("name",intent.getStringExtra("email"));
                            tutor.putExtra("email",intent.getStringExtra("email"));
                            tutor.putExtra("type",intent.getStringExtra("email"));}
        student.putExtra("email",user.getEmail());
        student.putExtra("type",intent.getStringExtra("type"));
     /*  */
        //  textName.setText(user.getDisplayName());
        //  textEmail.setText(user.getEmail());
       /* android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.customaction, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Buyer/Seller..?");*/
      //  CircleImageView imageButton = (CircleImageView) findViewById(R.id.imageButton);
        try{
            tutor.putExtra("pic",user.getPhotoUrl().toString());
            student.putExtra("pic",user.getPhotoUrl().toString());
         /*   Glide.with(this)     //profile
                    .load(user.getPhotoUrl().toString())
                    .into(imageButton);*/

        }catch(NullPointerException ex){  tutor.putExtra("pic","https://firebasestorage.googleapis.com/v0/b/febpsk.appspot.com/o/iljpswcuqhdrsmctkfpo.png?alt=media&token=5f497980-7a93-4761-b837-ae324d814520");
            student.putExtra("pic","https://firebasestorage.googleapis.com/v0/b/febpsk.appspot.com/o/iljpswcuqhdrsmctkfpo.png?alt=media&token=5f497980-7a93-4761-b837-ae324d814520");
           /*/ Glide.with(this)     //profile
                    .load("https://firebasestorage.googleapis.com/v0/b/febpsk.appspot.com/o/iljpswcuqhdrsmctkfpo.png?alt=media&token=5f497980-7a93-4761-b837-ae324d814520")
                    .into(imageButton);*/
        }







       /* imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Signout success!",
                        Toast.LENGTH_LONG).show();
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));

            }
        });

        //       mActionBar.setCustomView(mCustomView);
        //     mActionBar.setDisplayShowCustomEnabled(true);
*/

        String type=intent.getStringExtra("type").trim();
        if(type.equals("Tutor"))
            tutoractivity();
        else if(type.equals("Student"))
            studentactivity();
    }

    @Override
    protected void onStart(){
        super.onStart();

        //if the user is not logged in
        //opening the login activity
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, googlelogin.class));
        }
    }


    public void tutoractivity() {
        startActivity(tutor);
    }

    public void studentactivity() {
        startActivity(student);
    }
}
