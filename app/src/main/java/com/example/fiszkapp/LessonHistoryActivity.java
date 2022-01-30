package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

public class LessonHistoryActivity extends AppCompatActivity {

    DBHelper helper;
    private LessonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_history);
        helper=new DBHelper(this);
        Cursor cursor=helper.getAllData("Lesson");
        adapter = new LessonAdapter(this, cursor);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerViewLessons);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}