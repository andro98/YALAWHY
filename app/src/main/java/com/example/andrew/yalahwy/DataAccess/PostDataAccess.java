package com.example.andrew.yalahwy.DataAccess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.andrew.yalahwy.Activity.AddPost;
import com.example.andrew.yalahwy.Activity.MainActivity;
import com.example.andrew.yalahwy.Adapter.PostRecycle;
import com.example.andrew.yalahwy.Entity.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class PostDataAccess {
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    private Bitmap compressedImageFile;

    public PostDataAccess() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void addPost(final Post post, final Context context, byte[] imageData){

        final String randomName = UUID.randomUUID().toString();
        UploadTask filePath = storageReference.child("post_images").child(randomName + ".jpg").putBytes(imageData);
        filePath.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    final String downloadUri = task.getResult().getDownloadUrl().toString();

                    File newThumbFile = new File(Uri.parse(post.getPostImage()).getPath());
                    try {

                        compressedImageFile = new Compressor(context)
                                .setMaxHeight(100)
                                .setMaxWidth(100)
                                .setQuality(1)
                                .compressToBitmap(newThumbFile);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] thumbData = baos.toByteArray();

                    UploadTask uploadTask = storageReference.child("post_images/thumbs")
                            .child(randomName + ".jpg").putBytes(thumbData);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String downloadthumbUri = taskSnapshot.getDownloadUrl().toString();
                            Map<String, Object> postMap = new HashMap<>();
                            postMap.put("imageThumb", downloadthumbUri);
                            postMap.put("postImage", downloadUri);
                            postMap.put("postDesc", post.getPostDesc());
                            postMap.put("region", post.getRegion());
                            postMap.put("userID", post.getUserID());
                            postMap.put("timestamp", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("Posts").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
                                        //context.startActivity(new Intent(context, context.getClass()));
                                        ((MainActivity)context).finish();
                                    }else{
                                        Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //Error handling
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }else{
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void deletePost(){

    }
    public void updatePost(){

    }


    public void getPost(Context context, final List<Post> post_list_data, final PostRecycle postRecycle){
        Query firstQuery = firebaseFirestore.collection("Posts").orderBy("timestamp", Query.Direction.DESCENDING);
        firstQuery.addSnapshotListener((Activity) context, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                    if (doc.getType() == DocumentChange.Type.ADDED) {

                        Post post=  doc.getDocument().toObject(Post.class);
                        post_list_data.add(post);
                        postRecycle.notifyDataSetChanged();

                    }
                }
            }
        });

    }


}
