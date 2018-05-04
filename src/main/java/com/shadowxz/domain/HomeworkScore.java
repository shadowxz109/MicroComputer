package com.shadowxz.domain;

public class HomeworkScore {
    private Integer id;

    private Integer homeworkId;

    private String studentId;

    private Float scores;

    private String status;

    private Student student;

    public HomeworkScore(Integer id, Integer homeworkId, String studentId, Float scores, String status) {
        this.id = id;
        this.homeworkId = homeworkId;
        this.studentId = studentId;
        this.scores = scores;
        this.status = status;
    }

    public HomeworkScore(Integer homeworkId, String studentId, Float scores, String status) {
        this.homeworkId = homeworkId;
        this.studentId = studentId;
        this.scores = scores;
        this.status = status;
    }

    public HomeworkScore() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public Float getScores() {
        return scores;
    }

    public void setScores(Float scores) {
        this.scores = scores;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}