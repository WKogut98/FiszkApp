package com.example.fiszkapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    Button buttonLogIn;
    EditText inputUsername;
    DBHelper dbHelper;
    SQLiteDatabase mDatabase;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        Cursor cursor = dbHelper.getAllData("User");
        if(cursor.moveToNext())//sprawdź czy użytkownik został dodany
        {//jeśli tak to przejdź do HomeScreenActivity
            String username = cursor.getString(1);
            Intent toHomescreen = new Intent(MainActivity.this, HomeScreenActivity.class);
            toHomescreen.putExtra("username", username);
            startActivityForResult(toHomescreen, 0);
            finish();
        }
        buttonLogIn = (Button)findViewById(R.id.buttonLogIn);
        inputUsername = (EditText)findViewById(R.id.inputUsername);
        mDatabase = dbHelper.getWritableDatabase();
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //create a new user and go to the 'home screen'
                //public final static String[] cols_user={"ID", "NAME", "EXPERIENCE","LEVEL" };
                ContentValues contentValues=new ContentValues();
                String username=inputUsername.getText().toString();
                contentValues.put(DBHelper.cols_user[1],username);
                contentValues.put(DBHelper.cols_user[2], 0);
                contentValues.put(DBHelper.cols_user[3], 1);
                boolean isInserted = dbHelper.insertData(DBHelper.table_names[0], contentValues);
                if(isInserted)
                {
                    Toast.makeText(MainActivity.this,"Siema!",Toast.LENGTH_SHORT).show();
                    Intent toHomescreen = new Intent(MainActivity.this, HomeScreenActivity.class);
                    toHomescreen.putExtra("username", username);
                    startActivityForResult(toHomescreen, 0);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Wystąpił błąd",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}