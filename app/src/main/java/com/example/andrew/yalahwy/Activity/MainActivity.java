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
import android.widget.Toast;

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

    String name_location;
    String name_time;
    String name_type;
    boolean fromSearch;
    private int requestCode;

    private void init(){
        mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("YALAHWY");

        addPost = findViewById(R.id.addNewPost);
        recyclerView = findViewById(R.id.post_recycle);

        post_service = new Post_Service();
        person_service = new Person_Service();

        fromSearch = false;

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


        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(fromSearch) {
            post_service.showPost(this, name_location, name_time, name_type);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(post_service.getAdap());
        }else{
            post_service.showPost(this, name_location, name_time, name_type);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(post_service.getAdap());
        }
        fromSearch = false;

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
            case R.id.app_bar_search:
                go_to_search();
                return true;
            case R.id.profile_main_menu:
                startActivity(new Intent(MainActivity.this, ProfileAct.class));
                return true;
            default:
                return false;

        }
    }

    public void go_to_search() {
        Intent inten=new Intent(MainActivity.this,search.class);
        startActivityForResult(inten, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == requestCode) {
            if (resultCode == RESULT_OK) {
                name_location = data.getStringExtra("location");
                name_time = data.getStringExtra("time");
                name_type = data.getStringExtra("type");
                fromSearch = data.getBooleanExtra("fromSearch", false);
            }
        }
    }


    public void sendToLogin() {
        Intent mainIntent = new Intent(MainActivity.this, LoginAct.class);
        startActivity(mainIntent);
        finish();

    }
}
