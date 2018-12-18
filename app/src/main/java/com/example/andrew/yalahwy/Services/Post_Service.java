package com.example.andrew.yalahwy.Services;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.example.andrew.yalahwy.Activity.AddPost;
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
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class Post_Service {
    PostDataAccess postDataAccess;
    private Bitmap compressedImageFile;

    public Post_Service(){
      postDataAccess = new PostDataAccess();
    }

    public void showPost(){

    }

    public void ShowResultFromSearch(){

    }

    public void addPost(Post post, Context context){

        if(!post.getPostDesc().isEmpty() && !post.getRegion().isEmpty() && post.getPostImage()!= null){
            File newImageFile = new File(post.getPostImage().getPath());
            try {

                compressedImageFile = new Compressor(context)
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

            postDataAccess.addPost(post, context, imageData);
        }else{
            Toast.makeText(context, "Fill All the Fields Please", Toast.LENGTH_SHORT).show();
        }

    }

    private void getUserInfoForPost(){

    }

    public void updatePost(){

    }
}
