package com.example.andrew.yalahwy.Services;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrew.yalahwy.Activity.AddPost;
import com.example.andrew.yalahwy.Activity.MainActivity;
import com.example.andrew.yalahwy.Adapter.PostRecycle;
import com.example.andrew.yalahwy.DataAccess.AuthDataAccess;
import com.example.andrew.yalahwy.DataAccess.PostDataAccess;
import com.example.andrew.yalahwy.Entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class Post_Service {
    PostDataAccess postDataAccess;
    AuthDataAccess mAuth;
    PostRecycle postRecycle;
    List<Post> post_list_data;
    Timestamp mytime;

    private Bitmap compressedImageFile;

    public Post_Service(){

        postDataAccess = new PostDataAccess();
        mAuth = new AuthDataAccess();

        post_list_data = new ArrayList<>();


    }

    public PostRecycle getAdap(){return postRecycle;}

    public void showPost(Context context, String loc, String time, String type){
        if(mAuth.getCurrentUserid()){
            post_list_data.clear();
            postRecycle = new PostRecycle(post_list_data);
            if(loc == null && time == null && type == null){
                postDataAccess.getPost(context, post_list_data, postRecycle);
            }else{
                mytime = translate_date(time);
                postDataAccess.getPost(context, post_list_data, postRecycle, loc, type, mytime);
            }
        }

    }

    public void ShowResultFromSearch(){

    }

    public void addPost(AddPost addPost){
        if(mAuth.getCurrentUserid()) {
            Post post = new Post();
            if (addPost.getNewpostImageUri() != null && addPost.getNewpost_desc().getText() != null && addPost.getNewpost_reg().getText() != null) {
                post.setPostImage(addPost.getNewpostImageUri().toString());
                post.setPostDesc(addPost.getNewpost_desc().getText().toString());
                post.setRegion(addPost.getNewpost_reg().getText().toString());
                post.setUserID(mAuth.getUserId());
                if (!post.getPostDesc().isEmpty() && !post.getRegion().isEmpty() && post.getPostImage() != null) {
                    File newImageFile = new File(Uri.parse(post.getPostImage()).getPath());
                    try {

                        compressedImageFile = new Compressor(addPost.getBaseContext())
                                .setMaxHeight(720)
                                .setMaxWidth(720)
                                .setQuality(50)
                                .compressToBitmap(newImageFile);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageData = baos.toByteArray();

                    postDataAccess.addPost(post, addPost.getBaseContext(), imageData);
                } else {
                    Toast.makeText(addPost.getBaseContext(), "Fill All the Fields Please", Toast.LENGTH_SHORT).show();
                    addPost.getNewpost_prog().setVisibility(View.INVISIBLE);
                }
            } else {
                Toast.makeText(addPost.getBaseContext(), "Fill All the Fields Please", Toast.LENGTH_SHORT).show();
                addPost.getNewpost_prog().setVisibility(View.INVISIBLE);
            }
        }else {
            ((Activity)addPost).finish();
        }

    }

    private void getUserInfoForPost(){

    }

    public void updatePost(){

    }

    public Timestamp translate_date(String date) {
        switch (date) {
            case "today": {

                Calendar cal = Calendar.getInstance();
                //cal.add(cal.DATE,-1);
                Timestamp ts = new Timestamp(cal.getTime().getTime());
                return ts;
            }
            case "yasterday": {
                Calendar cal = Calendar.getInstance();
                cal.add(cal.DATE, -1);
                Timestamp ts = new Timestamp(cal.getTime().getTime());
                return ts;
            }
            case "this week": {
                Calendar cal = Calendar.getInstance();
                cal.add(cal.DATE, -7);
                Timestamp ts = new Timestamp(cal.getTime().getTime());
                return ts;
            }
            case "last week": {
                Calendar cal = Calendar.getInstance();
                cal.add(cal.DATE, -14);
                Timestamp ts = new Timestamp(cal.getTime().getTime());
                return ts;
            }
            case "this month": {
                Calendar cal = Calendar.getInstance();
                cal.add(cal.DATE, -30);
                Timestamp ts = new Timestamp(cal.getTime().getTime());
                return ts;
            }
            default:
                return null;
        }

    }
}
