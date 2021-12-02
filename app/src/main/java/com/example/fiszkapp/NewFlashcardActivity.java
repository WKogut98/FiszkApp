package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewFlashcardActivity extends AppCompatActivity {

    Spinner spinnerCollections;
    Button addFlashcardButton;
    String selectedCollection;
    EditText frontText;
    EditText backText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flashcard);
        //definicje komponentów w aktywności
        spinnerCollections=(Spinner) findViewById(R.id.spinnerCollections);
        addFlashcardButton=(Button)findViewById(R.id.addFlashcardButton);
        frontText=(EditText)findViewById(R.id.inputFront);
        backText=(EditText)findViewById(R.id.inputBack);

        DBHelper helpMe = new DBHelper(this);
        String [] kolekcje = helpMe.getAllItemNamesAsArray(DBHelper.table_names[2]); //Collection
        //dodanie kolekcji do spinnera
        ArrayAdapter<CharSequence> spinnerAdapter =
                new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, kolekcje);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCollections.setAdapter(spinnerAdapter);

        //dodanie słuchacza przy wybraniu 'Dodaj'
        spinnerCollections.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCollection = parent.getItemAtPosition(position).toString();
                if(selectedCollection.equals(kolekcje[1])) //jak bierzemy opcję "dodaj"
                {//to przenosimy się do aktywności dodawania kolekcji
                    Intent toLanguage = new Intent(NewFlashcardActivity.this, CollectionsActivity.class);
                    startActivityForResult(toLanguage, 0);
                    finish();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){}
        });

        //dodanie słuchacza przycisku dodawania fiszki
        addFlashcardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String front = frontText.getText().toString();
                String back = backText.getText().toString();
                if
                (
                    !selectedCollection.equals(kolekcje[0]) && //jeżeli nie wybrano "--Wybierz opcję--"
                    !selectedCollection.equals(kolekcje[1]) && //ani "Dodaj"
                    !front.trim().isEmpty() &&//ani gdy pole frontu jest puste
                    !back.trim().isEmpty() //ani gdy rewers jest pusty
                )
                {//to możemy dodać fiszkę
                    ContentValues cv = new ContentValues();
                    cv.put(DBHelper.cols_flashcard[1],front); //word_front
                    cv.put(DBHelper.cols_flashcard[2],back); //word_back
                    //trzeba znaleźć id kolekcji do której będziemy dodawać fiszkę
                    Cursor cur = helpMe.getElementFromAttribute("Collection","NAME",selectedCollection,true);
                    cur.moveToNext();
                    int id = cur.getInt(0);
                    cv.put(DBHelper.cols_flashcard[4],id); //COLLECTION_ID
                    //dodajemy do DB
                    boolean isInserted = helpMe.insertData(DBHelper.table_names[1], cv); //Flashcard
                    if (isInserted) {
                        Toast.makeText(NewFlashcardActivity.this, "Dodano nową fiszkę", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(NewFlashcardActivity.this, "Nie udało się dodać fiszki", Toast.LENGTH_LONG).show();
                    }
                    //czyścimy pola formularza
                    frontText.setText("");
                    backText.setText("");
                }
                else
                {
                    Toast.makeText(NewFlashcardActivity.this, "Nie uzupełnono wszystkich pól", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}