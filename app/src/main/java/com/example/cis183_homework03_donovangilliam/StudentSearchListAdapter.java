package com.example.cis183_homework03_donovangilliam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentSearchListAdapter extends BaseAdapter
{
    Context context;
    ArrayList<Student> student_list;

    public StudentSearchListAdapter(Context c, ArrayList<Student> al)
    {
        context = c;
        student_list = al;
    }

    @Override
    public int getCount() {
        return student_list.size();
    }

    @Override
    public Object getItem(int i) {
        return student_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (view == null)
        {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService((SearchStudents.LAYOUT_INFLATER_SERVICE));
            view = mInflater.inflate(R.layout.custom_cell_student_list, null);
        }
        TextView name = view.findViewById(R.id.tv_v_cc_sl_name);
        TextView username = view.findViewById(R.id.tv_v_cc_sl_username);
        Student student = student_list.get(i);
        name.setText("Name: "+ student.getFname() + " " + student.getLname());
        username.setText("Username: " + student.getUsername());
        return view;
    }
}
