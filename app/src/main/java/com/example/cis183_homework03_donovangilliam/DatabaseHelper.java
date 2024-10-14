package com.example.cis183_homework03_donovangilliam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "StudentReg.db";
    private static final String STUDENT_TABLE_NAME = "Students";
    private static final String MAJORS_TABLE_NAME = "Majors";
    private static final int DATABASE_VERSION = 1;

    public  DatabaseHelper(Context c)
    {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Setup

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
            db.execSQL("INSERT INTO " + STUDENT_TABLE_NAME +
                    "(Username," +
                    "Fname, " +
                    "Lname, " +
                    "Email, " +
                    "Age, " +
                    "GPA, " +
                    "MajorId) VALUES ('John', 'Man', 'jman@gmail.com', 19, 4.0, 1);");
            db.execSQL("INSERT INTO " + STUDENT_TABLE_NAME +
                    "(Username," +
                    "Fname, " +
                    "Lname, " +
                    "Email, " +
                    "Age, " +
                    "GPA, " +
                    "MajorId) VALUES ('Alex', 'Hyde', 'ahyde@gmail.com', 21, 2.7, 2);");
            db.execSQL("INSERT INTO " + STUDENT_TABLE_NAME +
                    "(Username, " +
                    "Fname, " +
                    "Lname, " +
                    "Email, " +
                    "Age, " +
                    "GPA, " +
                    "MajorId) VALUES ('Matt', 'Cuda', 'mcuda@gmail.com', 20, 3.3, 3);");

            //close the database
            db.close();
        }
    }

    private void initMajors()
    {
        if (countRecordsFromTable(MAJORS_TABLE_NAME) == 0)
        {
            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(MajorId, MajorName, MajorPrefix) VALUES (0, 'App Development', 'CIS');");
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(MajorId, MajorName, MajorPrefix) VALUES (1, 'General Psychology', 'PSYCH');");
            db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME + "(MajorId, MajorName, MajorPrefix) VALUES (2, 'Information Security', 'CIA');");

            db.close();
        }
    }

    public int countRecordsFromTable(String tableName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);
        db.close();
        return numRows;
    }


    //Data Manip

    public void filterStudentsByName(String name)
    {
        getMajorIdFromName("name");
    }

    public void filterStudentsByUname(String username)
    {

    }

    public void filterStudentsByMajor(int major)
    {

    }

    public void filterStudentsByGpa(float gpa)
    {

    }

    public int getMajorIdFromName(String major)
    {
        int id = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        //might cause issues if two majors have the same name for some reason
        //in this case it is fine
        //will add duplicate checking when adding majors to db anyway
        String select = "SELECT MajorId FROM " + MAJORS_TABLE_NAME + " WHERE MajorName = '" + major + "';";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
            id = cursor.getInt(0);
            cursor.close();
            db.close();
        }
        return id;
    }

    public int getMajorIdFromPrefix(String m_prefix)
    {
        int id = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        //see above comment
        String select = "SELECT MajorId FROM " + MAJORS_TABLE_NAME + " WHERE MajorPrefix = '" + m_prefix + "';";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
            id = cursor.getInt(0);
            cursor.close();
            db.close();
        }
        return id;
    }

    public void addStudentToDb(Student student)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Username", student.getUsername());
        cv.put("Fname", student.getFname());
        cv.put("Lname", student.getLname());
        cv.put("Email", student.getEmail());
        cv.put("Age", student.getAge());
    }

    public void addMajorToDb(Major major)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("MajorID", major.getMajorId());
        cv.put("MajorName", major.getMajorName());
        cv.put("MajorPrefix", major.getMajorPrefix());
        db.insert(MAJORS_TABLE_NAME, null, cv);
        db.close();
    }


}
