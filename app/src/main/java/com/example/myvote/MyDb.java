package com.example.myvote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDb extends SQLiteOpenHelper {
    public static String username;
    //public static final String COLUMN_ID = "ID";
    //public static final String COLUMN_NAME = "NAME";
    //public static final String COLUMN_VOTES = "VOTES";

    public MyDb(Context context) {
        super(context, "voting.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(username varchar(20), password varchar(20));");
        db.execSQL("insert into users values('voter', 'voter');");
        db.execSQL("create table administrator(username varchar(20), password varchar(20));");
        db.execSQL("insert into administrator values('admin', 'ayoose');");
        db.execSQL("create table candidates(id number(2), name varchar(50), votes number(4));");

        db.execSQL("create table voting (id number(2), mobile varchar(20));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        onCreate(db);
    }

}
