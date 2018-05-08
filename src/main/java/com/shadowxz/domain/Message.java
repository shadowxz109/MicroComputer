package com.shadowxz.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Message {
    private Integer id;

    private String type;

    private Integer contentId;

    private String status;

    private String receiveId;

    private String content;

    private String remark;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pulishTime;

    public Message(Integer id, String type, Integer contentId, String status, String receiveId, String remark) {
        this.id = id;
        this.type = type;
        this.contentId = contentId;
        this.status = status;
        this.receiveId = receiveId;
        this.remark = remark;
    }

    public Message(String type, Integer contentId, String status, String receiveId, String remark) {
        this.type = type;
        this.contentId = contentId;
        this.status = status;
        this.receiveId = receiveId;
        this.remark = remark;
    }

    public Message() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId == null ? null : receiveId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPulishTime() {
        return pulishTime;
    }

    public void setPulishTime(Date pulishTime) {
        this.pulishTime = pulishTime;
    }
}