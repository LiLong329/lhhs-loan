package com.lhhs.loan.dao.domain;

import java.util.Date;

public class LoanOrderCredentials {
    private Long orderCredentialsNo;

    private String orderProductId;

    private String orderNo;

    private String orderCredentialsName;
    
    private String orderCredentialsEnglishName;

    private String orderCredentialsType;

    private String orderCredentialsRequired;

    private String orderCredentialsDes;

    private String orderCredentialsStatus;

    private Date orderCredentialsUploadTime;

    public Long getOrderCredentialsNo() {
        return orderCredentialsNo;
    }

    public void setOrderCredentialsNo(Long orderCredentialsNo) {
        this.orderCredentialsNo = orderCredentialsNo;
    }

    public String getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId == null ? null : orderProductId.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getOrderCredentialsName() {
        return orderCredentialsName;
    }

    public void setOrderCredentialsName(String orderCredentialsName) {
        this.orderCredentialsName = orderCredentialsName == null ? null : orderCredentialsName.trim();
    }

    public String getOrderCredentialsEnglishName() {
		return orderCredentialsEnglishName;
	}

	public void setOrderCredentialsEnglishName(String orderCredentialsEnglishName) {
		this.orderCredentialsEnglishName = orderCredentialsEnglishName;
	}

	public String getOrderCredentialsType() {
        return orderCredentialsType;
    }

    public void setOrderCredentialsType(String orderCredentialsType) {
        this.orderCredentialsType = orderCredentialsType == null ? null : orderCredentialsType.trim();
    }

    public String getOrderCredentialsRequired() {
        return orderCredentialsRequired;
    }

    public void setOrderCredentialsRequired(String orderCredentialsRequired) {
        this.orderCredentialsRequired = orderCredentialsRequired == null ? null : orderCredentialsRequired.trim();
    }

    public String getOrderCredentialsDes() {
        return orderCredentialsDes;
    }

    public void setOrderCredentialsDes(String orderCredentialsDes) {
        this.orderCredentialsDes = orderCredentialsDes == null ? null : orderCredentialsDes.trim();
    }

    public String getOrderCredentialsStatus() {
        return orderCredentialsStatus;
    }

    public void setOrderCredentialsStatus(String orderCredentialsStatus) {
        this.orderCredentialsStatus = orderCredentialsStatus == null ? null : orderCredentialsStatus.trim();
    }

    public Date getOrderCredentialsUploadTime() {
        return orderCredentialsUploadTime;
    }

    public void setOrderCredentialsUploadTime(Date orderCredentialsUploadTime) {
        this.orderCredentialsUploadTime = orderCredentialsUploadTime;
    }
}