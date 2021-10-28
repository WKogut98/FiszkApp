package com.example.fiszkapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper
{
    public final static String DB_NAME="FiszkApp.db";
    public final static String TABLE_USERS="Users";
    public final static String COL_1="ID";
    public final static String COL_2="NAME";
    public final static String COL_3="EXPERIENCE";
    public final static String COL_4="LEVEL";

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table if not exists "+TABLE_USERS+" ("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COL_2+" TEXT,"+COL_3+" NUMBER"+COL_3+", NUMBER)"); //utworzenie tabeli
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists "+TABLE_USERS);
        onCreate(db);
    }
    public boolean insertData(String username, int experience, int level)
    {
        //wstawianie danych
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,experience);
        contentValues.put(COL_4,level);
        long insert=db.insert(TABLE_USERS,null,contentValues);
        if(insert==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor getAllData()
    {
        //pobieranie wszystkich danych
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_USERS,null);
        return res;
    }
    public boolean updateData(String id, String username, int exp, int level)
    {
        //uaktualnienie danych rekordu
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,username);
        contentValues.put(COL_3,exp);
        contentValues.put(COL_4,level);
        db.update(TABLE_USERS,contentValues,"ID = ?", new String[]{ id });
        return true;
    }
    public int deleteData(String id)
    {
        //usunięcie rekordu
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_USERS,"ID = ?", new String[]{ id });
    }
}
