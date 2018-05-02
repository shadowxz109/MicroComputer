package com.shadowxz.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Homework {
    private Integer id;

    private String chapterId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date pulishTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date deadline;

    private String teacherId;

    public Homework(Integer id, String chapterId, Date pulishTime, Date deadline, String teacherId) {
        this.id = id;
        this.chapterId = chapterId;
        this.pulishTime = pulishTime;
        this.deadline = deadline;
        this.teacherId = teacherId;
    }

    public Homework() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId == null ? null : chapterId.trim();
    }

    public Date getPulishTime() {
        return pulishTime;
    }

    public void setPulishTime(Date pulishTime) {
        this.pulishTime = pulishTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId == null ? null : teacherId.trim();
    }
}