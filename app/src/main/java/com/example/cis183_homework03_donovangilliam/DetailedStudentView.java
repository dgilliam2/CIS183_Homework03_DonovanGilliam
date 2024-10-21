package com.example.cis183_homework03_donovangilliam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DetailedStudentView extends AppCompatActivity
{
    TextView tv_j_si_username;
    TextView tv_j_si_firstname;
    TextView tv_j_si_lname;
    TextView tv_j_si_email;
    TextView tv_j_si_age;
    TextView tv_j_si_gpa;
    TextView tv_j_si_major;

    Button btn_j_si_editstudent;
    Button btn_j_si_return;

    Intent intent_j_si_editstudent;
    Intent intent_j_si_return;

    DatabaseHelper dbHelper;

    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_detailed_student_view);
        tv_j_si_username = findViewById(R.id.tv_v_si_username);
        tv_j_si_firstname = findViewById(R.id.tv_v_si_firstname);
        tv_j_si_lname = findViewById(R.id.tv_v_si_lastname);
        tv_j_si_email = findViewById(R.id.tv_v_si_email);
        tv_j_si_age = findViewById(R.id.tv_v_si_age);
        tv_j_si_gpa = findViewById(R.id.tv_v_si_gpa);
        tv_j_si_major = findViewById(R.id.tv_v_si_major);

        btn_j_si_editstudent = findViewById(R.id.btn_v_si_editstudent);
        btn_j_si_return = findViewById(R.id.btn_v_si_return);

        intent_j_si_return = new Intent(DetailedStudentView.this, MainActivity.class);
        intent_j_si_editstudent = new Intent(DetailedStudentView.this, EditStudent.class);
        Intent cameFrom = getIntent();

        dbHelper = new DatabaseHelper(this);

        editStudentListener();
        returnHomeListener();

        if (cameFrom.getSerializableExtra("PassedStudent") != null)
        {
            student = (Student) cameFrom.getSerializableExtra("PassedStudent");
            String majorPrefix = dbHelper.getMajorPrefixFromId(student.getMajorId());
            String majorName = dbHelper.getMajorNameFromId(student.getMajorId());

            setText(tv_j_si_username, student.getUsername());
            setText(tv_j_si_firstname, student.getFname());
            setText(tv_j_si_lname, student.getLname());
            setText(tv_j_si_email, student.getEmail());
            setText(tv_j_si_age, String.valueOf(student.getAge()));
            setText(tv_j_si_gpa, String.valueOf(student.getGPA()));
            setText(tv_j_si_major, majorPrefix + " " + majorName);
        }
    }

    //looks nicer, less cluttered. could maybe do this when setting j variables for v items too
    private void setText(TextView textView, String text)
    {
        textView.setText(textView.getText() + " " + text);
    }

    private void editStudentListener()
    {
        btn_j_si_editstudent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                intent_j_si_editstudent.putExtra("StudentEdit", student);
                startActivity(intent_j_si_editstudent);
            }
        });
    }

    private void returnHomeListener()
    {
        btn_j_si_return.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(intent_j_si_return);
            }
        });
    }
}
