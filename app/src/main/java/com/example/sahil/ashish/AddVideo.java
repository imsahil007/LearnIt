package com.example.sahil.ashish;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AddVideo extends AppCompatActivity  {
    String KEY_ID;
    DatabaseReference mDatabase;EditText edit,videotitle;
    ArrayList<Youtube_object> videos;
    ListView lv;
    ProgressDialog progressDialog;
    Spinner category;Youtube_object video;
    String type, category_toShow;int reload;
int total_videos=0;Course course;

    ListView list;
    YoutubeAdapter adapter;
    public void addNewVideo(final String link, final String title
                                ) {
final String youtube_key=link.substring(32);
        final String[] serial = new String[1];
        final String video_key = mDatabase.push().getKey();
        final DatabaseReference dr= FirebaseDatabase.getInstance().getReference("courses");
       dr.child(KEY_ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                course = snapshot.getValue(Course.class);
                dr.child(KEY_ID).removeValue();
                course.setTotal_videos();
                serial[0] =course.getTotal_videos();




                dr.child(KEY_ID).setValue(course);

                //prints "Do you have data? You'll love Firebase."
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    //    total_videos=FirebaseDatabase.getInstance().getReference("courses").child(KEY_ID).child("total_videos");
              //  String thumbnail="http://i3.ytimg.com/vi/"+link.trim()+"/maxresdefault.jpg";

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                String thumbnail = "http://img.youtube.com/vi/" + youtube_key + "/mqdefault.jpg";
                video = new Youtube_object(youtube_key, title, thumbnail, serial[0]);
                edit.setText("");
                videotitle.setText("");
                mDatabase.child(video_key).setValue(video);
                Toast.makeText(getApplicationContext(), "Video added" , Toast.LENGTH_SHORT).show();
Intent back=new Intent(AddVideo.this,Youtubelist.class);
back.putExtra("KEY_ID",KEY_ID);
                back.putExtra("type",type);
                startActivity(back);

                //     progressDialog.dismiss();
            }
        }, 300);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvideo);
        Intent intent=getIntent();
       // total_videos=Integer.parseInt(intent.getStringExtra("total_videos"));

        KEY_ID=intent.getStringExtra("KEY_ID");

        //FirebaseDatabase.getInstance().getReference("courses").child(KEY_ID).child("total_videos").setValue(total_videos+1);
        mDatabase =FirebaseDatabase.getInstance().getReference("videos/"+KEY_ID);//.child(KEY_ID);
        Button insert=(Button)findViewById(R.id.insertinfirebase);
        edit=(EditText)findViewById(R.id.linkwillcomehere) ;
       videotitle=(EditText)findViewById(R.id.videotitlewillcomehere) ;
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit.getText().toString().trim().length()!=0)
                    addNewVideo(edit.getText().toString().trim(),videotitle.getText().toString().trim());

            }
        });



    }


   }
