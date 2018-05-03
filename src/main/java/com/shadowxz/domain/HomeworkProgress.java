package com.shadowxz.domain;

import java.util.Date;

public class HomeworkProgress {
    private Integer id;

    private String homeworkId;

    private String studentId;

    private String questionNum;

    private String answer;

    private String status;

    private Float score;

    private Date finishDate;

    private String type;

    private HomeworkItem item;

    public HomeworkProgress(Integer id, String homeworkId, String studentId, String questionNum, String answer, String status, Float score, Date finishDate, String type) {
        this.id = id;
        this.homeworkId = homeworkId;
        this.studentId = studentId;
        this.questionNum = questionNum;
        this.answer = answer;
        this.status = status;
        this.score = score;
        this.finishDate = finishDate;
        this.type = type;
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

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId == null ? null : homeworkId.trim();
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId == null ? null : studentId.trim();
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum == null ? null : questionNum.trim();
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

    public HomeworkItem getItem() {
        return item;
    }

    public void setItem(HomeworkItem item) {
        this.item = item;
    }
}