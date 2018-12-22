package com.example.andrew.yalahwy.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.andrew.yalahwy.Entity.Person;
import com.example.andrew.yalahwy.Services.Person_Service;
import com.google.firebase.auth.FirebaseAuth;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileAct extends AppCompatActivity {
     private Toolbar ProfileToolbar;
     private CircleImageView ProfileImg;
     private Uri mainImageuri=null;
     private EditText Fname;
     private EditText Lname;
     private EditText ContactNo;
     private EditText Address;
     private Button Profile_btn;
     private ProgressBar profile_progress;

     private Person_Service serve;
     private Person person;

    private void init()
    {
        serve = new Person_Service();
        person=new Person();
        ProfileToolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(ProfileToolbar);
        getSupportActionBar().setTitle("Profile Setting");
        ProfileImg=findViewById(R.id.Profile_image);
        Fname=findViewById(R.id.Profile_fname);
        Lname=findViewById(R.id.Profile_lname);
        ContactNo=findViewById(R.id.Profile_contactNo);
        Address=findViewById(R.id.Profile_address);
        Profile_btn=findViewById(R.id.Profile_btn);
        profile_progress=findViewById(R.id.Profile_progress);
        profile_progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();

        Profile_btn.setEnabled(false);

        serve.ShowProfile(ProfileAct.this);


        //Profile_btn.setEnabled(true);

        ProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                serve.getProfileImage(ProfileAct.this);

            }
        });

        Profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               serve.SaveUserData(ProfileAct.this);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mainImageuri = result.getUri();
                ProfileImg.setImageURI(mainImageuri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public CircleImageView getProfileImg() {
        return ProfileImg;
    }

    public Uri getMainImageuri() {
        return mainImageuri;
    }

    public EditText getFname() {
        return Fname;
    }

    public EditText getLname() {
        return Lname;
    }

    public EditText getContactNo() {
        return ContactNo;
    }

    public EditText getAddress() {
        return Address;
    }

    public Button getProfile_btn() {
        return Profile_btn;
    }

    public ProgressBar getProfile_progress() {
        return profile_progress;
    }
}
