package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
{
    Button buttonLogIn;
    EditText inputUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLogIn = (Button)findViewById(R.id.buttonLogIn);
        inputUsername = (EditText)findViewById(R.id.inputUsername);
    }
}