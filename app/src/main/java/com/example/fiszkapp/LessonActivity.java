package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class LessonActivity extends AppCompatActivity {

    String front="Piotr";
    String back="Kopniak";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        //utworzenie menedżera fragmentu, żeby wstawić do niego dany fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().
                setReorderingAllowed(true).
                add(R.id.fragmentContainerView, CardFragment.newInstance(front, back), null)
                .commit();
    }
}