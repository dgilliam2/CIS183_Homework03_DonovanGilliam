package com.example.cis183_homework03_donovangilliam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailedStudentView extends AppCompatActivity {
    TextView tv_j_si_username;
    TextView tv_j_si_fname;
    TextView tv_j_si_lname;
    TextView tv_j_si_email;
    TextView tv_j_si_age;
    TextView tv_j_si_gpa;
    TextView tv_j_si_major;
    Intent intent_j_editstudent;

    DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_detailed_student_view);
        tv_j_si_username = findViewById(R.id.tv_v_si_username);
        tv_j_si_fname = findViewById(R.id.tv_v_si_firstname);
        tv_j_si_lname = findViewById(R.id.tv_v_si_lastname);
        tv_j_si_email = findViewById(R.id.tv_v_si_email);
        tv_j_si_age = findViewById(R.id.tv_v_si_age);
        tv_j_si_gpa = findViewById(R.id.tv_v_si_gpa);
        tv_j_si_major = findViewById(R.id.tv_v_si_major);

        dbhelper = new DatabaseHelper(this);


        Intent cameFrom = getIntent();

        //dont think i need to null check here but i will anyway
        if (cameFrom.getSerializableExtra("PassedStudent") != null) {
            Student student = (Student) cameFrom.getSerializableExtra("PassedStudent");
            String majorPrefix = dbhelper.getMajorPrefixFromId(student.getMajorId());
            String majorName = dbhelper.getMajorNameFromId(student.getMajorId());

            setText(tv_j_si_username, student.getUsername());
            setText(tv_j_si_fname, student.getFname());
            setText(tv_j_si_lname, student.getLname());
            setText(tv_j_si_email, student.getEmail());
            setText(tv_j_si_age, String.valueOf(student.getAge()));
            setText(tv_j_si_gpa, String.valueOf(student.getGPA()));
            setText(tv_j_si_major, majorPrefix + " " + majorName);
        }
    }

    private void setText(TextView textView, String text)
    {
        textView.setText(textView.getText() + " " + text);
    }
}
