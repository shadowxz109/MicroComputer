package com.shadowxz.domain;

public class Teacher {
    private String teacherId;

    private String password;

    private String picture;

    private String name;

    public Teacher(String teacherId, String password, String picture, String name) {
        this.teacherId = teacherId;
        this.password = password;
        this.picture = picture;
        this.name = name;
    }

    public Teacher() {
        super();
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId == null ? null : teacherId.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}