package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SelectCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_collection);

        //do adaptera trzeba tablicę Stringów
        DBHelper helpMe = new DBHelper(this);
        Cursor cursor = helpMe.getAllData(DBHelper.table_names[1]);
        ArrayList<String> collectionNames = new ArrayList<>();
        while(cursor.moveToNext())
        {
            collectionNames.add(cursor.getString(1));
        }
        if(collectionNames.isEmpty())
        {
            //przejdź do aktywności dodania kolekcji
            //finish();
        }
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_select_collection, collectionNames);
        ListView listView = (ListView)findViewById(R.id.collectionList);
        listView.setAdapter(adapter);
    }
}