package com.example.sahil.ashish;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Youtubelist extends AppCompatActivity {
String KEY_ID;
    DatabaseReference mDatabase;
    ArrayList<Youtube_object> videos;
    ListView lv;
    ProgressDialog progressDialog;
    Spinner category;
   int reload;



    ListView list;
    YoutubeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_youtubelist);
        final Intent newvideo=new Intent(Youtubelist.this,AddVideo.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        adapter=new YoutubeAdapter(this,videos);

        super.onCreate(savedInstanceState);

        final Intent intent=getIntent();
        KEY_ID=intent.getStringExtra("KEY_ID");
        String type=intent.getStringExtra("type");


        FloatingActionButton add=(FloatingActionButton)findViewById(R.id.addvideo);
        try{
        if(type.equals("Tutor")) {
            add.show();


            newvideo.putExtra("KEY_ID", KEY_ID);
            final String[] i = new String[1];
            FirebaseDatabase.getInstance().getReference("courses").child(KEY_ID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    // TODO: handle the post
                    Course course = snapshot.getValue(Course.class);
                    i[0] = course.getTotal_videos();
                    newvideo.putExtra("total_videos", i[0]);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

        else
            add.hide();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(newvideo);
            }
        });

        }catch (Exception e){}
        mDatabase = FirebaseDatabase.getInstance().getReference("videos/"+KEY_ID);

        videos=new ArrayList<>();temp();

        //adapter=new YoutubeAdapter(getApplicationContext(),videos);
        lv = (ListView)findViewById(R.id.list);
        temp();
        adapter=new YoutubeAdapter(this,videos);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //  adapter=new ProductAdapter(getApplicationContext(),products);

                lv.setAdapter(adapter); progressDialog.dismiss();
                //     progressDialog.dismiss();
            }
        }, 600);
        try {
            if (type.equals("Student")) {
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Youtube_object video = (Youtube_object) parent.getItemAtPosition(position);
                        Intent i = new Intent(Youtubelist.this, YoutubePlayer.class);
                        i.putExtra("link", video.getLink());
                        startActivity(i);
                    }
                });
            }
        }catch (Exception E){}
        temp();




    }
    private void temp() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                videos.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Youtube_object video = postSnapshot.getValue(Youtube_object.class);

                    //  Toast.makeText(getApplicationContext(),price,Toast.LENGTH_SHORT).show();

                    videos.add(video);
                    }
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });

    }
}
