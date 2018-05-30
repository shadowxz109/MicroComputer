package com.shadowxz.domain;

import java.util.List;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/30 11:10
 * @Modified by:
 */
public class QuestionAverageScore {

    private String description;

    private List<Double> scores;

    private List<String> questionNums;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Double> getScores() {
        return scores;
    }

    public void setScoreList(List<Double> scoreList) {
        this.scores = scoreList;
    }

    public List<String> getQuestionNums() {
        return questionNums;
    }

    public void setQuestionNumList(List<String> questionNumList) {
        this.questionNums = questionNumList;
    }
}
