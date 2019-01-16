package com.example.sahil.ashish;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by sahil on 3/7/2018.
 */

@IgnoreExtraProperties
public class Youtube_object implements Serializable {

 public String link;
    public String video_title;
    public String video_thumbnail;
    public String serial;

    DatabaseReference mDatabase;


    public Youtube_object() {

    }

    public Youtube_object(String link,String video_title,
                          String video_thumbnail,String serial) {
         this.link = link;
          this.video_title=video_title;
          this.video_thumbnail=video_thumbnail;
          this.serial=serial;


        //write a message to the database

    }
    public String getLink(){return link;}
    public String getSerial(){return serial;}
    public String getVideo_title() {
        return video_title;
    }//for image thumbnail

    public String getVideo_thumbnail() {
        return video_thumbnail;
    }//for  product name

}