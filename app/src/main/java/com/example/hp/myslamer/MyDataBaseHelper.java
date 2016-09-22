package com.example.hp.myslamer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP on 21-07-2015.
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="MyFriendsDatabase.db";


    public MyDataBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FRNDLIST(EMAIL VARCHAR PRIMARY KEY,NAME TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DELETE TABLE IF EXIST FRNDLIST;");

    }
    public void addFrnds(String email,String name){
        ContentValues cv =new ContentValues(2);

        cv.put("EMAIL",email);

        cv.put("NAME",name);

        getWritableDatabase().insert("FRNDLIST", "EMAIL", cv);
    }
    public Cursor getFrnds(){
        Cursor cursor=getReadableDatabase().rawQuery("SELECT * FROM FRNDLIST",null);

        return cursor;
    }
}
