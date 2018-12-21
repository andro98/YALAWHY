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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class Post_Service {
    PostDataAccess postDataAccess;
    AuthDataAccess mAuth;
    PostRecycle postRecycle;
    List<Post> post_list_data;

    private Bitmap compressedImageFile;

    public Post_Service(){

        postDataAccess = new PostDataAccess();
        mAuth = new AuthDataAccess();

        post_list_data = new ArrayList<>();

        postRecycle = new PostRecycle(post_list_data);
    }

    public PostRecycle getAdap(){return postRecycle;}

    public void showPost(Context context){

        postDataAccess.getPost(context, post_list_data, postRecycle);

    }

    public void ShowResultFromSearch(){

    }

    public void addPost(AddPost addPost){
        Post post = new Post();
        if(addPost.getNewpostImageUri() != null && addPost.getNewpost_desc().getText()!= null &&addPost.getNewpost_reg().getText() != null){
            post.setPostImage(addPost.getNewpostImageUri().toString());
            post.setPostDesc(addPost.getNewpost_desc().getText().toString());
            post.setRegion(addPost.getNewpost_reg().getText().toString());
            if(!post.getPostDesc().isEmpty() && !post.getRegion().isEmpty() && post.getPostImage()!= null){
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
            }else{
                Toast.makeText(addPost.getBaseContext(), "Fill All the Fields Please", Toast.LENGTH_SHORT).show();
                addPost.getNewpost_prog().setVisibility(View.INVISIBLE);
            }
        }else{
            Toast.makeText(addPost.getBaseContext(), "Fill All the Fields Please", Toast.LENGTH_SHORT).show();
            addPost.getNewpost_prog().setVisibility(View.INVISIBLE);
        }



    }

    public void addPost( Context context, TextView newpost_desc, TextView newpost_reg, Uri newpostImageUri){
        /*if(mAuth.getCurrentUserid() == false){
            Toast.makeText(context, "Register first", Toast.LENGTH_SHORT).show();
        }else{
*/
        Post post = new Post();

        final String desc = newpost_desc.getText().toString();
        final String reg = newpost_reg.getText().toString();

        post.setPostImage(newpostImageUri.toString());
        post.setPostDesc(desc);
        post.setRegion(reg);

        if(!post.getPostDesc().isEmpty() && !post.getRegion().isEmpty() && post.getPostImage()!= null){
            File newImageFile = new File(Uri.parse(post.getPostImage()).getPath());
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
      //  }



    }

    private void getUserInfoForPost(){

    }

    public void updatePost(){

    }
}
