package com.example.sahil.ashish;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class YoutubeAdapter extends ArrayAdapter{
    Context c;
    ArrayList<Youtube_object> videos;

    public YoutubeAdapter(Context c, ArrayList<Youtube_object> videos) {
        super(c, R.layout.list_row,videos);
        this.c = c;
        this.videos=videos;
      //  this.products = products;
    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int i) {
        return videos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null)
        {
            view= LayoutInflater.from(c).inflate(R.layout.youtube_row,viewGroup,false);
        }
       // int loader = R.drawable.logo;
        Youtube_object s= (Youtube_object) this.getItem(i);

        TextView video_title = (TextView)view.findViewById(R.id.video_title); // title

        final ImageView video_thumbnail=(ImageView)view.findViewById(R.id.list_image);

        video_title.setText(s.getSerial()+". "+s.getVideo_title());
        Glide.with(getContext())     //profile
                .load(s.getVideo_thumbnail())
                .into(video_thumbnail);
        /*herer
        try { URL url= new URL(s.getVideo_thumbnail()); //video_thumbnail.setImageBitmap(bmp);
         //   Toast.makeText(getContext(),"http://img.youtube.com/vi/"+s.getLink()+"/mqdefault.jpg",Toast.LENGTH_SHORT).show();
            final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    //  adapter=new ProductAdapter(getApplicationContext(),products);

                    video_thumbnail.setImageBitmap(bmp);
                    video_thumbnail.setImageURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/ashish-6fcf1.appspot.com/o/images%2F7665e90c-fe4f-4d8b-8a21-72de7780cd1f?alt=media&token=ea0b6ddd-d1d1-413b-9c5a-2cf8e7a8b13c"));
                    //     progressDialog.dismiss();

        } catch (Exception e) {
            e.printStackTrace();Toast.makeText(getContext(),"SAD REAX",Toast.LENGTH_SHORT).show();
        //    video_thumbnail.setImageResource(R.drawable.logo);
                             video_thumbnail.setImageURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/ashish-6fcf1.appspot.com/o/images%2F7665e90c-fe4f-4d8b-8a21-72de7780cd1f?alt=media&token=ea0b6ddd-d1d1-413b-9c5a-2cf8e7a8b13c"));


        }
*/


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