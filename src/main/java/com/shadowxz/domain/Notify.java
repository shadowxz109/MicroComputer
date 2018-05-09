package com.shadowxz.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Notify {
    private Integer id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pulishTime;

    private String description;

    private String teacherId;

    private String remark;

    public Notify(Integer id, Date pulishTime, String description, String teacherId, String remark) {
        this.id = id;
        this.pulishTime = pulishTime;
        this.description = description;
        this.teacherId = teacherId;
        this.remark = remark;
    }

    public Notify() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getPulishTime() {
        return pulishTime;
    }

    public void setPulishTime(Date pulishTime) {
        this.pulishTime = pulishTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId == null ? null : teacherId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}