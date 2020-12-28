package com.example.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.student.dao.StudentDao;
import com.example.student.model.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewStudent extends AppCompatActivity {
    TextView mssv;
    TextView name;
    TextView email;
    TextView address;
    TextView birth;
    Intent caller;
    Bundle myBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        caller = getIntent();
        myBundle = caller.getExtras();

        mssv = findViewById(R.id.view_mssv);
        name = findViewById(R.id.view_name);
        email = findViewById(R.id.view_email);
        address = findViewById(R.id.view_address);
        birth = findViewById(R.id.view_birth);

        mssv.setText("MSSV: " + myBundle.getString("mssv"));
        name.setText("name: " + myBundle.getString("name"));
        email.setText("email: " + myBundle.getString("email"));
        address.setText("address: " + myBundle.getString("address"));
        birth.setText("birth: " + myBundle.getString("birth"));

    }
}