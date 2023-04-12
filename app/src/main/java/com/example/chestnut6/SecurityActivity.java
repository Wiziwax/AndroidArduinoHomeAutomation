package com.example.chestnut6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class SecurityActivity extends AppCompatActivity {
    String grl = "http://192.168.80.229";
    String you = "http://youtube.com";
    String urt = "http://google.com";
    Toolbar toolbar;
    WebView webberView;


    Button b1;
    String link;
    WebView webView;
    EditText editText;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        getSupportActionBar().hide();



}
}