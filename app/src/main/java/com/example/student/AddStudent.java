package com.example.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student.dao.StudentDao;
import com.example.student.model.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddStudent extends AppCompatActivity {

    EditText mssv;
    EditText name;
    EditText email;
    EditText address;
    DatePicker birth;
    Intent caller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        caller = getIntent();

        mssv = findViewById(R.id.add_mssv);
        name = findViewById(R.id.add_name);
        email = findViewById(R.id.add_email);
        address = findViewById(R.id.add_address);
        birth = findViewById(R.id.add_birth);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student(
                        "",
                        name.getText().toString(),
                        email.getText().toString(),
                        mssv.getText().toString(),
                        birth.getDayOfMonth() + "-" + birth.getMonth() + "-" + birth.getYear(),
                        address.getText().toString(),
                        false
                );

                boolean r = StudentDao.getInstance().addStudent("" + getFilesDir(), student);

                if (r) {
                    Toast.makeText(getApplicationContext(), "Insert success" , Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Insert fail" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}