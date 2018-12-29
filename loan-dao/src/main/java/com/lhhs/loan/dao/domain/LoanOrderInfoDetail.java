package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

public class LoanOrderInfoDetail {
    private String orderNo;

    private BigDecimal loanAmount;

    private Integer loanTerm;

    private String loanTermUnit;

    private BigDecimal loanRate;

    private String loanRateUnit;

    private String repayment;

    private BigDecimal actualAmount;

    private Integer actualTerm;
    
    private String actualTermUnit;

    private BigDecimal actualRate;
    
    private String actualRateUnit;

    private Date actualLoanDate;

    private Long fundOwner;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getLoanTermUnit() {
        return loanTermUnit;
    }

    public void setLoanTermUnit(String loanTermUnit) {
        this.loanTermUnit = loanTermUnit == null ? null : loanTermUnit.trim();
    }

    public BigDecimal getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public String getLoanRateUnit() {
        return loanRateUnit;
    }

    public void setLoanRateUnit(String loanRateUnit) {
        this.loanRateUnit = loanRateUnit == null ? null : loanRateUnit.trim();
    }

    public String getRepayment() {
        return repayment;
    }

    public void setRepayment(String repayment) {
        this.repayment = repayment == null ? null : repayment.trim();
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Integer getActualTerm() {
        return actualTerm;
    }

    public void setActualTerm(Integer actualTerm) {
        this.actualTerm = actualTerm == null ? null : actualTerm;
    }
    
    public String getActualTermUnit() {
        return actualTermUnit;
    }

    public void setActualTermUnit(String actualTermUnit) {
        this.actualTermUnit = actualTermUnit == null ? null : actualTermUnit.trim();
    }

    public BigDecimal getActualRate() {
        return actualRate;
    }

    public void setActualRate(BigDecimal actualRate) {
        this.actualRate = actualRate;
    }
    
    public String getActualRateUnit() {
        return actualRateUnit;
    }

    public void setActualRateUnit(String actualRateUnit) {
        this.actualRateUnit = actualRateUnit == null ? null : actualRateUnit.trim();
    }

    public Date getActualLoanDate() {
        return actualLoanDate;
    }

    public void setActualLoanDate(Date actualLoanDate) {
        this.actualLoanDate = actualLoanDate;
    }

    public Long getFundOwner() {
        return fundOwner;
    }

    public void setFundOwner(Long fundOwner) {
        this.fundOwner = fundOwner;
    }
}