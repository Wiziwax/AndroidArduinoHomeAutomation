package com.example.chestnut6;

import static com.example.chestnut6.Fragments.SwitchesFragment.createConnectThread;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.chestnut6.Classes.PagerAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    TabLayout tabLayout;
    TabItem mSwitchSlide, mAnalysisSlide, mClassified;
    PagerAdapter pagerAdapter;
    Toolbar mToolbar;
    ImageView imageView;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.backToHomeActivity);
        getSupportActionBar().hide();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });





        mToolbar = findViewById(R.id.toolBar);

        final Button buttonConnect = findViewById(R.id.buttonConnect);

        buttonConnect.setEnabled(true);
        mSwitchSlide = findViewById(R.id.switchSlide);
        mAnalysisSlide = findViewById(R.id.analysisSlide);
        mClassified = findViewById(R.id.classifiedSlide);
        ViewPager viewPager = findViewById(R.id.fragmentContainer);
        tabLayout = findViewById(R.id.include);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 3);
        viewPager.setAdapter(pagerAdapter);


//        mToolbar.setTitle("Home Automation");
//        mToolbar.setTitleMarginStart(5);
//        mToolbar.setTitleMargin(47,0,0,0);
//        mToolbar.setTitleTextColor(-1);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        // Select Bluetooth Device
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Move to adapter list

                Intent intent = new Intent(MainActivity.this, SelectDeviceActivity.class);
                startActivity(intent);


            }
        });


    }


    @Override
    public void onBackPressed() {
        // Terminate Bluetooth Connection and close app
        if (createConnectThread != null) {
            createConnectThread.cancel();
        }
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


}