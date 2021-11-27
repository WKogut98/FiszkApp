package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LanguageActivity extends AppCompatActivity {
    EditText editTextLanguageName;
    Button buttonAddLanguage;
    DBHelper helper;
    SQLiteDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        editTextLanguageName=findViewById(R.id.editTextLanguageName);
        buttonAddLanguage=findViewById(R.id.buttonAddLanguage);
        helper = new DBHelper(this);
        mDatabase=helper.getWritableDatabase();
        buttonAddLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues=new ContentValues();
                String name=editTextLanguageName.getText().toString();
                contentValues.put(DBHelper.cols_language[1], name);
                boolean isInserted = helper.insertData(DBHelper.table_names[4], contentValues);
                if(isInserted)
                {
                    Toast.makeText(LanguageActivity.this,"Dodano nowy język",Toast.LENGTH_LONG).show();
                    LanguageActivity.this.finish();
                }
                else
                {
                    Toast.makeText(LanguageActivity.this,"Nie udało się dodać",Toast.LENGTH_LONG).show();
                    LanguageActivity.this.finish();
                }
            }
        });
    }
}