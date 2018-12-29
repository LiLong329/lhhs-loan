package com.lhhs.loan.dao.domain;

import java.util.Date;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanTaskList extends BaseObject{
   
	private static final long serialVersionUID = 5223029947539517663L;

	private Integer taskId;

    private String taskName;

    private String description;

    private Date playDate;

    private Date beginDate;

    private Date endDate;

    private String taskTotal;

    private String doTotal;

    private String failTotal;

    private String type;

    private Integer delay;

    private String status;

    private Date createDate;

    private Date lastModifyTime;

    private String code;

    private String msg;

    private String incrementStatus;

    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;
    //
    private Date oldLastModifyTime;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getPlayDate() {
        return playDate;
    }

    public void setPlayDate(Date playDate) {
        this.playDate = playDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTaskTotal() {
        return taskTotal;
    }

    public void setTaskTotal(String taskTotal) {
        this.taskTotal = taskTotal == null ? null : taskTotal.trim();
    }

    public String getDoTotal() {
        return doTotal;
    }

    public void setDoTotal(String doTotal) {
        this.doTotal = doTotal == null ? null : doTotal.trim();
    }

    public String getFailTotal() {
        return failTotal;
    }

    public void setFailTotal(String failTotal) {
        this.failTotal = failTotal == null ? null : failTotal.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getIncrementStatus() {
        return incrementStatus;
    }

    public void setIncrementStatus(String incrementStatus) {
        this.incrementStatus = incrementStatus == null ? null : incrementStatus.trim();
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4 == null ? null : field4.trim();
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5 == null ? null : field5.trim();
    }

	public Date getOldLastModifyTime() {
		return oldLastModifyTime;
	}

	public void setOldLastModifyTime(Date oldLastModifyTime) {
		this.oldLastModifyTime = oldLastModifyTime;
	}


    
}