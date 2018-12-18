package com.example.andrew.yalahwy.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.andrew.yalahwy.Entity.Post;
import com.example.andrew.yalahwy.Services.Post_Service;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.*;

public class AddPost extends AppCompatActivity {

    private ImageView newpost_img;
    private EditText newpost_desc, newpost_reg;
    private Button newpost_add;
    private android.support.v7.widget.Toolbar newpost_toolbar;
    private ProgressBar newpost_prog;

    private Post_Service post_service;

    private Uri newpostImageUri;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    private void init(){
        newpost_toolbar = findViewById(R.id.newpost_toolbar);
        setSupportActionBar(newpost_toolbar);
        getSupportActionBar().setTitle("Add New Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        post_service = new Post_Service();


        newpost_prog = findViewById(R.id.newpost_prog);
        newpost_prog.setVisibility(View.INVISIBLE);

        newpost_desc = findViewById(R.id.newpost_desc);
        newpost_img = findViewById(R.id.newpost_image);
        newpost_reg = findViewById(R.id.newpost_region);
        newpost_add = findViewById(R.id.addNewPost);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        init();

        newpost_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if(ContextCompat.checkSelfPermission(AddPost.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(AddPost.this, "Permission Denied", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(AddPost.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                    } else {

                        BringImagePicker();

                    }

                } else {

                    BringImagePicker();

                }

            }
        });

        newpost_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost();
            }
        });
    }

    private void addPost(){
        newpost_prog.setVisibility(View.VISIBLE);

        Post post = new Post();

        final String desc = newpost_desc.getText().toString();
        final String reg = newpost_reg.getText().toString();

        post.setPostImage(newpostImageUri);
        post.setPostDesc(desc);
        post.setRegion(reg);
        post.setTimestamp(FieldValue.serverTimestamp().toString());

        post_service.addPost(post, this);
    }

    private void BringImagePicker() {
        // this bring up the image picker so the user choose an image
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(512, 512)
                .setAspectRatio(1, 1)
                .start(AddPost.this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //this function works when user return from image peeker and select his image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                newpostImageUri = result.getUri();
                if(newpostImageUri != null) {
                    newpost_img.setImageURI(newpostImageUri);
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }

    }
}
