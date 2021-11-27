package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CollectionsActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    Button buttonAddCollection;
    EditText editTextCollectionName;
    Spinner spinnerLanguageFront;
    Spinner spinnerLanguageBack;
    String languageFront;
    String languageBack;
    DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        helper = new DBHelper(this);
        mDatabase=helper.getWritableDatabase();
        Cursor cursor=helper.getAllData("Collection");
        buttonAddCollection =(Button) findViewById(R.id.buttonAddCollection);
        String[] languageItemArray = helper.getAllItemNamesAsArray("Language");
        spinnerLanguageFront=(Spinner) findViewById(R.id.spinnerLanguageFront);
        spinnerLanguageBack=(Spinner) findViewById(R.id.spinnerLanguageBack);
        ArrayAdapter<CharSequence> spinnerAdapter1 =
                new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, languageItemArray);
                //ArrayAdapter.createFromResource(this, languageItemArray, android.R.layout.simple_spinner_item);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> spinnerAdapter2 =
                new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, languageItemArray);
                //ArrayAdapter.createFromResource(this, languageItemArray, android.R.layout.simple_spinner_item);
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguageFront.setAdapter(spinnerAdapter1);
        spinnerLanguageBack.setAdapter(spinnerAdapter2);
        spinnerLanguageFront.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                languageFront=parent.getItemAtPosition(position).toString();
                if(languageFront.equals(languageItemArray[1]))
                {
                    Intent toLanguage = new Intent(CollectionsActivity.this, LanguageActivity.class);
                    startActivityForResult(toLanguage, 0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerLanguageBack.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                languageBack=parent.getItemAtPosition(position).toString();
                if(languageBack.equals(languageItemArray[1]))
                {
                    Intent toLanguage = new Intent(CollectionsActivity.this, LanguageActivity.class);
                    startActivityForResult(toLanguage, 0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buttonAddCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!languageFront.equals(languageItemArray[0]) && !languageBack.equals(languageItemArray[0])
                        && !languageFront.equals(languageItemArray[1]) && !languageBack.equals(languageItemArray[1])) {
                    ContentValues contentValues = new ContentValues();
                    //public final static String[] cols_collection={"ID", "NAME", "LANGUAGE_ID_FRONT","LANGUAGE_ID_BACK"};
                    String name = editTextCollectionName.getText().toString();
                    contentValues.put(DBHelper.cols_collection[1], name);
                    Cursor cf = helper.getElementFromAttribute("Language", "NAME", languageFront);
                    cf.moveToNext();
                    Cursor cb = helper.getElementFromAttribute("Language", "NAME", languageBack);
                    cb.moveToNext();
                    int languageFrontID = cf.getInt(0);
                    int languageBackID = cb.getInt(0);
                    contentValues.put(DBHelper.cols_collection[2], languageFrontID);
                    contentValues.put(DBHelper.cols_collection[3], languageBackID);
                    boolean isInserted = helper.insertData(DBHelper.table_names[2], contentValues);
                    if (isInserted) {
                        Toast.makeText(CollectionsActivity.this, "Dodano nową kolekcję", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(CollectionsActivity.this, "Nie udało się dodać", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(CollectionsActivity.this, "Wybierz języki do awersu lub rewersu", Toast.LENGTH_LONG).show();
                }
            }
        });
        RecyclerView recyclerViewCollections = (RecyclerView) findViewById(R.id.recyclerViewCollections);
        recyclerViewCollections.setLayoutManager(new LinearLayoutManager(this));
    }
}