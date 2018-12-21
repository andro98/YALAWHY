package com.example.andrew.yalahwy.DataAccess;

import com.google.firebase.auth.FirebaseAuth;

public class AuthDataAccess {

    private FirebaseAuth mAuth;

    public AuthDataAccess(){
        mAuth = FirebaseAuth.getInstance();
    }


    public void checkSignInInfo(){

    }
    public void checkSocialAcc(){

    }
    public void RegisterByNor() {

    }
    public void getInfoSocial(){

    }
    public void registerBySocial(){

    }

    public boolean getCurrentUserid(){
        return (mAuth.getCurrentUser() != null);
    }
}
