package com.example.mycontactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;


public class DatabaseHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Contact2019.db";
    public static final String TABLE_NAME = "Contact2019_table";
    public static final String ID = "ID";

    public static final String COLUMN_NAME_CONTACT = "contact";
    public static final String COLUMN_NAME_NUMBER = "phone_number";
    public static final String COLUMN_NAME_ADDRESS = "home_address";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_CONTACT +  " TEXT," + COLUMN_NAME_NUMBER + " TEXT,"
                    + COLUMN_NAME_ADDRESS + " TEXT)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase(); //for test only, remove later
        Log.d("MyContactApp", "DatabaseHelper: constructed DatabaseHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MyContactApp", "DatabaseHelper: creating database");
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        Log.d("MyContactApp", "DatabaseHelper: upgraded database");
        onCreate(db);
    }

    public boolean insertData(String name, String number, String address) {
        Log.d("MyContactApp", "DatabaseHelper: inserting data");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_CONTACT, name);
        contentValues.put(COLUMN_NAME_NUMBER, number);
        contentValues.put(COLUMN_NAME_ADDRESS, address);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
        {
            Log.d("MyContactApp", "Databasehelper: Contact insert FAILED");
            return false;
        }
        else
        {
            Log.d("MyContactApp", "Databasehelper: Contact insert PASSED");
            return true;
        }
    }

    public Cursor getAllData() {
        Log.d("MyContactApp", "DatabaseHelper: getAllData called");
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }
}