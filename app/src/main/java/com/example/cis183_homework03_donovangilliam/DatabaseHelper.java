package com.example.cis183_homework03_donovangilliam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "StudentReg.db";
    private static final String STUDENT_TABLE_NAME = "Students";
    private static final String MAJORS_TABLE_NAME = "Majors";
    //put version up here too so i can change it easier
    private static final int DATABASE_VERSION = 8;

    public DatabaseHelper(Context c)
    {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Setup
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " +
                STUDENT_TABLE_NAME +
                " (Username varchar(50) PRIMARY KEY, " +
                "Fname varchar(50), " +
                "Lname varchar(50), " +
                "Email varchar(50), " +
                "Age integer NOT NULL, " +
                "GPA float NOT NULL, " +
                "MajorId integer NOT NULL, " +
                "FOREIGN KEY (MajorId) REFERENCES " +
                 MAJORS_TABLE_NAME +
                " (MajorId));");
        db.execSQL("CREATE TABLE " +
                MAJORS_TABLE_NAME +
                " (MajorId integer PRIMARY KEY AUTOINCREMENT NOT NULL, " +
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

    public void initData()
    {
        //check the table count here so that it only initializes the db if both of these are empty
        //used to prevent it from refilling students when we delete them
        if (countRecordsFromTable(STUDENT_TABLE_NAME) == 0 && countRecordsFromTable(MAJORS_TABLE_NAME) == 0)
        {
            initStudents();
            initMajors();
        }
    }

    private void initStudents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + STUDENT_TABLE_NAME +
                "(Username," +
                "Fname, " +
                "Lname, " +
                "Email, " +
                "Age, " +
                "GPA, " +
                "MajorId) VALUES ('jmahn2','John', 'Mahn', 'jman@gmail.com', 19, 4.0, 1);");
        db.execSQL("INSERT INTO " + STUDENT_TABLE_NAME +
                "(Username," +
                "Fname, " +
                "Lname, " +
                "Email, " +
                "Age, " +
                "GPA, " +
                "MajorId) VALUES ('ahy3','Alex', 'Hyde', 'ahyde@gmail.com', 21, 2.7, 2);");
        db.execSQL("INSERT INTO " + STUDENT_TABLE_NAME +
                "(Username, " +
                "Fname, " +
                "Lname, " +
                "Email, " +
                "Age, " +
                "GPA, " +
                "MajorId) VALUES ('mc20mc','Matt', 'Cuda', 'mcuda@gmail.com', 20, 3.3, 3);");

        //close the database
        db.close();
    }

    private void initMajors()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME +
                "(MajorId, " +
                "MajorName, " +
                "MajorPrefix) VALUES (1, 'App Development', 'CIS');");
        db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME +
                "(MajorId, " +
                "MajorName, " +
                "MajorPrefix) VALUES (2, 'General Psychology', 'PSYCH');");
        db.execSQL("INSERT INTO " + MAJORS_TABLE_NAME +
                "(MajorId, " +
                "MajorName, " +
                "MajorPrefix) VALUES (3, 'Information Security', 'CIA');");

        db.close();
    }

    public int countRecordsFromTable(String tableName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);
        db.close();
        return numRows;
    }

    //Data Manip

    //POPULATING ARRAYS
    public void fillStudentArray(ArrayList<Student> al)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + STUDENT_TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(select, null);
        //clear to prevent duplicates should the database be updated
        al.clear();
        if (cursor != null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                String username = cursor.getString(0);
                String fname = cursor.getString(1);
                String lname = cursor.getString(2);
                String email = cursor.getString(3);
                int age = cursor.getInt(4);
                float gpa = cursor.getFloat(5);
                int majorId = cursor.getInt(6);
                Student student = new Student(username, fname, lname, email, age, gpa, majorId);
                // Add the student to the ArrayList
                al.add(student);
                cursor.moveToNext();
            }
            cursor.close();
        }
    }

    public void fillMajorNameArray(ArrayList<String> al)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + MAJORS_TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(select, null);
        //clear to prevent duplicates should the database be updated
        al.clear();
        if (cursor != null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(1);
                // Add the major to the ArrayList
                al.add(name);
                cursor.moveToNext();
            }
            cursor.close();
        }
    }

    //FILTERING
    public void filterStudents(String statement, ArrayList<Student> al)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(statement, null);
        //clear to prevent duplicates should the database be updated
        al.clear();
        if (cursor != null)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                String username = cursor.getString(0);
                String fname = cursor.getString(1);
                String lname = cursor.getString(2);
                String email = cursor.getString(3);
                int age = cursor.getInt(4);
                float gpa = cursor.getFloat(5);
                int majorId = cursor.getInt(6);
                Student student = new Student(username, fname, lname, email, age, gpa, majorId);
                // Add the student to the ArrayList
                al.add(student);
                cursor.moveToNext();
            }
            cursor.close();
        }
    }

    //MAJOR INFO
    public int getMajorIdFromName(String mn)
    {
        int id = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        //might cause issues if two majors have the same name for some reason
        //in this case it is fine
        //will add duplicate checking when adding majors to db anyway
        String select = "SELECT MajorId FROM " + MAJORS_TABLE_NAME + " WHERE MajorName = '" + mn + "';";
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

    public String getMajorPrefixFromId(int mi)
    {
        String prefix = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT MajorPrefix FROM " + MAJORS_TABLE_NAME + " WHERE MajorId = '" + mi + "';";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
            prefix = cursor.getString(0);
            cursor.close();
            db.close();
        }
        return prefix;
    }

    public String getMajorNameFromId(int mi)
    {
        String name = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT MajorName FROM " + MAJORS_TABLE_NAME + " WHERE MajorId = '" + mi + "';";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
            name = cursor.getString(0);
            cursor.close();
            db.close();
        }
        return name;
    }


    //DUPE CHECKING
    public boolean findDuplicateStudents(String u)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT Username FROM " + STUDENT_TABLE_NAME + " WHERE Username = '" + u + "';";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst())
        {
            db.close();
            return true;
        }
        else
        {
            cursor.close();
            db.close();
            return false;
        }
    }

    public boolean findDuplicateMajors(String mn)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT MajorId FROM " + MAJORS_TABLE_NAME + " WHERE MajorName = '" + mn + "' COLLATE NOCASE;";
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst())
        {
            db.close();
            return true;
        }
        else
        {
            cursor.close();
            db.close();
            return false;
        }
    }

    //ADDING, REMOVING, AND UPDATING DATA
    public void addStudentToDb(Student student)
    {
        //Add to database
        //this is a lot more concise and easier to write than a raw SQL statement in this case
        //equivalent to db.execSQL(INSERT INTO Students(etc) VALUES etc,etc,etc)
        //possibly(likely?) slower than raw SQL, but doesn't matter here
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Username", student.getUsername());
        cv.put("Fname", student.getFname());
        cv.put("Lname", student.getLname());
        cv.put("Email", student.getEmail());
        cv.put("Age", student.getAge());
        cv.put("GPA", student.getGPA());
        cv.put("MajorId", student.getMajorId());
        db.insert(STUDENT_TABLE_NAME, null, cv);
        db.close();
    }

    public void addMajorToDb(Major major)
    {
        //see above
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("MajorName", major.getMajorName());
        cv.put("MajorPrefix", major.getMajorPrefix());
        db.insert(MAJORS_TABLE_NAME, null, cv);
        db.close();
    }

    public void removeStudentFromDb(String u)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(STUDENT_TABLE_NAME,"Username = ?", new String[]{u});
        //same as this, minus the placeholder
        //db.execSQL("DELETE FROM " + STUDENT_TABLE_NAME + " WHERE Username = '" + u + "';");
        db.close();
    }

    public void updateStudentInfo(Student student)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Fname", student.getFname());
        cv.put("Lname", student.getLname());
        cv.put("Email", student.getEmail());
        cv.put("Age", student.getAge());
        cv.put("GPA", student.getGPA());
        cv.put("MajorId", student.getMajorId());
        db.update(STUDENT_TABLE_NAME, cv, "Username = ?", new String[]{student.getUsername()});
        //^equivalent to this, minus the placeholder
        //android docs say to use these for insert, delete, update
        //use rawQuery for SELECT
        /*db.execSQL("UPDATE "
                + STUDENT_TABLE_NAME
                + " SET " +
                "Fname = '" + student.getFname() +
                "', Lname= '" + student.getLname() +
                "', Email = '" + student.getEmail() +
                "', Age = " + student.getAge() +
                ", GPA = " + student.getGPA() +
                ", MajorId = " + student.getMajorId() +
                " WHERE Username = '" + student.getUsername() + "';");*/
        db.close();
    }
}
