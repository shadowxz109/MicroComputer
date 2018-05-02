package com.shadowxz.domain;

public class HomeworkItem {
    private Integer id;

    private String questionNum;

    private String detail;

    private String type;

    private String answer;

    private String remark;

    private Float score;

    private Integer homeworkId;

    public HomeworkItem(Integer id, String questionNum, String detail, String type, String answer, String remark, Float score, Integer homeworkId) {
        this.id = id;
        this.questionNum = questionNum;
        this.detail = detail;
        this.type = type;
        this.answer = answer;
        this.remark = remark;
        this.score = score;
        this.homeworkId = homeworkId;
    }

    public HomeworkItem() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(String questionNum) {
        this.questionNum = questionNum == null ? null : questionNum.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }
}