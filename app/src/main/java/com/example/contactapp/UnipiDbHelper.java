package com.example.contactapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UnipiDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "UnipiDB.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_STUDENT_TABLE =
            "CREATE TABLE " + UnipiDbContract.StudentEntry.TABLE_NAME + " (" +
                    UnipiDbContract.StudentEntry._ID + " INTEGER PRIMARY KEY," +
                    UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_NAME + TEXT_TYPE + COMMA_SEP +
                    UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_TEL + TEXT_TYPE + " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UnipiDbContract.StudentEntry.TABLE_NAME;

    public UnipiDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

}
