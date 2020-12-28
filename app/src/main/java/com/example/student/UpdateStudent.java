package com.example.student;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.student.dao.StudentDao;
import com.example.student.model.Student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateStudent extends AppCompatActivity {

    TextView mssv;
    EditText name;
    EditText email;
    EditText address;
    DatePicker birth;
    Intent caller;
    Bundle myBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        caller = getIntent();
        myBundle = caller.getExtras();

        mssv = findViewById(R.id.mssv);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        birth = findViewById(R.id.birth);

        mssv.setText("MSSV: " + myBundle.getString("mssv"));
        name.setText(myBundle.getString("name"));
        email.setText(myBundle.getString("email"));
        address.setText(myBundle.getString("address"));
        try {
            Date date = new SimpleDateFormat("dd-mm-yyyy").parse(myBundle.getString("birth"));
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            birth.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(UpdateStudent.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("ARE YOU SURE ABOUT THAT!!!");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Student student = new Student(
                                        "",
                                        name.getText().toString(),
                                        email.getText().toString(),
                                        myBundle.getString("mssv"),
                                        birth.getDayOfMonth() + "-" + birth.getMonth() + "-" + birth.getYear(),
                                        address.getText().toString(),
                                        false
                                );

                                boolean r = StudentDao.getInstance().update(""+getFilesDir(), student);

                                if (r) {
                                    myBundle.putString("name", student.getName());
                                    myBundle.putString("email", student.getEmail());
                                    myBundle.putString("address", student.getAddress());
                                    myBundle.putString("birth", student.getBirth());
                                    caller.putExtras(myBundle);
                                    setResult(Activity.RESULT_OK, caller);
                                }else {
                                    setResult(Activity.RESULT_CANCELED, caller);
                                }
                                finish();
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

    }
}