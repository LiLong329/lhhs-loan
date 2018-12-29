package com.lhhs.loan.dao.domain;

import java.util.Date;

public class LoanTimerTask {
    private Long taskId; //任务表id 自增

    private String taskUrl;//任务url地址

    private String taskWorkId;//具体任务的id 例:车产id 房产id 报单id

    private String taskRequestType;//请求业务类型

    private String taskRequestMethed;//任务请求方法类型

    private String taskState;//任务跑批状态 0:失败1:成功

    private Date taskTime;//任务新建时间和更新时间

    private String taskParameter;//跑批任务请求参数

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskUrl() {
        return taskUrl;
    }

    public void setTaskUrl(String taskUrl) {
        this.taskUrl = taskUrl == null ? null : taskUrl.trim();
    }

    public String getTaskWorkId() {
        return taskWorkId;
    }

    public void setTaskWorkId(String taskWorkId) {
        this.taskWorkId = taskWorkId == null ? null : taskWorkId.trim();
    }

    public String getTaskRequestType() {
        return taskRequestType;
    }

    public void setTaskRequestType(String taskRequestType) {
        this.taskRequestType = taskRequestType == null ? null : taskRequestType.trim();
    }

    public String getTaskRequestMethed() {
        return taskRequestMethed;
    }

    public void setTaskRequestMethed(String taskRequestMethed) {
        this.taskRequestMethed = taskRequestMethed == null ? null : taskRequestMethed.trim();
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState == null ? null : taskState.trim();
    }

    public Date getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskParameter() {
        return taskParameter;
    }

    public void setTaskParameter(String taskParameter) {
        this.taskParameter = taskParameter == null ? null : taskParameter.trim();
    }
}