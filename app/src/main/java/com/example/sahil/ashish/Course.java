package com.example.sahil.ashish;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by sahil on 3/7/2018.
 */

@IgnoreExtraProperties
public class Course implements Serializable {

    public String key_id;
    public String course_name;
    public String course_description;
    public String course_duration;
    public String course_uri;
    public String name;
    public String email;
    public String course_category;
    public String youtube_link;
    public String total_videos;
    public String enrolledby;
    public String rating;

    DatabaseReference mDatabase;

    private static final String image = "Image";


    public Course() {

    }

    public Course( String key_id,String course_name,
                    String course_description,
                    String course_duration,String course_category,
                    String course_uri,
                    String name, String email,String total_videos,String enrolledby,String rating) {
         this.key_id = key_id;
          this.course_name=course_name;
          this.course_description=course_description;
          this.course_duration=course_duration;
          this.course_uri=course_uri;
          this.name=name;
          this.email=email;
          this.course_category=course_category;
          this.total_videos=total_videos;
          this.enrolledby=enrolledby;
          this.rating=rating;


        //write a message to the database

    }
   // public void setYoutubelink(String youtube_link){this.youtube_link=youtube_link;}
    public String getCourse_uri() {
        return course_uri;
    }//for image thumbnail
public String getTotal_videos(){return total_videos;}
public void setTotal_videos(){
    int i=Integer.parseInt(total_videos);
    i++;
    this.total_videos=Integer.toString(i);}

    /*public void addTotal_videos(){int x=(Integer.parseInt(this.total_videos));
                               x++;
                                this.total_videos=Integer.toString(x);}*/
    public String getKey_id() {
        return key_id;
    }//for  product name
    public String getEnrolledby(){return enrolledby;}
    public String getRating(){return rating;}
    public void setRating(String rating){this.rating=rating;}
    public void setEnrolledby(String newemail){this.enrolledby=(this.enrolledby)+","+newemail;}
    public String getName() {
        return name;
    }//for  product name

    public String getCourse_name() {
        return course_name;
    }

    public String getCourse_description() {
        return course_description;
    }

    public String getCourse_duration() {
        return course_duration;
    }

    public String getEmail() {
        return email;
    }
    public String getYoutube_link(){return youtube_link;}


    public String getCourse_category() {


        return course_category;
    }
}