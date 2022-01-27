package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LessonActivity extends AppCompatActivity {

    Bundle bundle;
    String front;
    String back;
    DBHelper helper;
    int currentIndex=0;
    Button buttonNext;
    Button buttonEndLesson;
    TextView textTimer;
    long timeLeft;
    private CountDownTimer timer; //zegar odmierzajacy czas do końca lekcji
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
        buttonEndLesson = findViewById(R.id.buttonEndLesson);
        buttonEndLesson.setBackgroundColor(Color.RED);
        textTimer=findViewById(R.id.textTimer);

        timeLeft=60000;

        startTimer();

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
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
                fragmentManager.beginTransaction().
                        setReorderingAllowed(true).
                        add(R.id.fragmentContainerView, CardFragment.newInstance(front, back), null)
                        .commit();
            }
        });
        buttonEndLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!helper.isBadgeUnlocked("Rage quit"))
                {
                    Cursor badge =helper.getElementFromAttribute("badges", "name","Rage quit");
                    badge.moveToNext();
                    ContentValues values=new ContentValues();
                    values.put(DBHelper.cols_badge[4],1);
                    helper.updateData(""+badge.getInt(0), "badge", values);
                }
                finish();
            }
        });
    }
    public void startTimer()
    {
        timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }
    public void stopTimer()
    {
        timer.cancel();
    }
    public void updateTimerText()
    {
        int minutes = (int) timeLeft/60000;
        int seconds = (int) timeLeft % 60000/1000;
        String secStr=""+seconds;
        if(seconds<10)
        {
            secStr="0"+seconds;
        }
        String timerText="Czas: " + minutes + ":" + secStr;
        textTimer.setText(timerText);
    }
}