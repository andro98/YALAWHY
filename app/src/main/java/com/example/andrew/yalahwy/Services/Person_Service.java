package com.example.andrew.yalahwy.Services;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.andrew.yalahwy.Activity.ProfileAct;
import com.example.andrew.yalahwy.DataAccess.AuthDataAccess;
import com.example.andrew.yalahwy.DataAccess.UserDataAccess;
import com.example.andrew.yalahwy.Entity.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class Person_Service {
    UserDataAccess userDataAccess;
    AuthDataAccess mAuth;

  public Person_Service(){
      userDataAccess = new UserDataAccess();
      mAuth = new AuthDataAccess();
  }
    public void SignIn(){

    }
    public void SignInWithSocial(){

    }
    public void CheckInfoReg(){

    }
    public void NormalReg(){

    }
    public void SocialReg(){

    }

    public void SaveUserData(ProfileAct profileAct) {
        Person p = new Person();

        if (profileAct.getFname().getText() != null && profileAct.getLname().getText() != null && profileAct.getAddress().getText() != null && profileAct.getContactNo() != null && profileAct.getMainImageuri() != null) {
            p.setFirstName(profileAct.getFname().getText().toString());
            p.setLastName(profileAct.getLname().getText().toString());
            p.setAddress(profileAct.getAddress().getText().toString());
            p.setMainImageURI(profileAct.getMainImageuri());
            p.setPhoneNumber(profileAct.getContactNo().getText().toString());
            if (mAuth.getCurrentUserid()) {
                String User_id = mAuth.getUserId();
                userDataAccess.SaveProfileInfo(p, User_id, profileAct);
            }

        /*if(p.getFirstName()!=null&&p.getLastName()!=null&&p.getPhoneNumber()!=null&&p.getAddress()!=null)
        {
         /* String User_id=myAuth.getCurrentUser().getUid();
          StorageReference imagePath= Storageref.child("Profile Image").child(User_id+"jpg");
          imagePath.putFile(p.getMainImageURI()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
              @Override
              public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                  if(task.isSuccessful())
                  {
                      Uri download_uri=task.getResult().getDownloadUrl();
                     // Toast.makeText(Person_Service.this,"the Image is uploaded",Toast.LENGTH_LONG).show();

                  }else
                  {
                      String error =task.getException().getMessage();
                    // Toast.makeText(Person_Service.this,"Error : "+error,Toast.LENGTH_LONG).show();
                  }

              }
          })


         }
        }*/

        }
    }

    public void getProfileImage(ProfileAct profileAct){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if(ContextCompat.checkSelfPermission(profileAct, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(profileAct,"Permission denied",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(profileAct,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }else
            {
               BringImagePicker(profileAct);

            }
        }else
        {
            BringImagePicker(profileAct);
        }
    }

    public void ShowProfile(ProfileAct profileAct){
      //if(mAuth.getCurrentUserid())
        Person p;

       userDataAccess.ShowProf(mAuth.getUserId(),profileAct);

    }

    public void updateProfile(){

    }
    public void BringImagePicker(ProfileAct profileAct)
    {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(profileAct);
    }
}
