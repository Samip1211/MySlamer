package com.example.hp.myslamer;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

/**
 * Created by HP on 29-07-2015.
 */
public class FriendDataBase extends SQLiteOpenHelper {

    String umail;
    private static final String DATABASE_NAME="MyFriendInfo.db";

    public FriendDataBase(Context context) {
        super(context,DATABASE_NAME,null,2);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL("CREATE TABLE "+umail+"IF NOT EXIST (NUM INTEGER AUTOINCREMENT PRIMARY KEY,QUESTION VARCHAR);");
    }
    public void onCreate1(String umail){
        umail=umail.replace("@", "a");

        umail=umail.replace(".", "a");

        SQLiteDatabase db = this.getWritableDatabase();

        System.out.println(umail);

        umail=umail.toUpperCase();

        db.execSQL("CREATE TABLE IF NOT EXISTS "+umail +" (NUM INTEGER PRIMARY KEY,QUESTION VARCHAR,ANSWER VARCHAR);");
    }
    public void addQuestion(String question,String umail){
        ContentValues cv =new ContentValues(2);

        cv.put("QUESTION", question);

        umail=umail.toUpperCase();

        getWritableDatabase().insert(umail, "QUESTION", cv);
    }
    public void addAnswer(Integer numb,String mail,String answer){

        SQLiteDatabase db = this.getWritableDatabase();

        mail=mail.replace("@", "a");

        mail = mail.replace(".", "a");

        mail=mail.toUpperCase();

        ContentValues values= new ContentValues(2);

        values.put("ANSWER",answer);

        db.update(mail,values, "NUM="+ numb, null);

       // System.out.println("UPDATE "+ mail +" SET ANSWER = "+answer+" WHERE NUM = "+numb);

        //db.execSQL("UPDATE "+ mail +" SET ANSWER = "+answer+" WHERE NUM = "+numb);
    }

    public Cursor getQuestion(String mail){

        mail=mail.replace("@","a");

        mail = mail.replace(".", "a");

        mail=mail.toUpperCase();
        Cursor cursor=getReadableDatabase().rawQuery("SELECT * FROM "+ mail,null);

        return cursor;
    }
    public Cursor getSpecific(Integer num ,String mail){

        mail=mail.replace("@","a");

        mail=mail.replace(".", "a");

        mail=mail.toUpperCase();

        Cursor cursor=getReadableDatabase().rawQuery("SELECT * FROM "+mail +" WHERE NUM = "+ num, null);

        return cursor;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
