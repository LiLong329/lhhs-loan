package com.lhhs.loan.dao.domain;

import java.util.Date;

public class LoanExTask {
    private Long letTaskid;

    private Integer letNode;

    private Integer letNodeStatus;

    private String letEmployeename;

    private Date letTime;

    private Integer letResult;

    private Integer letEmployeeno;

    private String letDeclarationformid;
    private String letSuggestion;
    private String letRemark;

    public Long getLetTaskid() {
        return letTaskid;
    }

    public void setLetTaskid(Long letTaskid) {
        this.letTaskid = letTaskid;
    }

    public Integer getLetNode() {
        return letNode;
    }

    public void setLetNode(Integer letNode) {
        this.letNode = letNode;
    }

    public Integer getLetNodeStatus() {
        return letNodeStatus;
    }

    public void setLetNodeStatus(Integer letNodeStatus) {
        this.letNodeStatus = letNodeStatus;
    }

    public String getLetEmployeename() {
        return letEmployeename;
    }

    public void setLetEmployeename(String letEmployeename) {
        this.letEmployeename = letEmployeename == null ? null : letEmployeename.trim();
    }

    public Date getLetTime() {
        return letTime;
    }

    public void setLetTime(Date letTime) {
        this.letTime = letTime;
    }

    public Integer getLetResult() {
        return letResult;
    }

    public void setLetResult(Integer letResult) {
        this.letResult = letResult;
    }

    public Integer getLetEmployeeno() {
        return letEmployeeno;
    }

    public void setLetEmployeeno(Integer letEmployeeno) {
        this.letEmployeeno = letEmployeeno;
    }

    public String getLetDeclarationformid() {
        return letDeclarationformid;
    }

    public void setLetDeclarationformid(String letDeclarationformid) {
        this.letDeclarationformid = letDeclarationformid == null ? null : letDeclarationformid.trim();
    }

	public String getLetSuggestion() {
		return letSuggestion;
	}

	public void setLetSuggestion(String letSuggestion) {
		this.letSuggestion = letSuggestion;
	}

	public String getLetRemark() {
		return letRemark;
	}

	public void setLetRemark(String letRemark) {
		this.letRemark = letRemark;
	}
    
}