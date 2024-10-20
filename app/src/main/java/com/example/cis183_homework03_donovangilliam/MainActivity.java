package com.example.cis183_homework03_donovangilliam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private static ArrayList<Student> student_list = new ArrayList<>();
    StudentListAdapter adapter = new StudentListAdapter(this, student_list);

    ListView lv_j_m_studentlist;
    Button btn_j_m_addstudent;
    Button btn_j_m_searchstudent;

    Intent intent_j_addstudent;
    Intent intent_j_searchstudent;
    Intent intent_j_studentdetails;

    DatabaseHelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lv_j_m_studentlist = findViewById(R.id.lv_v_m_studentlist);
        btn_j_m_addstudent = findViewById(R.id.btn_v_m_addstudent);
        btn_j_m_searchstudent = findViewById(R.id.btn_v_m_searchstudent);

        intent_j_addstudent = new Intent(MainActivity.this, AddNewStudent.class);
        intent_j_searchstudent = new Intent(MainActivity.this, SearchStudents.class);
        intent_j_studentdetails = new Intent(MainActivity.this, DetailedStudentView.class);

        lv_j_m_studentlist.setAdapter(adapter);

        dbhelper = new DatabaseHelper(this);
        dbhelper.initData();
        dbhelper.fillStudentArray(student_list);

        addNewStudentListener();
        searchStudentsListener();
        studentListTapListener();
        studentListHoldListener();
    }

    private void addNewStudentListener()
    {
        btn_j_m_addstudent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(intent_j_addstudent);
            }
        });
    }

    private void searchStudentsListener()
    {
        btn_j_m_searchstudent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(intent_j_searchstudent);
            }
        });
    }

    private void studentListTapListener()
    {
        lv_j_m_studentlist.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Student student = student_list.get(i);
                intent_j_studentdetails.putExtra("PassedStudent", student);
                startActivity(intent_j_studentdetails);
            }
        });
    }

    private void studentListHoldListener()
    {

    }
}