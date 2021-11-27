package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_collection);

        //do adaptera trzeba listę Stringów
        DBHelper helpMe = new DBHelper(this);
        Cursor cursor = helpMe.getAllData(DBHelper.table_names[2]);
        ArrayList<String> collectionNames = new ArrayList<>();
        ArrayList<Integer> idList = new ArrayList<>();
        while(cursor.moveToNext())
        {
            collectionNames.add(cursor.getString(1));
            idList.add(cursor.getInt(0));
        }
        if(collectionNames.isEmpty())
        {
            Toast.makeText(this, "Przed dodaniem fiszki dodaj kolekcję do której będą dodawane fiszki", Toast.LENGTH_LONG).show();
            Intent toAddCollecion = new Intent(SelectCollectionActivity.this, CollectionsActivity.class);
            startActivity(toAddCollecion);
            finish();
        }
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_select_collection, collectionNames);
        ListView listView = (ListView)findViewById(R.id.collectionList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toAddFlashcard = new Intent(SelectCollectionActivity.this, NewFlashcardActivity.class);
            }
        });
    }
}