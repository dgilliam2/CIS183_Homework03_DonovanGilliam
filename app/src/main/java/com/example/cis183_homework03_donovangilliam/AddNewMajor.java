package com.example.cis183_homework03_donovangilliam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNewMajor extends AppCompatActivity {

    TextView tv_j_am_duplicate;
    TextView tv_j_am_error;


    EditText et_j_am_majorprefix;
    EditText et_j_am_majorname;

    Button btn_j_am_addmajor;

    Intent intent_j_am_confirm;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_major);

        tv_j_am_error = findViewById(R.id.tv_v_am_error);
        tv_j_am_duplicate = findViewById(R.id.tv_v_am_duplicate);

        et_j_am_majorprefix = findViewById(R.id.et_v_am_majorprefix);
        et_j_am_majorname = findViewById(R.id.et_v_am_majorname);

        btn_j_am_addmajor = findViewById(R.id.btn_v_am_addmajor);

        intent_j_am_confirm = new Intent(AddNewMajor.this, AddNewStudent.class);

        dbHelper = new DatabaseHelper(this);

        addMajorConfirmListener();


    }

    private void addMajorConfirmListener()
    {
        btn_j_am_addmajor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (fieldsNotNull())
                {
                    if (!dbHelper.findDuplicateMajors(et_j_am_majorname.getText().toString()))
                    {
                        Major major = new Major();
                        major.setMajorName(et_j_am_majorname.getText().toString());
                        major.setMajorPrefix(et_j_am_majorprefix.getText().toString());
                        dbHelper.addMajorToDb(major);
                        startActivity(intent_j_am_confirm);
                    }
                    else
                    {
                        tv_j_am_duplicate.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    tv_j_am_error.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean fieldsNotNull()
    {
        if (!et_j_am_majorprefix.getText().toString().isEmpty()
            && !et_j_am_majorname.getText().toString().isEmpty())
        {
            return true;
        }
        return false;
    }
}