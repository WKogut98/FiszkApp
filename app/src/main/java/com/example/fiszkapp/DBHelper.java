package com.example.fiszkapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    public final static String DB_NAME="FiszkApp.db";
    public final static String[] table_names={"User", "Flashcard", "Collection","Lesson", "Language", "Badge"};
    public final static String[] cols_user={"ID", "NAME", "EXPERIENCE","LEVEL" };
    public final static String[] cols_flashcard={"ID", "WORD_FRONT", "WORD_BACK","PRIORITY", "COLLECTION_ID"};
    public final static String[] cols_collection={"ID", "NAME", "LANGUAGE_ID_FRONT","LANGUAGE_ID_BACK"};
    public final static String[] cols_lesson={"ID", "STARTED", "ENDED", "TOTAL_QUESTIONS","ANSWERS_CORRECT", "GAINED_EXP"};
    public final static String[] cols_language={"ID", "NAME"};
    public final static String[] cols_badge={"ID", "NAME", "DESCRIPTION", "IS_UNLOCKED"};

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table if not exists "+table_names[0]+" ("+cols_user[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +cols_user[1]+" TEXT,"+cols_user[2]+" NUMBER"+cols_user[3]+", NUMBER)"); //utworzenie tabeli user

        db.execSQL("create table if not exists "+table_names[1]+" ("+cols_flashcard[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +cols_flashcard[1]+" TEXT,"+cols_flashcard[2]+" TEXT"+cols_user[3]+", NUMBER"+cols_user[4]+", INTEGER)"); //utworzenie tabeli flashcard

        db.execSQL("create table if not exists "+table_names[2]+" ("+cols_collection[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +cols_collection[1]+" TEXT,"+cols_collection[2]+" INTEGER"+cols_collection[3]+", INTEGER)"); //utworzenie tabeli collection

        db.execSQL("create table if not exists "+table_names[3]+" ("+cols_lesson[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +cols_lesson[1]+" DATETIME,"+cols_lesson[2]+" DATETIME"+cols_lesson[3]+", INTEGER"+cols_lesson[4]+", INTEGER"+cols_lesson[5]+", NUMBER)"); //utworzenie tabeli lesson

        db.execSQL("create table if not exists "+table_names[4]+" ("+cols_language[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +cols_language[1]+" TEXT)"); //utworzenie tabeli language

        db.execSQL("create table if not exists "+table_names[5]+" ("+cols_badge[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +cols_badge[1]+" TEXT,"+cols_badge[2]+" TEXT"+cols_badge[3]+", BOOLEAN)"); //utworzenie tabeli badge
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        for(int i = 0; i<table_names.length; i++)
        {
            db.execSQL("drop table if exists "+table_names[i]); //drop all tables in a loop
        }
        onCreate(db);
    }
    //public boolean insertData(String username, int experience, int level)
    public boolean insertData(String table_name, ContentValues contentValues)
    {
        //wstawianie danych
        SQLiteDatabase db=this.getWritableDatabase();
        /*ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,experience);
        contentValues.put(COL_4,level);*/
        long insert=db.insert(table_name,null,contentValues);
        if(insert==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor getAllData(String table_name)
    {
        //pobieranie wszystkich danych
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+table_name,null);
        return res;
    }
    //public boolean updateData(String id, String username, int exp, int level)
    public boolean updateData(String id, String table_name, ContentValues contentValues)
    {
        //uaktualnienie danych rekordu
        SQLiteDatabase db=this.getWritableDatabase();
        /*ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,exp);
        contentValues.put(COL_4,level);*/
        db.update(table_name,contentValues,"ID = ?", new String[]{ id });
        return true;
    }
    public int deleteData(String id, String table_name)
    {
        //usunięcie rekordu
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(table_name,"ID = ?", new String[]{ id });
    }
}
