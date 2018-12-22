package com.example.andrew.yalahwy.DataAccess;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.andrew.yalahwy.Activity.MainActivity;
import com.example.andrew.yalahwy.Activity.ProfileAct;
import com.example.andrew.yalahwy.Adapter.PostRecycle;
import com.example.andrew.yalahwy.Entity.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.Task;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class UserDataAccess {

    StorageReference Storageref;
    private FirebaseFirestore firebaseFirestore;

    public UserDataAccess(){

        Storageref = FirebaseStorage.getInstance().getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
    }

    public void ShowProf(final String user_id, final ProfileAct profileAct )
    {
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
         @Override
         public void onComplete(@NonNull Task<DocumentSnapshot> task) {
             if(task.isSuccessful())
             {
                 if(task.getResult().exists())
                 {
                   String Fname=task.getResult().getString("FName");
                   String LName=task.getResult().getString("LName");
                   String ContactNo=task.getResult().getString("ContactNo");
                   String Address=task.getResult().getString("Address");
                   String Image=task.getResult().getString("Image");
                   Uri ImageUri=Uri.parse(Image);
                   Glide.with(profileAct).load(ImageUri).into(profileAct.getProfileImg());
                   profileAct.getContactNo().setText(ContactNo);
                   profileAct.getFname().setText(Fname);
                   profileAct.getLname().setText(LName);
                   profileAct.getAddress().setText(Address);


                 }else{
                    Toast.makeText(profileAct,"Data Doesn't exists",Toast.LENGTH_LONG).show();
                 }
             }else
             {

             }
             profileAct.getProfile_btn().setEnabled(true);
             profileAct.getProfile_progress().setVisibility(View.INVISIBLE);
         }
     });

 }

    public void SaveInfo(){

    }

    public void SaveProfileInfo(final Person person, final String user_id,final ProfileAct profileAct)
    {

        //String User_id=myAuth.getCurrentUser().getUid();
        StorageReference imagePath= Storageref.child("Profile Image").child(user_id+"jpg");
        imagePath.putFile(person.getMainImageURI()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Uri download_uri = task.getResult().getDownloadUrl();
                    Map<String,String> Usermap=new HashMap<>();
                    Usermap.put("FName",person.getFirstName());
                    Usermap.put("LName",person.getLastName());
                    Usermap.put("ContactNo",person.getPhoneNumber());
                    Usermap.put("Address",person.getAddress());
                    Usermap.put("Image",download_uri.toString());
                   firebaseFirestore.collection("Users").document(user_id).set(Usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful())
                           {
                               Toast.makeText(profileAct,"The User Setting Is Updated",Toast.LENGTH_LONG).show();
                               Intent mainIntent=new Intent(profileAct, MainActivity.class);
                               ((Activity)profileAct).startActivity(mainIntent);
                               ((Activity)profileAct).finish();
                           }else
                           {
                              String error=task.getException().getMessage();
                              Toast.makeText(profileAct,"FireStore Error: "+error,Toast.LENGTH_LONG).show();
                           }

                       }
                   });

                } else {
                    String error = task.getException().getMessage();

                }
            }
        });
    }

    public void getUserInfo(String user_id, final PostRecycle.ViewHolder postRecycle){
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {
                    if(task.getResult().exists())
                    {
                        Person person = new Person();
                        person.setFirstName(task.getResult().getString("FName"));
                        person.setLastName(task.getResult().getString("LName"));
                        person.setPhoneNumber(task.getResult().getString("ContactNo"));
                        person.setAddress(task.getResult().getString("Address"));
                        person.setImageUri(task.getResult().getString("Image"));
                        postRecycle.setProfile_name(person.getFirstName());
                        postRecycle.setProfile_image(person.getImageUri());

                    }else{
                    }
                }else
                {

                }
            }
        });
    }
    public void updateUser(){

    }
    public void deleteUser(){

    }


}
