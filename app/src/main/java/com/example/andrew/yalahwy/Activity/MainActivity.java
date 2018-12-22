package com.example.andrew.yalahwy.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.crashlytics.android.Crashlytics;
import com.example.andrew.yalahwy.Entity.Person;
import com.example.andrew.yalahwy.Services.Person_Service;
import com.example.andrew.yalahwy.Services.Post_Service;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addPost;
    private Post_Service post_service;
    private RecyclerView recyclerView;
    private Toolbar mainToolbar;
    private Person_Service person_service;


    private void init(){
        mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("YALAHWY");

        addPost = findViewById(R.id.addNewPost);
        recyclerView = findViewById(R.id.post_recycle);

        post_service = new Post_Service();
        person_service = new Person_Service();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(post_service.getAdap());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);


        init();

        Boolean currentUser = person_service.checkIfLoggedIn();
        if(!currentUser){
            sendToLogin();
        }else{
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddPost.class));
            }
        });

        post_service.showPost(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.profile_main_menu:
                startActivity(new Intent(MainActivity.this, ProfileAct.class));
                return true;
            default:
                finish();
                System.exit(0);
                return false;

        }
    }

    public void sendToLogin() {
        Intent mainIntent = new Intent(MainActivity.this, LoginAct.class);
        startActivity(mainIntent);
        finish();

    }
}
