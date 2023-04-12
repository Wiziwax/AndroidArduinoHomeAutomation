package com.example.chestnut6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {

    LinearLayout MainsActivity;
    LinearLayout SecurityActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

        MainsActivity = findViewById(R.id.start);
        SecurityActivity = findViewById(R.id.start2);

        MainsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });



        SecurityActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SecurityActivity.class);
                startActivity(intent);

            }
        });

    }



}