package com.example.student.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.student.model.Student;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import io.bloco.faker.Faker;

public class StudentDao {

    private static StudentDao instance = new StudentDao();

    private StudentDao() {

    }

    public static StudentDao getInstance() {
        return instance;
    }

    SQLiteDatabase db;
    Random generator = new Random(1);

    String[] colors = new String[]{
            "#fff44336", "#ffe91e63", "#ff9c27b0", "#ff673ab7",
            "#ff3f51b5", "#ff2196f3", "#ff03a9f4", "#ff00bcd4",
            "#ff009688", "#ff4caf50", "#ff8bc34a", "#ffcddc39",
            "#ffffeb3b", "#ffffc107", "#ffff9800", "#ffff5722",
            "#ff795548", "#ff9e9e9e", "#ff607d8b", "#ff333333"
    };

    public void ceateDB(String fileDir) {
        String dataPath = fileDir + "/mydata";
        db = SQLiteDatabase.openDatabase(dataPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        db.beginTransaction();
        try {
            // Thuc hien cac cau truy van
            // Tao bang
            db.execSQL("DROP TABLE IF EXISTS student");
            db.execSQL("create table if not exists student(" +
                    "mssv text primary key," +
                    "name text," +
                    "birth text," +
                    "address text," +
                    "email text);");

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void ceateRandomDB(String fileDir, int num) {
        String dataPath = fileDir + "/mydata";
        db = SQLiteDatabase.openDatabase(dataPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        db.beginTransaction();
        try {
            Date date;
            Faker faker = new Faker();
            DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
            ContentValues cv = new ContentValues();

            for (int i = 0; i < num; i++) {
                cv.put("mssv", String.valueOf(20170000 + generator.nextInt(9999)));
                cv.put("name", faker.name.name());
                cv.put("birth", dateFormat.format(faker.date.birthday()));
                cv.put("address", faker.address.city());
                cv.put("email", faker.name.firstName() + "@gmail.com");
                long r = db.insert("student", null, cv);
                Log.v("TAG", "Result: " + r);
                cv.clear();
            }
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public boolean addStudent(String fileDir, Student student) {
        String dataPath = fileDir + "/mydata";
        db = SQLiteDatabase.openDatabase(dataPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        long r = 0;

        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();

            cv.put("mssv", student.getMssv());
            cv.put("name", student.getName());
            cv.put("birth", student.getBirth());
            cv.put("address", student.getAddress());
            cv.put("email", student.getEmail());
            r = db.insert("student", null, cv);
            Log.v("TAG", "Result: " + r);
            cv.clear();

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
            if (r == -1){
                return false;
            }else {
                return true;
            }
        }
    }

    public List<Student> getAllStudent(String fileDir) {
        String dataPath = fileDir + "/mydata";
        db = SQLiteDatabase.openDatabase(dataPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        List<Student> students = new ArrayList<>();

        db.beginTransaction();
        try {
            String sql = "select * from student";
            Cursor cs = db.rawQuery(sql, null);

            cs.moveToFirst();
            while (cs.moveToNext()) {
                String mssv = cs.getString(cs.getColumnIndex("mssv"));
                String name = cs.getString(cs.getColumnIndex("name"));
                String birth = cs.getString(cs.getColumnIndex("birth"));
                String address = cs.getString(cs.getColumnIndex("address"));
                String email = cs.getString(cs.getColumnIndex("email"));

                Student student = new Student(colors[generator.nextInt(colors.length - 1)], name, email, mssv, birth, address, false);
                students.add(student);
            }

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.endTransaction();
        }
        return students;
    }

    public List<Student> searchStudent(String fileDir, String search) {
        String dataPath = fileDir + "/mydata";
        db = SQLiteDatabase.openDatabase(dataPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);

        List<Student> students = new ArrayList<>();

        db.beginTransaction();
        try {
            String sql = "select * from student where mssv like ? or name like ?";
            String[] args = {'%' + search + '%', '%' + search + '%'};
            Cursor cs = db.rawQuery(sql, args);

            cs.moveToPosition(-1);
            while (cs.moveToNext()) {
                String mssv = cs.getString(cs.getColumnIndex("mssv"));
                String name = cs.getString(cs.getColumnIndex("name"));
                String birth = cs.getString(cs.getColumnIndex("birth"));
                String address = cs.getString(cs.getColumnIndex("address"));
                String email = cs.getString(cs.getColumnIndex("email"));

                Student student = new Student(colors[generator.nextInt(colors.length - 1)], name, email, mssv, birth, address, false);
                students.add(student);
            }

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            db.endTransaction();
        }
        return students;
    }

    public boolean deleteStudent(String fileDir, Student student) {
        String dataPath = fileDir + "/mydata";
        db = SQLiteDatabase.openDatabase(dataPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        int r = 0;

        List<Student> students = new ArrayList<>();

        db.beginTransaction();
        try {
            String[] args = {student.getMssv()};
            r = db.delete("student", "mssv = ?", args);
            Log.v("TAG", "Result: " + r);
            db.setTransactionSuccessful();

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
            if (r == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean update(String fileDir, Student student) {
        String dataPath = fileDir + "/mydata";
        db = SQLiteDatabase.openDatabase(dataPath, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        int r = 0;

        List<Student> students = new ArrayList<>();

        db.beginTransaction();
        try {
            ContentValues cv = new ContentValues();
            cv.put("name", student.getName());
            cv.put("email", student.getEmail());
            cv.put("birth", student.getBirth());
            cv.put("address", student.getAddress());
            String[] args = {student.getMssv()};
            r = db.update("student", cv, "mssv = ?", args);
            Log.v("TAG", "Result: " + r);
            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
            if (r == 1) {
                return true;
            } else {
                return false;
            }
        }
    }

}
