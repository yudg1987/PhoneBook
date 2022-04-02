package com.dgyu.phonebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static String name = "sqlLiteUser111.db";
    static int dbVersion = 1;

    public DatabaseHelper(Context context) {
        super(context, name, null, dbVersion);
    }

    //只在创建的时候用一次
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(id integer primary key autoincrement,username varchar(20),password varchar(20),age integer,sex varchar(2),pic integer,phone varchar(20))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

