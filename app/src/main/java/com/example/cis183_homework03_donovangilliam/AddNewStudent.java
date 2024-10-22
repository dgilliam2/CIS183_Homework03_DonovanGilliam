package com.example.cis183_homework03_donovangilliam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddNewStudent extends AppCompatActivity
{
    TextView tv_j_as_duplicate;
    TextView tv_j_as_error;

    EditText et_j_as_username;
    EditText et_j_as_fname;
    EditText et_j_as_lname;
    EditText et_j_as_email;
    EditText et_j_as_age;
    EditText et_j_as_gpa;

    Button btn_j_as_addstudent;
    Button btn_j_as_addmajor;
    Button btn_j_as_return;

    Spinner spn_j_as_majors;

    Intent intent_j_as_addmajor;
    Intent intent_j_as_return;

    DatabaseHelper dbHelper;

    ArrayList<String> major_list = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_student);

        tv_j_as_error = findViewById(R.id.tv_v_as_error);
        tv_j_as_duplicate = findViewById(R.id.tv_v_as_duplicate);

        et_j_as_username = findViewById(R.id.et_v_as_username);
        et_j_as_fname = findViewById(R.id.et_v_as_fname);
        et_j_as_lname = findViewById(R.id.et_v_as_lname);
        et_j_as_email = findViewById(R.id.et_v_as_email);
        et_j_as_age = findViewById(R.id.et_v_as_age);
        et_j_as_gpa = findViewById(R.id.et_v_as_gpa);

        btn_j_as_addstudent = findViewById(R.id.btn_v_as_addstudent);
        btn_j_as_addmajor = findViewById(R.id.btn_v_as_addmajor);
        btn_j_as_return = findViewById(R.id.btn_v_as_return);

        spn_j_as_majors = findViewById(R.id.spn_v_as_majors);

        intent_j_as_addmajor = new Intent(AddNewStudent.this,AddNewMajor.class);
        intent_j_as_return = new Intent(AddNewStudent.this, MainActivity.class);

        dbHelper = new DatabaseHelper(this);
        dbHelper.fillMajorNameArray(major_list);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, major_list);
        spn_j_as_majors.setAdapter(arrayAdapter);

        addStudentConfirmListener();
        addNewMajorListener();
        returnButtonListener();
    }

    private void addStudentConfirmListener()
    {
        btn_j_as_addstudent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (fieldsNotNull())
                {
                    if (!dbHelper.findDuplicateStudents(et_j_as_username.getText().toString()))
                    {
                        Student student = new Student();
                        student.setUsername(et_j_as_username.getText().toString());
                        student.setFname(et_j_as_fname.getText().toString());
                        student.setLname(et_j_as_lname.getText().toString());
                        student.setEmail(et_j_as_email.getText().toString());
                        student.setAge(Integer.parseInt(et_j_as_age.getText().toString()));
                        student.setGPA(Float.parseFloat(et_j_as_gpa.getText().toString()));
                        student.setMajorId(dbHelper.getMajorIdFromName(spn_j_as_majors.getSelectedItem().toString()));

                        dbHelper.addStudentToDb(student);
                        Toast.makeText(AddNewStudent.this, "Student added to database.", Toast.LENGTH_SHORT).show();

                        et_j_as_username.setText("");
                        et_j_as_fname.setText("");
                        et_j_as_lname.setText("");
                        et_j_as_email.setText("");
                        et_j_as_age.setText("");
                        et_j_as_gpa.setText("");
                        spn_j_as_majors.setSelection(0);
                        tv_j_as_duplicate.setVisibility(View.INVISIBLE);
                        tv_j_as_error.setVisibility(View.INVISIBLE);
                    }
                    else
                    {
                        tv_j_as_duplicate.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    tv_j_as_error.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void addNewMajorListener()
    {
        btn_j_as_addmajor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(intent_j_as_addmajor);
            }
        });
    }

    private void returnButtonListener()
    {
        btn_j_as_return.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(intent_j_as_return);
            }
        });
    }

    //separate function for null checking bc it looks cleaner
    private boolean fieldsNotNull()
    {
        if (!et_j_as_username.getText().toString().isEmpty()
                && !et_j_as_fname.getText().toString().isEmpty()
                && !et_j_as_lname.getText().toString().isEmpty()
                && !et_j_as_email.getText().toString().isEmpty()
                && !et_j_as_age.getText().toString().isEmpty()
                && !et_j_as_gpa.getText().toString().isEmpty())
        {
            return true;
        }
        return false;
    }
}