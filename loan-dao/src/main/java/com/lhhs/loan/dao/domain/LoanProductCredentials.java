package com.lhhs.loan.dao.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoanProductCredentials {
    private String proCredentialsNo;

    private String productId;

    private String proCredentialsName;
    
    private String proEnglishName;

    private String proCrendentialsType;

    private String proRequired;

    private String proStatus;

    private String proCredentialsDes;

    private Date proTime;

    public String getProCredentialsNo() {
        return proCredentialsNo;
    }

    public void setProCredentialsNo(String proCredentialsNo) {
        this.proCredentialsNo = proCredentialsNo == null ? null : proCredentialsNo.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProCredentialsName() {
        return proCredentialsName;
    }

    public void setProCredentialsName(String proCredentialsName) {
        this.proCredentialsName = proCredentialsName == null ? null : proCredentialsName.trim();
    }

    public String getProCrendentialsType() {
        return proCrendentialsType;
    }

    public void setProCrendentialsType(String proCrendentialsType) {
        this.proCrendentialsType = proCrendentialsType == null ? null : proCrendentialsType.trim();
    }

    public String getProRequired() {
        return proRequired;
    }

    public void setProRequired(String proRequired) {
        this.proRequired = proRequired == null ? null : proRequired.trim();
    }

    public String getProStatus() {
        return proStatus;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus == null ? null : proStatus.trim();
    }

    public String getProCredentialsDes() {
        return proCredentialsDes;
    }

    public void setProCredentialsDes(String proCredentialsDes) {
        this.proCredentialsDes = proCredentialsDes == null ? null : proCredentialsDes.trim();
    }

    public Date getProTime() {
        return proTime;
    }

    public void setProTime(Date proTime) {
        this.proTime = proTime;
    }

    /**
     * 产品资质对象转化成订单资质对象
     * @param orderNo
     * @return
     */
	public LoanOrderCredentials transformOrderCredentials(String orderNo) {
		LoanOrderCredentials temp = new LoanOrderCredentials();
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		temp.setOrderCredentialsNo(Long.valueOf(currentTime));
		temp.setOrderProductId(this.productId);
		temp.setOrderNo(orderNo);
		temp.setOrderCredentialsName(this.proCredentialsName);
		temp.setOrderCredentialsEnglishName(this.proEnglishName);
		temp.setOrderCredentialsType(this.proCrendentialsType);
		temp.setOrderCredentialsRequired(this.proRequired);
		temp.setOrderCredentialsStatus("0");
		temp.setOrderCredentialsDes(this.proCredentialsDes);
		return temp;
	}

	public String getProEnglishName() {
		return proEnglishName;
	}

	public void setProEnglishName(String proEnglishName) {
		this.proEnglishName = proEnglishName;
	}
}