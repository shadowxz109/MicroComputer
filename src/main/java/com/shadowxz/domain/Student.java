package com.shadowxz.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Student {
    private String studentId;

    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    private String clazz;

    private String picture;

    private String email;

    private String gender;

    public Student(String studentId, String name, String clazz, String picture, String email, String gender) {
        this.studentId = studentId;
        this.name = name;
        this.clazz = clazz;
        this.picture = picture;
        this.email = email;
        this.gender = gender;
    }

    public Student() {
        super();
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz == null ? null : clazz.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }
}