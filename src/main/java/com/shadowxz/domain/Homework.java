package com.shadowxz.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Homework {
    private Integer id;

    private String chapterId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pulishTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;

    private String teacherId;

    private String description;

    private List<HomeworkScore> scores;

    private List<HomeworkItem> items;

    public Homework(Integer id, String chapterId, Date pulishTime, Date deadline, String teacherId,String description) {
        this.id = id;
        this.chapterId = chapterId;
        this.pulishTime = pulishTime;
        this.deadline = deadline;
        this.teacherId = teacherId;
        this.description = description;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getPulishTime() {
        return pulishTime;
    }

    public void setPulishTime(Date pulishTime) {
        this.pulishTime = pulishTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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

    public List<HomeworkItem> getItems() {
        return items;
    }

    public void setItems(List<HomeworkItem> items) {
        this.items = items;
    }

    public List<HomeworkScore> getScores() {
        return scores;
    }

    public void setScores(List<HomeworkScore> scores) {
        this.scores = scores;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}