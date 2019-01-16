package com.example.sahil.ashish;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddCourse.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddCourse#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCourse extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //my objects--sahil
    public DatabaseReference mDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;
   // public String KEY_ID ;
    String name,email;
    private Button btnUpload;
    private Button btnChoose;
    public Button btnSave;
    EditText title,descriptin,duration;
    String course_category;
    Spinner category;
    String uri;
    private Uri filePath;
    ImageView imageView;
    private final int PICK_IMAGE_REQUEST = 71;

    private OnFragmentInteractionListener mListener;
    public AddCourse() {
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
    public static AddCourse newInstance(String param1, String param2) {
        AddCourse fragment = new AddCourse();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public void writeNewCourse(    String course_name ,
                                    String course_description ,
                                    String course_duration ,String course_category,
                                    String course_uri ,
                                    String name ,String email) {


        String KEY_ID = mDatabase.push().getKey();
        Course course=new Course(KEY_ID,course_name,course_description,course_duration,course_category,course_uri,name,email,"0",null,"5");

        mDatabase.child(KEY_ID).setValue(course);

        Toast.makeText(getContext(), "Course added", Toast.LENGTH_SHORT).show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_course, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("courses");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        category=(Spinner)v.findViewById(R.id.category);
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course_category=parent.getItemAtPosition(position).toString();
                if(position==0)
                    course_category="Others";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnUpload = (Button) v.findViewById(R.id.btn_upload);
        btnChoose = (Button) v.findViewById(R.id.btn_choose);
        btnSave = (Button) v.findViewById(R.id.btn_save);
        title=(EditText)v.findViewById(R.id.title);
        descriptin=(EditText)v.findViewById(R.id.description);
        duration=(EditText)v.findViewById(R.id.duration);
        imageView=(ImageView)v.findViewById(R.id.image);
        btnSave.setEnabled(false);
        btnSave.setBackgroundColor(Color.GRAY);
        btnUpload.setEnabled(false);
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

       name= getArguments().getString("name");
        email= getArguments().getString("email");

        String d="Hello "+name+",You can add new courses here";
        TextView t1=(TextView)v.findViewById(R.id.t1);
        TextView t2=(TextView)v.findViewById(R.id.t2);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/matura.ttf");
        t1.setTypeface(typeface);
        t2.setTypeface(typeface);
       //changes font
        Toast.makeText(getContext(),d,Toast.LENGTH_SHORT).show();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String course_title = title.getText().toString().trim();
                String course_description = descriptin.getText().toString().trim();
                String course_duration = duration.getText().toString().trim();
                if(course_title.length()!=0)
                    writeNewCourse(course_title, course_description,course_duration,course_category,uri,name ,email);

                title.setText("");
                descriptin.setText("");
                duration.setText("");
                imageView.setImageResource(R.drawable.logo);

            }

        });
        return v;
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            btnUpload.setEnabled(false);
                            uri=taskSnapshot.getMetadata().getDownloadUrl().toString();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                            btnSave.setEnabled(true);
                            btnSave.setBackgroundColor(Color.BLUE);
                            btnChoose.setEnabled(true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        btnUpload.setEnabled(true);
        btnChoose.setEnabled(false);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
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
