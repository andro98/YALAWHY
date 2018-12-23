package com.example.andrew.yalahwy.Activity;

import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.andrew.yalahwy.DataAccess.PostDataAccess;
import com.example.andrew.yalahwy.Services.Post_Service;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class search extends AppCompatActivity {

    //Fireb
    Spinner spinner;
    Spinner spinner_2;
    Spinner spinner_3;
    Button btn;
    //3 string
    String name_location;
    String name_time;
    String name_type;
    boolean fromSearch;


    private Toolbar mainToolbar;

    ArrayAdapter<CharSequence>adapter;
    ArrayAdapter<CharSequence>adapter_2;
    ArrayAdapter<CharSequence>adapter_3;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mainToolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Search");

        spinner=(Spinner) findViewById(R.id.spinner_date);
        spinner_2=(Spinner) findViewById(R.id.spinner_loc);
        spinner_3=(Spinner)findViewById(R.id.spinner_type);

        adapter=ArrayAdapter.createFromResource(this,R.array.search_cat,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        adapter_2=ArrayAdapter.createFromResource(this,R.array.search_cat_loc,android.R.layout.simple_spinner_item);
        adapter_2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_2.setAdapter(adapter_2);

        adapter_3=ArrayAdapter.createFromResource(this,R.array.search_type,android.R.layout.simple_spinner_item);
        adapter_3.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_3.setAdapter(adapter_3);
        /*spinner_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //translate_date(parent.getItemAtPosition(position).toString());
                name_type =  parent.getItemAtPosition(position).toString();
            }
        });*/
        spinner_3.setOnItemSelectedListener(new search.typeOnItemSelectedListener());
        spinner.setOnItemSelectedListener(new search.timeOnSelectedListener());
        spinner_2.setOnItemSelectedListener(new search.locOnSelectedListener());
        /*spinner_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        name_location=parent.getItemAtPosition(position).toString();
            }
        });
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        name_time=parent.getItemAtPosition(position).toString();
            }
        });*/
        btn = findViewById(R.id.button2);

       btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                //return 3 string to main activity 3n tari2 onActivityResult
               Intent intent = getIntent();
               intent.putExtra("type", name_type);
               intent.putExtra("time", name_time);
               intent.putExtra("location", name_location);
               fromSearch = true;
               intent.putExtra("fromSearch", fromSearch);
                setResult(RESULT_OK, intent);
               finish();

           }
       });

    }

    public class typeOnItemSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            name_type = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public class timeOnSelectedListener  implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            name_time = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public class locOnSelectedListener  implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            name_location = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
