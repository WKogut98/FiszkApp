package com.example.fiszkapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    public final static String DB_NAME="FiszkApp.db";
    public final static String TABLE_NAME="Users";
    public final static String COL_1="ID";
    public final static String COL_2="USERNAME";
    public final static String COL_3="PASSWORD";

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table if not exists "+TABLE_NAME+" ("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COL_2+" TEXT,"+COL_3+" TEXT)"); //utworzenie tabeli
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String username, String password)
    {
        //wstawianie danych
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,password);
        long insert=db.insert(TABLE_NAME,null,contentValues);
        if(insert==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
        public Cursor getAllData()
        {
            //pobieranie wszystkich danych
            SQLiteDatabase db=this.getWritableDatabase();
            Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
            return res;
        }
        public boolean updateData(String id, String username,String password)
        {
            //uaktualnienie danych rekordu
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(COL_1,id);
            contentValues.put(COL_2,username);
            contentValues.put(COL_3,password);
            db.update(TABLE_NAME,contentValues,"ID = ?", new String[]{ id });
            return true;
        }
        public int deleteData(String id)
        {
            //usunięcie rekordu
            SQLiteDatabase db=this.getWritableDatabase();
            return db.delete(TABLE_NAME,"ID = ?", new String[]{ id });
        }
    }
}
