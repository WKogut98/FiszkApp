package com.example.fiszkapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeScreenActivity extends AppCompatActivity {
    TextView labelWelcome = (TextView) findViewById(R.id.labelWelcome);
    Bundle args = getIntent().getExtras();
    final String username = args.getString("username");
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_activity);
        labelWelcome.setText("Witaj "+username+" !");
    }
}
