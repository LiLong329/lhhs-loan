package com.lhhs.loan.dao.domain;

import java.util.Date;

public class LoanGroup {
    private Integer lgGroupId;

    private Integer lgDeptId;

    private String lgName;

    private String lgDepict;

    private Date lgTime;

    private String lgStatus;

    private String lgRemark;
    
    /*************临时字段*******************/
    private String ldName;
    

    public String getLdName() {
		return ldName;
	}

	public void setLdName(String ldName) {
		this.ldName = ldName;
	}

	public Integer getLgGroupId() {
        return lgGroupId;
    }

    public void setLgGroupId(Integer lgGroupId) {
        this.lgGroupId = lgGroupId;
    }

    public Integer getLgDeptId() {
        return lgDeptId;
    }

    public void setLgDeptId(Integer lgDeptId) {
        this.lgDeptId = lgDeptId;
    }

    public String getLgName() {
        return lgName;
    }

    public void setLgName(String lgName) {
        this.lgName = lgName == null ? null : lgName.trim();
    }

    public String getLgDepict() {
        return lgDepict;
    }

    public void setLgDepict(String lgDepict) {
        this.lgDepict = lgDepict == null ? null : lgDepict.trim();
    }

    public Date getLgTime() {
        return lgTime;
    }

    public void setLgTime(Date lgTime) {
        this.lgTime = lgTime;
    }

    public String getLgStatus() {
        return lgStatus;
    }

    public void setLgStatus(String lgStatus) {
        this.lgStatus = lgStatus == null ? null : lgStatus.trim();
    }

    public String getLgRemark() {
        return lgRemark;
    }

    public void setLgRemark(String lgRemark) {
        this.lgRemark = lgRemark == null ? null : lgRemark.trim();
    }
}