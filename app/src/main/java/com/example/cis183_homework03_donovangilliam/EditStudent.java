package com.example.cis183_homework03_donovangilliam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EditStudent extends AppCompatActivity
{

    TextView tv_j_es_error;

    EditText et_j_es_fname;
    EditText et_j_es_lname;
    EditText et_j_es_email;
    EditText et_j_es_age;
    EditText et_j_es_gpa;

    Button btn_j_es_confirm;
    Button btn_j_es_return;

    Spinner spn_j_es_majors;

    Intent intent_j_es_main;
    Intent intent_j_es_return;

    DatabaseHelper dbHelper;

    ArrayList<String> major_list = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;

    private Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_student);

        tv_j_es_error = findViewById(R.id.tv_v_es_error);

        et_j_es_fname = findViewById(R.id.et_v_es_fname);
        et_j_es_lname = findViewById(R.id.et_v_es_lname);
        et_j_es_email = findViewById(R.id.et_v_es_email);
        et_j_es_age = findViewById(R.id.et_v_es_age);
        et_j_es_gpa = findViewById(R.id.et_v_es_gpa);

        btn_j_es_confirm = findViewById(R.id.btn_v_es_confirm);
        btn_j_es_return = findViewById(R.id.btn_v_es_return);

        spn_j_es_majors = findViewById(R.id.spn_v_es_majors);

        intent_j_es_main = new Intent(EditStudent.this,MainActivity.class);
        intent_j_es_return = new Intent(EditStudent.this, DetailedStudentView.class);
        Intent cameFrom = getIntent();


        dbHelper = new DatabaseHelper(this);

        dbHelper.fillMajorNameArray(major_list);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, major_list);
        spn_j_es_majors.setAdapter(arrayAdapter);

        if (cameFrom.getSerializableExtra("StudentEdit") != null)
        {
            student = (Student) cameFrom.getSerializableExtra("StudentEdit");

            String majorName = dbHelper.getMajorNameFromId(student.getMajorId());

            setText(et_j_es_fname, student.getFname());
            setText(et_j_es_lname, student.getLname());
            setText(et_j_es_email, student.getEmail());
            setText(et_j_es_age, String.valueOf(student.getAge()));
            setText(et_j_es_gpa, String.valueOf(student.getGPA()));

            //set spinner position to match student's major
            int spinnerPosition = arrayAdapter.getPosition(majorName);
            spn_j_es_majors.setSelection(spinnerPosition);
        }

        editStudentConfirmListener();
        returnButtonListener();
    }

    //not really needed since line length is lower, but still looks nice
    private void setText(EditText editText, String text)
    {
        editText.setText(text);
    }

    private void editStudentConfirmListener()
    {
        btn_j_es_confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (fieldsNotNull())
                {
                    student.setFname(et_j_es_fname.getText().toString());
                    student.setLname(et_j_es_lname.getText().toString());
                    student.setEmail(et_j_es_email.getText().toString());
                    student.setAge(Integer.parseInt(et_j_es_age.getText().toString()));
                    student.setGPA(Float.parseFloat(et_j_es_gpa.getText().toString()));
                    student.setMajorId(dbHelper.getMajorIdFromName(spn_j_es_majors.getSelectedItem().toString()));

                    dbHelper.updateStudentInfo(student);
                    startActivity(intent_j_es_main);
                }
                else
                {
                    tv_j_es_error.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void returnButtonListener()
    {
        btn_j_es_return.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(intent_j_es_return);
            }
        });
    }

    //separate function for null checking bc it looks cleaner
    private boolean fieldsNotNull()
    {
        if (!et_j_es_fname.getText().toString().isEmpty()
            && !et_j_es_lname.getText().toString().isEmpty()
            && !et_j_es_email.getText().toString().isEmpty()
            && !et_j_es_age.getText().toString().isEmpty()
            && !et_j_es_gpa.getText().toString().isEmpty())
        {
            return true;
        }
        return false;
    }
}