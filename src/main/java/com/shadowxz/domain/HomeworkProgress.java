package com.shadowxz.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

public class HomeworkProgress {
    private Integer id;

    private String studentId;

    private String answer;

    private String status;

    private Float score;

    private Date finishDate;

    private String type;

    private Integer itemId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Student student;

    public HomeworkProgress(Integer id, String studentId, String answer, String status, Float score, Date finishDate, String type, Integer itemId) {
        this.id = id;
        this.studentId = studentId;
        this.answer = answer;
        this.status = status;
        this.score = score;
        this.finishDate = finishDate;
        this.type = type;
        this.itemId = itemId;
    }

    public HomeworkProgress(String studentId, String answer, String status, Float score, Date finishDate, String type, Integer itemId) {
        this.studentId = studentId;
        this.answer = answer;
        this.status = status;
        this.score = score;
        this.finishDate = finishDate;
        this.type = type;
        this.itemId = itemId;
    }

    public HomeworkProgress() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}