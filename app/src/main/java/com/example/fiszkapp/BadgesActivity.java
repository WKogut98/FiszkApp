package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

public class BadgesActivity extends AppCompatActivity {

    DBHelper helper;
    private BadgeAdapter adapterLocked;
    private BadgeAdapter adapterUnlocked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges);
        helper=new DBHelper(this);

        Cursor cursorUnlocked=helper.getElementFromAttribute("Badge", "IS_UNLOCKED", "1");
        Cursor cursorLocked=helper.getElementFromAttribute("Badge", "IS_UNLOCKED", "0");

        adapterLocked = new BadgeAdapter(this, cursorLocked);
        adapterUnlocked = new BadgeAdapter(this, cursorUnlocked);

        RecyclerView recyclerViewUnlocked=(RecyclerView) findViewById(R.id.RecyclerViewUnlocked);
        RecyclerView recyclerViewLocked=(RecyclerView) findViewById(R.id.RecyclerViewLocked);
        recyclerViewUnlocked.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLocked.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewLocked.setAdapter(adapterLocked);
        recyclerViewUnlocked.setAdapter(adapterUnlocked);
    }
}