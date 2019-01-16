package com.example.sahil.ashish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Courselist.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Courselist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Courselist extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String email;
    // TODO: Rename aString emailnd change types of parameters
    private String mParam1;
    private String mParam2;
//my parameters-sahil
DatabaseReference mDatabase;
    ArrayList<Course> courses;
ListView lv;
    ProgressDialog progressDialog;
    Spinner category;
    String category_toShow;int reload;


    ListView list;
    CourseAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public Courselist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCourse.
     */
    // TODO: Rename and change types and number of parameters
    public static Courselist newInstance(String param1, String param2) {
        Courselist fragment = new Courselist();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category_toShow="Unknown";
         if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_course_list, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        adapter=new CourseAdapter(getContext(),courses);


      /*  progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.show();*/

        category=(Spinner)v.findViewById(R.id.category);
        lv = (ListView)v.findViewById(R.id.list);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_toShow=parent.getItemAtPosition(position).toString().trim();
                if(position==0)
                    category_toShow="All";
              temp();
                adapter=new CourseAdapter(getApplicationContext(),courses);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        //  adapter=new ProductAdapter(getApplicationContext(),products);

                        lv.setAdapter(adapter); progressDialog.dismiss();
                        //     progressDialog.dismiss();
                    }
                }, 600);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
String type= getArguments().getString("type");
email=getArguments().getString("email");
if(type.equals("Student")){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course course = (Course) parent.getItemAtPosition(position);
               Intent intent = new Intent(getContext(), Coursedetails.class);

                intent.putExtra("studentemail", email);
                intent.putExtra("rating",course.getRating());
                intent.putExtra("name", course.getName().toString().trim());
                intent.putExtra("course_description", course.getCourse_description().toString().trim());
                intent.putExtra("email", course.getEmail().toString().trim());
                intent.putExtra("course_name", course.getCourse_name().toString().trim());
                intent.putExtra("course_duration", course.getCourse_duration().toString().trim());
                intent.putExtra("course_uri", course.getCourse_uri().toString().trim());
                intent.putExtra("course_category", course.getCourse_category().toString().trim());
                intent.putExtra("KEY_ID", course.getKey_id().toString().trim());
                startActivity(intent);

            }
        });}


temp();
        return v;
    }

    private void temp() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                courses.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Course product = postSnapshot.getValue(Course.class);

                    //  Toast.makeText(getApplicationContext(),price,Toast.LENGTH_SHORT).show();

                    if (category_toShow.equals(product.getCourse_category()))
                    {  courses.add(product);}
                    else if(category_toShow.equals("All")){
                        courses.add(product);
                    }
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        reload=2;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        mDatabase = FirebaseDatabase.getInstance().getReference("courses");
        courses=new ArrayList<>();temp();


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
