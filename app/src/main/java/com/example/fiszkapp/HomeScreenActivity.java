package com.example.fiszkapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreenActivity extends AppCompatActivity {
    TextView labelWelcome;
    Bundle args;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_activity);
        labelWelcome = (TextView) findViewById(R.id.labelWelcome);
        args = getIntent().getExtras();
        username = args.getString("username");
        labelWelcome.setText("Witaj "+username+" !");
    }
}
