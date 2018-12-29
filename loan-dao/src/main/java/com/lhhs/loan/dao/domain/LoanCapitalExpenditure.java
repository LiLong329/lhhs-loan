package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;

public class LoanCapitalExpenditure {
    private String capitalExpenditureId;

    private String expenditureVariety;
    
    private String subjectName;

    private BigDecimal expenditureRate;

    private String expenditureRateUnit;

    private String expenditureTerm;

    private String expenditureTermUnit;

    private BigDecimal amount;

    private Integer disbursement;

    private String accountName;

    private String bankcardId;

    private String bankName;

    private String branchBank;

    private String remark;

    private String orderId;

    private String orgId;

    public String getCapitalExpenditureId() {
        return capitalExpenditureId;
    }

    public void setCapitalExpenditureId(String capitalExpenditureId) {
        this.capitalExpenditureId = capitalExpenditureId == null ? null : capitalExpenditureId.trim();
    }

    public String getExpenditureVariety() {
		return expenditureVariety;
	}

	public void setExpenditureVariety(String expenditureVariety) {
		this.expenditureVariety = expenditureVariety;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public BigDecimal getExpenditureRate() {
        return expenditureRate;
    }

    public void setExpenditureRate(BigDecimal expenditureRate) {
        this.expenditureRate = expenditureRate;
    }

    public String getExpenditureRateUnit() {
        return expenditureRateUnit;
    }

    public void setExpenditureRateUnit(String expenditureRateUnit) {
        this.expenditureRateUnit = expenditureRateUnit == null ? null : expenditureRateUnit.trim();
    }

    public String getExpenditureTerm() {
        return expenditureTerm;
    }

    public void setExpenditureTerm(String expenditureTerm) {
        this.expenditureTerm = expenditureTerm == null ? null : expenditureTerm.trim();
    }

    public String getExpenditureTermUnit() {
        return expenditureTermUnit;
    }

    public void setExpenditureTermUnit(String expenditureTermUnit) {
        this.expenditureTermUnit = expenditureTermUnit == null ? null : expenditureTermUnit.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getDisbursement() {
        return disbursement;
    }

    public void setDisbursement(Integer disbursement) {
        this.disbursement = disbursement;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getBankcardId() {
        return bankcardId;
    }

    public void setBankcardId(String bankcardId) {
        this.bankcardId = bankcardId == null ? null : bankcardId.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBranchBank() {
        return branchBank;
    }

    public void setBranchBank(String branchBank) {
        this.branchBank = branchBank == null ? null : branchBank.trim();
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