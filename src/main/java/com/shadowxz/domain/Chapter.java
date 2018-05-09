package com.shadowxz.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Chapter {
    private String chapterId;

    private String chapterName;

    private String fatherId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Chapter> children;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Homework homework;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Courseware courseware;

    public Chapter(String chapterId, String chapterName, String fatherId) {
        this.chapterId = chapterId;
        this.chapterName = chapterName;
        this.fatherId = fatherId;
    }

    public Chapter() {
        super();
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId == null ? null : chapterId.trim();
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName == null ? null : chapterName.trim();
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId == null ? null : fatherId.trim();
    }

    public List<Chapter> getChildren() {
        return children;
    }

    public void setChildren(List<Chapter> children) {
        this.children = children;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    public Courseware getCourseware() {
        return courseware;
    }

    public void setCourseware(Courseware courseware) {
        this.courseware = courseware;
    }
}