package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;

public class LoanCapitalEarning {
    private String capitalEarningId;

    private String earningVariety;
    
    private String subjectName;

    private BigDecimal earningRate;

    private String earingRateUnit;

    private String earningTerm;

    private String earningTermUnit;

    private BigDecimal earningAmount;

    private Integer earningApproach;

    private String remark;

    private String orderId;

    private String orgId;

    public String getEarningVariety() {
		return earningVariety;
	}

	public void setEarningVariety(String earningVariety) {
		this.earningVariety = earningVariety;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getCapitalEarningId() {
        return capitalEarningId;
    }

    public void setCapitalEarningId(String capitalEarningId) {
        this.capitalEarningId = capitalEarningId == null ? null : capitalEarningId.trim();
    }

    public BigDecimal getEarningRate() {
        return earningRate;
    }

    public void setEarningRate(BigDecimal earningRate) {
        this.earningRate = earningRate;
    }

    public String getEaringRateUnit() {
        return earingRateUnit;
    }

    public void setEaringRateUnit(String earingRateUnit) {
        this.earingRateUnit = earingRateUnit == null ? null : earingRateUnit.trim();
    }

    public String getEarningTerm() {
        return earningTerm;
    }

    public void setEarningTerm(String earningTerm) {
        this.earningTerm = earningTerm == null ? null : earningTerm.trim();
    }

    public String getEarningTermUnit() {
        return earningTermUnit;
    }

    public void setEarningTermUnit(String earningTermUnit) {
        this.earningTermUnit = earningTermUnit == null ? null : earningTermUnit.trim();
    }

    public BigDecimal getEarningAmount() {
        return earningAmount;
    }

    public void setEarningAmount(BigDecimal earningAmount) {
        this.earningAmount = earningAmount;
    }

    public Integer getEarningApproach() {
        return earningApproach;
    }

    public void setEarningApproach(Integer earningApproach) {
        this.earningApproach = earningApproach;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }
}