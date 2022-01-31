package com.example.fiszkapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
//to powinno być MainActivity
public class HomeScreenActivity extends AppCompatActivity {
    TextView labelWelcome;
    Bundle args;
    String username;
    TextView textLevel;
    TextView textExp;
    ProgressBar progressBarExp;
    Button buttonNewFlashcard;
    Button buttonCollections;
    Button buttonBadges;
    Button buttonLessonHistory;
    Button buttonStartLesson;
    TextView textLastLesson;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_activity);
        labelWelcome = (TextView) findViewById(R.id.labelWelcome);
        textLevel = (TextView)findViewById(R.id.textLevel);
        textExp = (TextView)findViewById(R.id.textExp);
        textLastLesson = (TextView)findViewById(R.id.textLastLesson);
        progressBarExp = (ProgressBar)findViewById(R.id.progressBarExp);
        buttonNewFlashcard = (Button)findViewById(R.id.buttonNewFlashcard);
        buttonCollections = (Button)findViewById(R.id.buttonCollections);
        buttonBadges = (Button)findViewById(R.id.buttonBadges);
        buttonLessonHistory = (Button)findViewById(R.id.buttonLessonHoistory);
        buttonStartLesson = (Button)findViewById(R.id.buttonStartLesson);
        args = getIntent().getExtras();
        username = args.getString("username");
        labelWelcome.setText(username);

        //weź dane użytkownika
        DBHelper dbHelper = new DBHelper(this);
        Cursor cursor = dbHelper.getAllData("user");
        cursor.moveToNext();
        Cursor lastLesson = dbHelper.getLastLesson();
        if(lastLesson.getCount()!=0)
        {
            lastLesson.moveToNext();
            String lastLessonDate = lastLesson.getString(2);
            textLastLesson.setText(lastLessonDate);
        }
        else
        {
            textLastLesson.setText("Nie przeprowadzono jeszcze lekcji!");
        }
        //ustawienie poziomu na stronie głównej
        int level = cursor.getInt(3);
        int exp = cursor.getInt(2);
        textLevel.setText(String.valueOf(level));
        int percentage = (int)((float)Experience.calculateExpFromCurrentLevel(exp,level)/Experience.calculateNeededExp(level)*100);
        progressBarExp.setProgress(percentage);
        textExp.setText("pozostało: "+ Experience.expToNextLevel(exp, level));

        buttonNewFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewFlashcard = new Intent(HomeScreenActivity.this, NewFlashcardActivity.class);
                startActivityForResult(toNewFlashcard, 0);
            }
        });
        buttonStartLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLesson = new Intent(HomeScreenActivity.this, LessonStartActivity.class);
                startActivityForResult(toLesson, 0);
            }
        });
        buttonCollections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCollections = new Intent(HomeScreenActivity.this, CollectionsActivity.class);
                startActivityForResult(toCollections, 0);
            }
        });
        buttonLessonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toLessonHistory = new Intent(HomeScreenActivity.this, LessonHistoryActivity.class);
                startActivityForResult(toLessonHistory, 0);
            }
        });
        buttonBadges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBadges = new Intent(HomeScreenActivity.this,BadgesActivity.class);
                startActivityForResult(toBadges,0);
            }
        });
    }
}
