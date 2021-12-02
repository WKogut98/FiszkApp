package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class NewFlashcardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flashcard);

        DBHelper helpMe = new DBHelper(this);
        Cursor cursor = helpMe.getAllData(DBHelper.table_names[2]);
        ArrayList<String> collectionNames = new ArrayList<>();
        ArrayList<Integer> idList = new ArrayList<>();
        while(cursor.moveToNext())
        {
            collectionNames.add(cursor.getString(1));
            idList.add(cursor.getInt(0));
        } if(collectionNames.isEmpty())
        {
            Toast.makeText(this, "Przed dodaniem fiszki dodaj kolekcję do której będą dodawane fiszki", Toast.LENGTH_LONG).show();
            Intent toAddCollecion = new Intent(NewFlashcardActivity.this, CollectionsActivity.class);
            startActivity(toAddCollecion);
            finish();
        }
    }
}