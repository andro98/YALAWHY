package com.example.andrew.yalahwy.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.andrew.yalahwy.DataAccess.AuthDataAccess;
import com.example.andrew.yalahwy.Services.Person_Service;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAct extends AppCompatActivity {

    private EditText loginEmailText;
    private EditText loginPassText;
    private Button loginBtn;
    private Button loginRegBtn;

    private Person_Service person_service;

    private ProgressBar loginProgress;

    private void init(){
        loginEmailText = findViewById(R.id.reg_email);
        loginPassText = findViewById(R.id.reg_confirm_pass);
        loginBtn = findViewById(R.id.login_btn);
        loginRegBtn = findViewById(R.id.login_reg_btn);
        loginProgress = findViewById(R.id.login_progress);

        person_service = new Person_Service();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();


        loginRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent regIntent = new Intent(LoginAct.this, RegisterAct.class);
                startActivity(regIntent);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String loginEmail = loginEmailText.getText().toString();
                final String loginPass = loginPassText.getText().toString();

                person_service.SignIn(loginEmail, loginPass,LoginAct.this);

            }
        });


    }

    public ProgressBar getLoginProgress() {
        return loginProgress;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Boolean currentUser = person_service.checkIfLoggedIn();
        if(currentUser){
            sendToMain();
        }
    }

    public void sendToMain() {

        Intent mainIntent = new Intent(LoginAct.this, MainActivity.class);
        startActivity(mainIntent);
        finish();

    }
}
