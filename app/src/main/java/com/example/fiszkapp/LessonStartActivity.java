package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class LessonStartActivity extends AppCompatActivity {

    Spinner spinner;
    Button button;
    String selectedCollection;
    final int MIN_FLASHCARD_COUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_start);
        spinner = (Spinner)findViewById(R.id.spinner);
        button = (Button)findViewById(R.id.beginLessonButton);

        DBHelper helpMe = new DBHelper(this);
        String [] kolekcje = helpMe.getAllItemNamesAsArray(DBHelper.table_names[2]); //Collection
        //dodanie kolekcji do spinnera
        ArrayAdapter<CharSequence> spinnerAdapter =
                new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, kolekcje);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCollection = parent.getItemAtPosition(position).toString();
                if(selectedCollection.equals(kolekcje[1])) //jak bierzemy opcję "dodaj"
                {//to przenosimy się do aktywności dodawania kolekcji
                    Intent toLanguage = new Intent(LessonStartActivity.this, CollectionsActivity.class);
                    startActivityForResult(toLanguage, 0);
                    finish();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if
                (
                    !selectedCollection.equals(kolekcje[0]) && //jeżeli nie wybrano "--Wybierz opcję--"
                    !selectedCollection.equals(kolekcje[1])//ani "Dodaj"
                )
                {//wybrano kolekcję
                    int number = helpMe.getNumberOfFlashcardsInCollection(selectedCollection);
                    if(number < MIN_FLASHCARD_COUNT)
                    {
                        int remaining = MIN_FLASHCARD_COUNT - number;
                        Toast.makeText(LessonStartActivity.this, "Za mało fiszek w kolekcji; " +
                                "dodaj jeszcze "+remaining, Toast.LENGTH_LONG).show();
                    }
                    else
                    {

                        Toast.makeText(LessonStartActivity.this, "Powinna się rozpocząć lekcja",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(LessonStartActivity.this, "Wybierz kolekcję",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}