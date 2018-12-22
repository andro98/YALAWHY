package com.example.andrew.yalahwy.DataAccess;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.example.andrew.yalahwy.Activity.LoginAct;
import com.example.andrew.yalahwy.Activity.ProfileAct;
import com.example.andrew.yalahwy.Activity.RegisterAct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthDataAccess {

    private FirebaseAuth mAuth;

    public AuthDataAccess(){
        mAuth = FirebaseAuth.getInstance();
    }

    public void checkSignInInfo(final String Email , final String PSW,final LoginAct loginAct){

        mAuth.signInWithEmailAndPassword(Email, PSW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    loginAct.sendToMain();
                } else {
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(loginAct, "Error : " + errorMessage, Toast.LENGTH_LONG).show();
                }

                loginAct.getLoginProgress().setVisibility(View.INVISIBLE);

            }
        });

    }

    public void checkSocialAcc(){

    }
    public void RegisterByNor(final String Email , final String PSW,final RegisterAct registerAct) {

        mAuth.createUserWithEmailAndPassword(Email, PSW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent setupIntent = new Intent(registerAct, ProfileAct.class);
                    ((Activity)registerAct).startActivity(setupIntent);
                    ((Activity)registerAct).finish();

                } else {
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(registerAct, "Error : " + errorMessage, Toast.LENGTH_LONG).show();
                    registerAct.getReg_progress().setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    public void getInfoSocial(){

    }
    public void registerBySocial(){

    }

    public boolean getCurrentUserid(){
        return (mAuth.getCurrentUser() != null);
    }

    public String getUserId(){
        return mAuth.getCurrentUser().getUid();
    }
}
