package com.example.contactapp;

import android.provider.BaseColumns;

public class UnipiDbContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private UnipiDbContract() {}
    // Inner class that defines the table contents
    public static class StudentEntry implements BaseColumns {
        public static final String TABLE_NAME = "student";
        public static final String COLUMN_NAME_STUDENT_NAME = "student_name";
        public static final String COLUMN_NAME_STUDENT_TEL = "student_tel";
    }
}
