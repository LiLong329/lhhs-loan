package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

public class LoanOrderHouseExtend {
    private Long id;

    private String orderNo;

    private String custId;

    private String propertyNum;

    private String propertyName;

    private BigDecimal buildArea;

    private String houseAddress;

    private String builtAge;

    private String realName;

    private String liveSituation;

    private String queryStatus;

    private BigDecimal valuationPrice;

    private BigDecimal sellingPrice;

    private BigDecimal mortgageRate;

    private String isMortgage;

    private String surplusValue;

    private String mortgagePosition;

    private String houseType;

    private String secondMortgageOwner;

    private Date updateTime;
    
    private String customerId;
    
    private String houseFacing;

    private String houseLayout;

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getPropertyNum() {
        return propertyNum;
    }

    public void setPropertyNum(String propertyNum) {
        this.propertyNum = propertyNum == null ? null : propertyNum.trim();
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName == null ? null : propertyName.trim();
    }

    public BigDecimal getBuildArea() {
        return buildArea;
    }

    public void setBuildArea(BigDecimal buildArea) {
        this.buildArea = buildArea;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress == null ? null : houseAddress.trim();
    }

    public String getBuiltAge() {
        return builtAge;
    }

    public void setBuiltAge(String builtAge) {
        this.builtAge = builtAge == null ? null : builtAge.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getLiveSituation() {
        return liveSituation;
    }

    public void setLiveSituation(String liveSituation) {
        this.liveSituation = liveSituation == null ? null : liveSituation.trim();
    }

    public String getQueryStatus() {
        return queryStatus;
    }

    public void setQueryStatus(String queryStatus) {
        this.queryStatus = queryStatus == null ? null : queryStatus.trim();
    }

    public BigDecimal getValuationPrice() {
        return valuationPrice;
    }

    public void setValuationPrice(BigDecimal valuationPrice) {
        this.valuationPrice = valuationPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getMortgageRate() {
        return mortgageRate;
    }

    public void setMortgageRate(BigDecimal mortgageRate) {
        this.mortgageRate = mortgageRate;
    }

    public String getIsMortgage() {
        return isMortgage;
    }

    public void setIsMortgage(String isMortgage) {
        this.isMortgage = isMortgage == null ? null : isMortgage.trim();
    }

    public String getSurplusValue() {
        return surplusValue;
    }

    public void setSurplusValue(String surplusValue) {
        this.surplusValue = surplusValue == null ? null : surplusValue.trim();
    }

    public String getMortgagePosition() {
        return mortgagePosition;
    }

    public void setMortgagePosition(String mortgagePosition) {
        this.mortgagePosition = mortgagePosition == null ? null : mortgagePosition.trim();
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType == null ? null : houseType.trim();
    }

    public String getSecondMortgageOwner() {
        return secondMortgageOwner;
    }

    public void setSecondMortgageOwner(String secondMortgageOwner) {
        this.secondMortgageOwner = secondMortgageOwner == null ? null : secondMortgageOwner.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getHouseFacing() {
		return houseFacing;
	}

	public void setHouseFacing(String houseFacing) {
		this.houseFacing = houseFacing;
	}

	public String getHouseLayout() {
		return houseLayout;
	}

	public void setHouseLayout(String houseLayout) {
		this.houseLayout = houseLayout;
	}
}