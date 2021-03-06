package com.example.fiszkapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper
{
    public final static String DB_NAME="FiszkApp.db";
    public final static String[] table_names={"User", "Flashcard", "Collection","Lesson", "Language", "Badge"};
    public final static String[] cols_user={"ID", "NAME", "EXPERIENCE","LEVEL" };
    public final static String[] cols_flashcard={"ID", "WORD_FRONT", "WORD_BACK","PRIORITY", "COLLECTION_ID"};
    public final static String[] cols_collection={"ID", "NAME", "LANGUAGE_ID_FRONT","LANGUAGE_ID_BACK"};
    public final static String[] cols_lesson={"ID", "STARTED", "ENDED", "TOTAL_QUESTIONS","ANSWERS_CORRECT", "GAINED_EXP"};
    public final static String[] cols_language={"ID", "NAME"};
    public final static String[] cols_badge={"ID", "NAME", "DESCRIPTION", "IMAGE", "IS_UNLOCKED"};

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table if not exists "+table_names[0]+" ("+cols_user[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +cols_user[1]+" TEXT,"+cols_user[2]+" INTEGER,"+cols_user[3]+" INTEGER)"); //utworzenie tabeli user

        db.execSQL("create table if not exists "+table_names[1]+" ("+cols_flashcard[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +cols_flashcard[1]+" TEXT,"+cols_flashcard[2]+" TEXT,"+cols_flashcard[3]+" INTEGER,"+cols_flashcard[4]+" INTEGER)"); //utworzenie tabeli flashcard

        db.execSQL("create table if not exists "+table_names[2]+" ("+cols_collection[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +cols_collection[1]+" TEXT,"+cols_collection[2]+" INTEGER,"+cols_collection[3]+" INTEGER)"); //utworzenie tabeli collection

        db.execSQL("create table if not exists "+table_names[3]+" ("+cols_lesson[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +cols_lesson[1]+" DATETIME,"+cols_lesson[2]+" DATETIME,"+cols_lesson[3]+" INTEGER,"+cols_lesson[4]+" INTEGER,"+cols_lesson[5]+" NUMBER)"); //utworzenie tabeli lesson

        db.execSQL("create table if not exists "+table_names[4]+" ("+cols_language[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +cols_language[1]+" TEXT)"); //utworzenie tabeli language

        db.execSQL("create table if not exists "+table_names[5]+" ("+cols_badge[0]+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +cols_badge[1]+" TEXT,"+cols_badge[2]+" TEXT,"+cols_badge[3]+" TEXT,"+cols_badge[4]+" INTEGER)"); //utworzenie tabeli badge
        populateBadges(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        for (String table_name : table_names) {
            db.execSQL("drop table if exists " + table_name); //drop all tables in a loop
        }
        onCreate(db);
    }
    //public boolean insertData(String username, int experience, int level)
    public boolean insertData(String table_name, ContentValues contentValues)
    {
        //wstawianie danych
        SQLiteDatabase db=this.getWritableDatabase();
        long insert=db.insert(table_name,null,contentValues);
        return insert != -1;
    }
    public Cursor getAllData(String table_name)
    {
        //pobieranie wszystkich danych
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("select * from "+table_name,null);
    }
    public Cursor getElementFromAttribute(String table_name, String attribute, String value)
    {
        //pobieranie rekordu z warto??ci atrybutu
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("select * from "+table_name+" where "+attribute+"="+value,null);
    }
    public Cursor getElementFromAttribute(String table_name, String attribute, String value, Boolean isAttributeString)
    {
        if (isAttributeString) {
            SQLiteDatabase db=this.getWritableDatabase();
            return db.rawQuery("select * from "+table_name+" where "+attribute+"='"+value+"'",null);
        } else {
            return getElementFromAttribute(table_name,attribute,value);
        }
    }
    //public boolean updateData(String id, String username, int exp, int level)
    public boolean updateData(String id, String table_name, ContentValues contentValues)
    {
        //uaktualnienie danych rekordu
        SQLiteDatabase db=this.getWritableDatabase();
        db.update(table_name,contentValues,"ID = ?", new String[]{ id });
        return true;
    }
    public int deleteData(String id, String table_name)
    {
        //usuni??cie rekordu
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(table_name,"ID = ?", new String[]{ id });
    }
    public String[] getAllItemNamesAsArray(String table_name)
    {
        Cursor cursor = getAllData(table_name);
        String[] itemArray = new String[cursor.getCount()+2];
        itemArray[0]="--Wybierz opcj??--";
        itemArray[1]="Dodaj";
        for(int i=2;i<itemArray.length;i++)
        {
            cursor.moveToNext();
            itemArray[i]=cursor.getString(1);
        }
        return itemArray;
    }

    public Cursor getLastLesson()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.rawQuery("select * from Lesson order by ID desc limit 1",null);
    }

    public int getNumberOfFlashcardsInCollection(String collectionName)
    {
        //trzeba znale???? id kolekcji do kt??rej b??dziemy dodawa?? fiszk??
        Cursor cur = getElementFromAttribute("Collection","NAME",collectionName,true);
        cur.moveToNext();
        int id = cur.getInt(0);
        //we?? liczb?? fiszek
        String query = "select count(1) from Flashcard f " +
                "inner join Collection c on f.COLLECTION_ID = c.ID " +
                "where c.id = "+id;
        SQLiteDatabase db=this.getWritableDatabase();
        cur = db.rawQuery(query,null);
        cur.moveToNext();
        int a = cur.getInt(0);
        return a;
    }

    public List<FlashCard> getFlashcardsInCollection(String collectionName)
    {
        String query = "select * from Flashcard f " +
                "inner join Collection c on f.COLLECTION_ID = c.ID " +
                "where c.NAME = '"+collectionName+"' " +
                "order by f.PRIORITY asc " +
                "limit 5";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cur = db.rawQuery(query,null);
        List<FlashCard> list = new ArrayList<>();
        int id;
        String front;
        String back;
        int priority;
        while(cur.moveToNext())
        {
            id = cur.getInt(0);
            front = cur.getString(1);
            back = cur.getString(2);
            priority = cur.getInt(3);
            list.add(new FlashCard(id,front,back,priority));
        }
        return list;
    }
    public List<String> getWordsInRandomOrder(String collectionName, boolean isReversed, String rightAnswer)
    {
        List<FlashCard> list=getFlashcardsInCollection(collectionName);
        List<String> words=new ArrayList<>();
        for(FlashCard f:list)
        {
            if(!isReversed)
            {
                words.add(f.back);
            }
            else
            {
                words.add(f.front);
            }
        }
        words.remove(rightAnswer);
        Collections.shuffle(words);
        return words;
    }

    public void updateFlashcardsToDB(List<FlashCard> list)
    {
        for (FlashCard card : list)
        {
            String query = "update Flashcard set " +
                    "PRIORITY = " + card.getPriority() + " " +
                    "WHERE ID = " + card.getId();
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(query);
        }
    }

    public void populateBadges(SQLiteDatabase db)
    {
        String[][] badges={
                {"G??odny wiedzy", "Uko??cz pierwsz?? lekcj??", Integer.toString(R.drawable.badge01)},
                {"O, to tu s?? levele?!", "Awansuj na poziom drugi", Integer.toString(R.drawable.badge02)},
                {"M??dra g??owa", "Uko??cz lekcj?? bez pomy??ki", Integer.toString(R.drawable.badge03)},
                {"Chc?? wi??cej", "Dodaj 10 fiszek", Integer.toString(R.drawable.badge04)},
                {"Dopiero si?? rozkr??cam", "Dodaj 20 fiszek", Integer.toString(R.drawable.badge05)},
                {"Ci??gle mi ma??o", "Dodaj 30 fiszek", Integer.toString(R.drawable.badge06)},
                {"Zahartowany w boju", "Awansuj na poziom dziesi??ty", Integer.toString(R.drawable.badge07)},
                {"Sumienny", "Odb??d?? 5 lekcji", Integer.toString(R.drawable.badge08)},
                {"Prymus", "Odb??d?? 10 lekcji", Integer.toString(R.drawable.badge09)},
                {"??wiczenie czyni mistrza", "Odb??d?? 20 lekcji", Integer.toString(R.drawable.badge10)},
                {"Empiryk", "Zdob??d?? 5000 punkt??w do??wiadczenia", Integer.toString(R.drawable.badge11)},
                {"Lvl 100 BOSS", "Awansuj na poziom setny", Integer.toString(R.drawable.badge12)},
                {"Rage quit", "Wyjd?? z lekcji przed jej ko??cem", Integer.toString(R.drawable.badge13)},
                {"Dywersyfikacja ??rodk??w", "Miej przynajmniej 2 kolekcje", Integer.toString(R.drawable.badge14)},
        };
        for(int i=0;i<14;i++)
        {
            String query = "INSERT INTO Badge ("+cols_badge[1]+","+cols_badge[2]+","+cols_badge[3]+","+cols_badge[4]+")" +
                    "VALUES ('"+badges[i][0]+"', '"+badges[i][1]+"','"+badges[i][2]+"', 0)";
            db.execSQL(query);
        }
    }

    public boolean isBadgeUnlocked(String badgeName) //sprawdzenie czy odznaka jest odblokowana
    {
        Cursor badge=getElementFromAttribute("Badge", "name", badgeName,true);
        badge.moveToNext();
        int isUnlocked=badge.getInt(4);
        return isUnlocked != 0;
    }
    public void unlockBadge(String badgeName) //odblokowanie odznaki
    {
        if(!isBadgeUnlocked(badgeName))
        {
            Cursor badge =getElementFromAttribute("Badge", "name",badgeName,true);
            badge.moveToNext();
            ContentValues values=new ContentValues();
            values.put(DBHelper.cols_badge[4],1);
            updateData(""+badge.getInt(0), "Badge", values);
        }
    }
}
