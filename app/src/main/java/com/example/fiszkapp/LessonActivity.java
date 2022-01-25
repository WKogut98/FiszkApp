package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends AppCompatActivity {

    Bundle bundle;
    String front;
    String back;
    DBHelper helper;
    int currentIndex=0;
    Button buttonNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        String colName= bundle.getString("collectionName");
        helper=new DBHelper(this);
        List<FlashCard> flashCards = helper.getFlashcardsInCollection(colName);
        front = flashCards.get(currentIndex).getFront();
        back = flashCards.get(currentIndex).getBack();
        setContentView(R.layout.activity_lesson);
        buttonNext = findViewById(R.id.buttonNext);
        //utworzenie menedżera fragmentu, żeby wstawić do niego dany fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().
                setReorderingAllowed(true).
                add(R.id.fragmentContainerView, CardFragment.newInstance(front, back), null)
                .commit();
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                front = flashCards.get(currentIndex).getFront();
                back = flashCards.get(currentIndex).getBack();
                Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainerView);
                if(fragment != null)
                {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
                fragmentManager.beginTransaction().
                        setReorderingAllowed(true).
                        add(R.id.fragmentContainerView, CardFragment.newInstance(front, back), null)
                        .commit();
            }
        });
    }
}