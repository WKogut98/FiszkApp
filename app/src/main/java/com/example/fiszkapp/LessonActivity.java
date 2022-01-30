package com.example.fiszkapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LessonActivity extends AppCompatActivity {

    Bundle bundle; //dane z poprzedniej akt
    String front;
    String back;
    DBHelper helper;
    int currentIndex=0; //indeks do iterowania po fiszkach
    Button buttonNext;
    Button buttonEndLesson;
    TextView textTimer;
    ContentValues values;
    SimpleDateFormat format; //format daty
    long timeLeft; //ile czasu zostalo
    boolean isLearning; //przegladamy czy robimy zadania
    int lessonId;
    public List<Boolean> answerList; //lista odpowiedzi: true - poprawna, false-zła
    private CountDownTimer timer; //zegar odmierzajacy czas do końca lekcji
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        String colName= bundle.getString("collectionName");
        helper=new DBHelper(this);
        List<FlashCard> flashCards = helper.getFlashcardsInCollection(colName);
        answerList=new ArrayList<>();
        int numberOfFlashcards = flashCards.size();

        front = flashCards.get(currentIndex).getFront();
        back = flashCards.get(currentIndex).getBack();
        setContentView(R.layout.activity_lesson);
        buttonNext = findViewById(R.id.buttonNext);
        buttonEndLesson = findViewById(R.id.buttonEndLesson);
        buttonEndLesson.setBackgroundColor(Color.RED);
        textTimer=findViewById(R.id.textTimer);
        timeLeft=60 * 1000;
        isLearning = true;

        values=new ContentValues();
        format = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
        String start=format.format(new Date());
        values.put("STARTED", start);
        helper.insertData("Lesson", values); //tworzymy nowa lekcje
        lessonId = getActiveLessonId(start);
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
                if (isLearning && currentIndex < numberOfFlashcards) {
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
                } else {
                    if(isLearning)
                    {
                        currentIndex = 0;
                        isLearning=8==3;//lmao
                        resetTimer();
                    }
                    //przejdź do ćwiczenia
                    if(currentIndex < numberOfFlashcards)
                    {
                        front = flashCards.get(currentIndex).getFront();
                        back = flashCards.get(currentIndex).getBack();
                        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainerView);
                        if (fragment != null) {
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }
                        String word="";
                        String answer="";
                        boolean isReversed = false;
                        if(flipACoin()) //losujemy czy pytamy o awers czy rewers
                        {
                            word=front;
                            answer=back;
                        }
                        else {
                            word = back;
                            answer = front;
                            isReversed=true;
                        }
                        if(flipACoin())
                        {
                            fragmentManager.beginTransaction().
                                    setReorderingAllowed(true).
                                    add(R.id.fragmentContainerView, Excercise1Fragment.newInstance(word, answer, isReversed,
                                            colName), null)
                                    .commit();
                        }
                        else
                        {
                            fragmentManager.beginTransaction().
                                    setReorderingAllowed(true).
                                    add(R.id.fragmentContainerView, Excercise2Fragment.newInstance(word, answer), null)
                                    .commit();
                        }
                    }
                    else {
                        int wrongAnswers=getWrongAnswersNumber();
                        int rightAnswers=answerList.size()-wrongAnswers;
                        String end=format.format(new Date());
                        values=new ContentValues();
                        values.put("ENDED", end);
                        values.put("TOTAL_QUESTIONS", numberOfFlashcards);
                        values.put("ANSWERS_CORRECT", rightAnswers);
                        values.put("GAINED_EXP", getGainedXP());
                        helper.updateData(String.valueOf(lessonId), "Lesson", values);
                        if(wrongAnswers==0)
                        {
                            helper.unlockBadge("Mądra głowa");
                        }
                        Toast.makeText(LessonActivity.this, "Lekcja ukończona", Toast.LENGTH_SHORT).show();
                        helper.unlockBadge("Głodny wiedzy");
                        Cursor user=helper.getAllData("User");
                        user.moveToNext();
                        int userId=user.getInt(0);
                        int totalExp=user.getInt(2);
                        int level=user.getInt(3);
                        totalExp=Experience.addExp(totalExp, getGainedXP());
                        ContentValues cv=new ContentValues();
                        cv.put("EXPERIENCE", totalExp);
                        if(totalExp>=5000)
                        {
                            helper.unlockBadge("Empiryk");
                        }
                        if(totalExp>=Experience.totalExpToGetLevel(level+1))
                        {
                            level++;
                            if(level==2)
                            {
                                helper.unlockBadge("O, to tu są levele?!");
                            }
                            if(level==10)
                            {
                                helper.unlockBadge("Zahartowany w boju");
                            }
                            if(level==100)
                            {
                                helper.unlockBadge("Lvl 100 BOSS");
                            }
                            cv.put("LEVEL", level);
                        }
                        helper.updateData(String.valueOf(userId), "User", cv);
                        finish();
                    }
                }
            }
        });
        buttonEndLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.unlockBadge("Rage quit");
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
                //przejdź do ćwiczenia

                //i zresetuj timer
                resetTimer();
            }
        };
        timer.start();
    }
    public void stopTimer()
    {
        timer.cancel();
    }
    public void resetTimer()//reset timera
    {
        timer.cancel();
        timer.start();
    }
    public void updateTimerText()//uaktualnianie timera co sekundę
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
    private boolean flipACoin()
    {
        return (int)Math.round(Math.random()) == 0;
    }
    private int getWrongAnswersNumber() //ile złych odp
    {
        int result=0;
        for(Boolean a:answerList)
        {
            if(!a)
            {
                result++;
            }
        }
        return result;
    }
    //pobranie punktów exp
    private int getGainedXP()
    {
        int rightAnswers=answerList.size()-getWrongAnswersNumber();
        int xp=200*rightAnswers;
        if(!answerList.contains(false))
        {
            xp+=300; //bonus za brak złych odpowiedzi
        }
        return xp;
    }
    //pobranie id lekcji
    private int getActiveLessonId(String started)
    {
        Cursor c=helper.getElementFromAttribute("Lesson",
                "Started", started, true);
        c.moveToNext();
        return c.getInt(0);
    }
}