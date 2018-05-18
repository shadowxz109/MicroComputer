package com.shadowxz.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Courseware {

    private Integer id;

    private String name;

    private String chapterId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date uploadDate;

    private Integer downloadTimes;

    private String file;

    private String teacherId;

    public Courseware(Integer id, String name, String chapterId, Date uploadDate, Integer downloadTimes, String file,String teacherId) {
        this.id = id;
        this.name = name;
        this.chapterId = chapterId;
        this.uploadDate = uploadDate;
        this.downloadTimes = downloadTimes;
        this.file = file;
        this.teacherId = teacherId;
    }

    public Courseware() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId == null ? null : chapterId.trim();
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(Integer downloadTimes) {
        this.downloadTimes = downloadTimes;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file == null ? null : file.trim();
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}