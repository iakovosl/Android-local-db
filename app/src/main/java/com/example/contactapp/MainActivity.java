package com.example.contactapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    UnipiDbHelper mDbHelper;
    EditText ed1,ed2,ed3,ed4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new UnipiDbHelper(getApplicationContext());
        ed1=(EditText)findViewById(R.id.editText1);
        ed2=(EditText)findViewById(R.id.editText2);
        ed3=(EditText)findViewById(R.id.editText3);
        ed4=(EditText)findViewById(R.id.editText4);

    }
    // CRUD OPERATIONS
    // Adding new Student
    public void addStudent(Student student) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_NAME, student.getStudent_name());
        values.put(UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_TEL, student.getStudent_tel());

        // Inserting Row
        db.insert(UnipiDbContract.StudentEntry.TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Student
    public Student getStudent(int id) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                UnipiDbContract.StudentEntry._ID,
                UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_NAME,
                UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_TEL
        };
        // Filter results WHERE _ID = id
        String selection = UnipiDbContract.StudentEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };

        Cursor c = db.query(
                UnipiDbContract.StudentEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );
        if (c.getCount()>0){
            c.moveToFirst();
            Student student = new Student(c.getInt(c.getColumnIndex(UnipiDbContract.StudentEntry._ID)),
                    c.getString(c.getColumnIndex(UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_NAME)),
                    c.getString(c.getColumnIndex(UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_TEL)));
            // return student
            db.close();
            return student;
        }else{
            db.close();
            return null;
        }
    }

    // Getting All Students
    public List<Student> getAllStudents() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        List<Student> studentList = new ArrayList<Student>();
        String[] projection = {
                UnipiDbContract.StudentEntry._ID,
                UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_NAME,
                UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_TEL
        };
        Cursor c = db.query(
                UnipiDbContract.StudentEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                     // null columns means all
                null,                                     // null values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );
        while (c.moveToNext()) {
            Student student = new Student(c.getInt(c.getColumnIndex(UnipiDbContract.StudentEntry._ID)),
                    c.getString(c.getColumnIndex(UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_NAME)),
                    c.getString(c.getColumnIndex(UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_TEL)));
            studentList.add(student);
        }
        db.close();
        return studentList;
    }

    // Getting Students Count
    public int getStudentsCount() {
        String countQuery = "SELECT  * FROM " + UnipiDbContract.StudentEntry.TABLE_NAME;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        db.close();
        // return count
        return count;
    }

    // Updating single Student
    public int updateStudent(Student student) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // New value for two columns
        ContentValues values = new ContentValues();
        values.put(UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_NAME, student.getStudent_name());
        values.put(UnipiDbContract.StudentEntry.COLUMN_NAME_STUDENT_TEL, student.getStudent_tel());
        // Which row to update, based on the ID
        String selection = UnipiDbContract.StudentEntry._ID + " =?";
        String[] selectionArgs = { String.valueOf(student.getStudent_id()) };
        int count = db.update(
                UnipiDbContract.StudentEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        db.close();
        return count;
    }

    // Deleting single Student
    public int deleteStudent(Student student) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Which row to delete, based on the ID
        String selection = UnipiDbContract.StudentEntry._ID + " =?";
        String[] selectionArgs = { String.valueOf(student.getStudent_id()) };
        int count = db.delete(
                UnipiDbContract.StudentEntry.TABLE_NAME,
                selection,
                selectionArgs);
        db.close();
        return count;
    }

    public void messageshow(String message){
        AlertDialog.Builder abuilder;
        abuilder = new AlertDialog.Builder(this);
        abuilder.setTitle("Students");
        abuilder.setMessage(message);
        abuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = abuilder.create();
        dialog.show();
    }
    public void add1(View view){
        Student student=new Student(ed1.getText().toString(),ed2.getText().toString());
        addStudent(student);
        Toast.makeText(this,"Student added",Toast.LENGTH_LONG).show();
    }
    public void viewall(View view){
        List<Student> studentList = getAllStudents();
        if (studentList != null){
            StringBuffer buffer = new StringBuffer();
            for (Student student : studentList) {
                buffer.append(student.getStudent_id()+"\n");
                buffer.append(student.getStudent_name()+"\n");
                buffer.append(student.getStudent_tel()+"\n\n");
            }
            messageshow(buffer.toString());
        }
    }
    public void view1(View view){
        Student student = getStudent(Integer.valueOf(ed3.getText().toString()));
        if (student != null)
            messageshow(student.getStudent_name()+","+student.getStudent_tel());
    }
    public void delete(View view){
        Student student = getStudent(Integer.valueOf(ed4.getText().toString()));
        if (student != null){
            int count = deleteStudent(student);
            Toast.makeText(this,String.valueOf(count)+" Student deleted",Toast.LENGTH_LONG).show();
        }
    }
    public void howmany(View view){
        int count = getStudentsCount();
        messageshow(String.valueOf(count)+" students in DB!");
    }



}
