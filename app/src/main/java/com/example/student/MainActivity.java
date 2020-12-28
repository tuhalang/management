package com.example.student;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.student.adapter.StudentAdapter;
import com.example.student.dao.StudentDao;
import com.example.student.model.Student;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    List<Student> items;
    String[] colors;
    Random generator;
    ListView listView;
    StudentAdapter adapter;
    SearchView txtSearchValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = StudentDao.getInstance().getAllStudent(""+getFilesDir());

        adapter = new StudentAdapter(this, items);
        listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
        listView.setLongClickable(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode){
                case 1121:
                    break;
                case 1122:
                    if (resultCode == Activity.RESULT_OK){
                        Bundle myBundle = data.getExtras();

                        String mssv = myBundle.getString("mssv");

                        for(Student student: items){
                            if (student.getMssv().equals(mssv)){
                                student.setAddress(myBundle.getString("address"));
                                student.setName(myBundle.getString("name"));
                                student.setEmail(myBundle.getString("email"));
                                student.setBirth(myBundle.getString("birth"));
                                break;
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        txtSearchValue = (SearchView) menu.findItem(R.id.search)
                .getActionView();
        // set searchView listener (look for text changes, and submit event)
        txtSearchValue.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.equals("")){
                    items = StudentDao.getInstance().getAllStudent("" + getFilesDir());
                    adapter = new StudentAdapter(MainActivity.this, items);
                    listView.setAdapter(adapter);
                }else{
                    items = StudentDao.getInstance().searchStudent("" + getFilesDir(), query);
                    adapter = new StudentAdapter(MainActivity.this, items);
                    listView.setAdapter(adapter);
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    items = StudentDao.getInstance().getAllStudent("" + getFilesDir());
                    adapter = new StudentAdapter(MainActivity.this, items);
                    listView.setAdapter(adapter);
                }else{
                    items = StudentDao.getInstance().searchStudent("" + getFilesDir(), newText);
                    adapter = new StudentAdapter(MainActivity.this, items);
                    listView.setAdapter(adapter);
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.add){
            Intent myIntent = new Intent(MainActivity.this, AddStudent.class);
            startActivity(myIntent);
        }else if(id == R.id.add_random){
            StudentDao.getInstance().ceateDB(""+getFilesDir());
            StudentDao.getInstance().ceateRandomDB(""+getFilesDir(), 10);
            items = StudentDao.getInstance().getAllStudent(""+getFilesDir());
            adapter = new StudentAdapter(this, items);
            listView.setAdapter(adapter);
        }
        return true;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Student selectedStudent = items.get(info.position);

        if(item.getTitle().equals("select")){
            selectedStudent.setChecked(!selectedStudent.isChecked());
            adapter.notifyDataSetChanged();
        }else if(item.getTitle().equals("select all")){
            for (Student student : items){
                student.setChecked(true);
            }
            adapter.notifyDataSetChanged();
        } else if(item.getTitle().equals("view")){
            Intent myIntent = new Intent(MainActivity.this, ViewStudent.class);
            Bundle myBundle = new Bundle();

            myBundle.putString("mssv", selectedStudent.getMssv());
            myBundle.putString("name", selectedStudent.getName());
            myBundle.putString("email", selectedStudent.getEmail());
            myBundle.putString("address", selectedStudent.getAddress());
            myBundle.putString("birth", selectedStudent.getBirth());

            myIntent.putExtras(myBundle);
            startActivityForResult(myIntent, 1121);
        }else if(item.getTitle().equals("update")){
            Intent myIntent = new Intent(MainActivity.this, UpdateStudent.class);
            Bundle myBundle = new Bundle();

            myBundle.putString("mssv", selectedStudent.getMssv());
            myBundle.putString("name", selectedStudent.getName());
            myBundle.putString("email", selectedStudent.getEmail());
            myBundle.putString("address", selectedStudent.getAddress());
            myBundle.putString("birth", selectedStudent.getBirth());

            myIntent.putExtras(myBundle);
            startActivityForResult(myIntent, 1122);
        }else if(item.getTitle().equals("delete")){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("ARE YOU SURE ABOUT THAT!!!");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            boolean r = StudentDao.getInstance().deleteStudent(""+getFilesDir(), selectedStudent);
                            if (r) {
                                items.remove(selectedStudent);
                                adapter.notifyDataSetChanged();
                            }
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
        }else if(item.getTitle().equals("delete selected")){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("ARE YOU SURE ABOUT THAT!!!");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            for (int i = 0; i < items.size(); i ++){
                                Student student = items.get(i);
                                if(student.isChecked()){
                                    boolean r = StudentDao.getInstance().deleteStudent(""+getFilesDir(), student);
                                    if (r) {
                                        items.remove(student);
                                        i -= 1;
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
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
        }else{
            return false;
        }
        return true;
    }
}