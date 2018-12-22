package com.example.andrew.yalahwy.Services;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.andrew.yalahwy.Activity.LoginAct;
import com.example.andrew.yalahwy.Activity.ProfileAct;
import com.example.andrew.yalahwy.Activity.RegisterAct;
import com.example.andrew.yalahwy.DataAccess.AuthDataAccess;
import com.example.andrew.yalahwy.DataAccess.UserDataAccess;
import com.example.andrew.yalahwy.Entity.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
    public void SignIn(final String Email , final String PSW,final LoginAct loginAct){

        if(!TextUtils.isEmpty(Email) && !TextUtils.isEmpty(PSW)){
            loginAct.getLoginProgress().setVisibility(View.VISIBLE);

            mAuth.checkSignInInfo(Email, PSW, loginAct);

        }else{
            Toast.makeText(loginAct, "Please Enter Email or Psw", Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean checkIfLoggedIn(){
      return mAuth.getCurrentUserid();
    }

    public void SignInWithSocial(){

    }

    public void NormalReg(final String Email , final String PSW, final String CPSW ,final RegisterAct registerAct){

        if (!TextUtils.isEmpty(Email) && !TextUtils.isEmpty(PSW) & !TextUtils.isEmpty(CPSW)){
        if(PSW.equals(CPSW)){

            registerAct.getReg_progress().setVisibility(View.VISIBLE);
            mAuth.RegisterByNor(Email,PSW,registerAct);


        }else {
            Toast.makeText(registerAct, "Confirm Password and Password Field doesn't match.", Toast.LENGTH_LONG).show();
            registerAct.getReg_progress().setVisibility(View.INVISIBLE);
            }
        }else{
            Toast.makeText(registerAct, "Please enter Email or Psw.", Toast.LENGTH_LONG).show();
            registerAct.getReg_progress().setVisibility(View.INVISIBLE);
        }
  }
    public void SocialReg(){

    }

    public void SaveUserData(ProfileAct profileAct) {
        Person p = new Person();

        if (profileAct.getFname().getText() != null && profileAct.getLname().getText() != null && profileAct.getAddress().getText() != null && profileAct.getContactNo() != null && profileAct.getMainImageuri() != null) {
            profileAct.getProfile_progress().setVisibility(View.VISIBLE);
            p.setFirstName(profileAct.getFname().getText().toString());
            p.setLastName(profileAct.getLname().getText().toString());
            p.setAddress(profileAct.getAddress().getText().toString());
            p.setMainImageURI(profileAct.getMainImageuri());
            p.setPhoneNumber(profileAct.getContactNo().getText().toString());
            if (mAuth.getCurrentUserid()) {
                String User_id = mAuth.getUserId();
                userDataAccess.SaveProfileInfo(p, User_id, profileAct);
            }
        }
        else{
            Toast.makeText(profileAct, "Please fill data", Toast.LENGTH_SHORT).show();
            profileAct.getProfile_progress().setVisibility(View.INVISIBLE);
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
