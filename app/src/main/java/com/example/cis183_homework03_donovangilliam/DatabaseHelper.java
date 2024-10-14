package com.example.cis183_homework03_donovangilliam;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "StudentReg.db";
    private static final String STUDENT_TABLE_NAME = "Students";
    private static final String MAJORS_TABLE_NAME = "Majors";

    public  DatabaseHelper(Context c)
    {
        super(c, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " +
                STUDENT_TABLE_NAME +
                " (Username varchar(50) primary key, " +
                "Fname varchar(50), " +
                "Lname varchar(50), " +
                "Email varchar(50), " +
                "Age integer not null, " +
                "GPA float not null, " +
                "MajorId varchar(50), " +
                "foreign key (MajorId) references " +
                 MAJORS_TABLE_NAME +
                " (MajorId));");
        db.execSQL("CREATE TABLE " +
                MAJORS_TABLE_NAME +
                " (MajorId integer primary key autoincrement not null, " +
                "MajorName varchar(50), " +
                "MajorPrefix varchar(50));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + MAJORS_TABLE_NAME + ";");

        onCreate(db);
    }

    private void initData()
    {
        initStudents();
        initMajors();
    }

    private void initStudents()
    {
        if (countRecordsFromTable(STUDENT_TABLE_NAME) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO " + STUDENT_TABLE_NAME + "(Fname, Lname, Email, Age, GPA, MajorId) VALUES ('John', 'Man', 'jman@gmail.com', 19, 4.0, 1);");
            db.execSQL("INSERT INTO " + STUDENT_TABLE_NAME + "(Fname, Lname, Email, Age, GPA, MajorId) VALUES ('Alex', 'Hyde', 'ahyde@gmail.com', 21, 2.7, 2);");
            db.execSQL("INSERT INTO " + STUDENT_TABLE_NAME + "(Fname, Lname, Email, Age, GPA, MajorId) VALUES ('Matt', 'Cuda', 'mcuda@gmail.com', 20, 3.3, 3);");

            //close the database
            db.close();
        }
    }

    private void initMajors()
    {
        if (countRecordsFromTable(MAJORS_TABLE_NAME) == 0)
        {

        }
    }

    public int countRecordsFromTable(String tableName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);
        db.close();
        return numRows;
    }
}
