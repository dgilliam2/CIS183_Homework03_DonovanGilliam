package com.example.cis183_homework03_donovangilliam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchStudents extends AppCompatActivity
{

    private ArrayList<Student> student_list = new ArrayList<>();
    StudentSearchListAdapter searchListAdapter = new StudentSearchListAdapter(this, student_list);

    EditText et_j_ss_fname;
    EditText et_j_ss_lname;
    EditText et_j_ss_username;
    EditText et_j_ss_gpamin;
    EditText et_j_ss_gpamax;

    Button btn_j_ss_query;
    Button btn_j_ss_return;

    CheckBox cb_j_ss_fname;
    CheckBox cb_j_ss_lname;
    CheckBox cb_j_ss_username;
    CheckBox cb_j_ss_major;
    CheckBox cb_j_ss_gpa;

    Spinner spn_j_ss_majors;

    ListView lv_j_ss_studentquery;

    Intent intent_j_ss_return;

    DatabaseHelper dbHelper;

    ArrayList<String> major_list = new ArrayList<>();

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_students);

        et_j_ss_fname = findViewById(R.id.et_v_ss_fname);
        et_j_ss_lname = findViewById(R.id.et_v_ss_lname);
        et_j_ss_username = findViewById(R.id.et_v_ss_username);
        et_j_ss_gpamin = findViewById(R.id.et_v_ss_gpamin);
        et_j_ss_gpamax = findViewById(R.id.et_v_ss_gpamax);

        btn_j_ss_query = findViewById(R.id.btn_v_ss_query);
        btn_j_ss_return = findViewById(R.id.btn_v_ss_return);

        cb_j_ss_fname = findViewById(R.id.cb_v_ss_fname);
        cb_j_ss_lname = findViewById(R.id.cb_v_ss_lname);
        cb_j_ss_username = findViewById(R.id.cb_v_ss_username);
        cb_j_ss_major = findViewById(R.id.cb_v_ss_major);
        cb_j_ss_gpa = findViewById(R.id.cb_v_ss_gpa);

        spn_j_ss_majors = findViewById(R.id.spn_v_ss_majors);

        lv_j_ss_studentquery = findViewById(R.id.lv_v_ss_studentquery);

        lv_j_ss_studentquery.setAdapter(searchListAdapter);

        intent_j_ss_return = new Intent(SearchStudents.this, MainActivity.class);

        dbHelper = new DatabaseHelper(this);
        dbHelper.fillMajorNameArray(major_list);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, major_list);
        spn_j_ss_majors.setAdapter(arrayAdapter);

        querySelectionListener();
        returnButtonListener();
    }

    private void querySelectionListener()
    {
        btn_j_ss_query.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                studentQuery();
            }
        });
    }

    private void returnButtonListener()
    {
        btn_j_ss_return.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(intent_j_ss_return);
            }
        });
    }

    //check if anything is checked
    //if at least one thing is checked, they can query
    private boolean checkCheckBoxes()
    {
        if (cb_j_ss_fname.isChecked()
                || cb_j_ss_lname.isChecked()
                || cb_j_ss_username.isChecked()
                || cb_j_ss_major.isChecked()
                || cb_j_ss_gpa.isChecked())
        {
            return true;
        }
        return false;
    }

    //this is the worst piece of code in this program, i need to study how to do boolean operations better
    //the SQL statements are fine but figuring out the boolean logic for this gave me a headache
    private void studentQuery()
    {
        if (checkCheckBoxes())
        {
            String sql_statement = "SELECT * FROM Students WHERE 1=1";
            //this boolean is used to check if the query is valid
            //for example, if I select the "By GPA" checkbox, but I do not enter anything, it is
            //not valid. This is to prevent the above SQL statement from executing, which will return
            //the entire table.
            //this is what gave me the most problems.
            Boolean validQuery = false;

            if (cb_j_ss_fname.isChecked() && !et_j_ss_fname.getText().toString().isEmpty())
            {
                sql_statement += " AND Fname = '" + et_j_ss_fname.getText().toString() + "'";
                validQuery = true;
            }

            if (cb_j_ss_lname.isChecked() && !et_j_ss_lname.getText().toString().isEmpty())
            {
                sql_statement += " AND Lname = '" + et_j_ss_lname.getText().toString() + "'";
                validQuery = true;
            }

            if (cb_j_ss_username.isChecked() && !et_j_ss_username.getText().toString().isEmpty())
            {
                sql_statement += " AND Username = '" + et_j_ss_username.getText().toString() + "'";
                validQuery = true;
            }

            if (cb_j_ss_major.isChecked())
            {
                sql_statement += " AND MajorId = " + dbHelper.getMajorIdFromName(spn_j_ss_majors.getSelectedItem().toString());
                validQuery = true;
            }

            if (cb_j_ss_gpa.isChecked() && (!et_j_ss_gpamin.getText().toString().isEmpty() && !et_j_ss_gpamax.getText().toString().isEmpty()))
            {
                sql_statement += " AND GPA BETWEEN " + Float.parseFloat(et_j_ss_gpamin.getText().toString()) + " AND " + Float.parseFloat(et_j_ss_gpamax.getText().toString());
                validQuery = true;
            }

            if (validQuery)
            {
                Log.d("SQL Statement", sql_statement);
                dbHelper.filterStudents(sql_statement, student_list);
                searchListAdapter.notifyDataSetChanged();
            }
            else
            {
                Toast.makeText(SearchStudents.this,"Please fill out all selected fields.", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(SearchStudents.this, "Please select one filter.", Toast.LENGTH_SHORT).show();
        }
    }
}