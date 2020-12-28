package com.example.student.model;

import java.util.Date;

public class Student {
    String avatarColor;
    String name;
    String email;
    String mssv;
    String birth;
    String address;

    boolean checked;

    public Student(String avatarColor, String name, String email, String mssv, String birth, String address, boolean checked) {
        this.avatarColor = avatarColor;
        this.name = name;
        this.email = email;
        this.mssv = mssv;
        this.birth = birth;
        this.address = address;
        this.checked = checked;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarColor() {
        return avatarColor;
    }

    public void setAvatarColor(String avatarColor) {
        this.avatarColor = avatarColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
