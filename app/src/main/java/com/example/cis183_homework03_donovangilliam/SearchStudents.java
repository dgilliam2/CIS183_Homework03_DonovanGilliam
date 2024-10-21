package com.example.cis183_homework03_donovangilliam;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SearchStudents extends AppCompatActivity
{
    EditText et_j_ss_fname;
    EditText et_j_ss_lname;
    EditText et_j_ss_username;
    EditText et_j_ss_gpa;

    CheckBox cb_j_ss_name;
    CheckBox cb_j_ss_username;
    CheckBox cb_j_ss_major;
    CheckBox cb_j_ss_gpa;

    Spinner spn_j_ss_majors;

    ListView lv_j_ss_studentquery;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_students);

        et_j_ss_fname = findViewById(R.id.et_v_ss_fname);
        et_j_ss_lname = findViewById(R.id.et_v_ss_lname);
        et_j_ss_username = findViewById(R.id.et_v_ss_username);

        cb_j_ss_name = findViewById(R.id.cb_v_ss_name);
        cb_j_ss_username = findViewById(R.id.cb_v_ss_username);
        cb_j_ss_major = findViewById(R.id.cb_v_ss_major);
        cb_j_ss_gpa = findViewById(R.id.cb_v_ss_gpa);

        spn_j_ss_majors = findViewById(R.id.spn_v_ss_majors);

        lv_j_ss_studentquery = findViewById(R.id.lv_v_ss_studentquery);




    }
}