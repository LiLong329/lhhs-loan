package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

public class LoanPayPlanTemp {
    private Long id;

    private String orderNo;

    private String customerId;

    private String customerName;

    private String accountId;

    private String transMainId;

    private String repaymentMethod;

    private BigDecimal repaymentTotal;

    private BigDecimal repaymentCapital;

    private Date repaymentCapitalTime;

    private BigDecimal repaymentInterest;

    private Date repaymentInterestTime;

    private Integer overdueDays;

    private Short period;

    private BigDecimal overdueInterestTotal;

    private BigDecimal overdueCapital;

    private BigDecimal overdueInterest;

    private BigDecimal compensate;

    private BigDecimal feesTotal;

    private BigDecimal seviceFee;

    private String transType;

    private BigDecimal advanceTotal;

    private BigDecimal advanceCapital;

    private BigDecimal advanceInterest;

    private Date repaymentActualTime;

    private Date repaymentedTime;

    private Date capitalTime;

    private Date interestTime;

    private BigDecimal paidTotal;

    private BigDecimal paidCapital;

    private BigDecimal paidInterest;

    private BigDecimal paidOverdue;

    private BigDecimal paidCompensate;

    private String interestStart;

    private String interestEnd;

    private BigDecimal interestSpread;

    private String status;

    private String createUser;

    private Date createTime;

    private String lastUser;

    private Date lastModifyTime;

    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getTransMainId() {
        return transMainId;
    }

    public void setTransMainId(String transMainId) {
        this.transMainId = transMainId == null ? null : transMainId.trim();
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod == null ? null : repaymentMethod.trim();
    }

    public BigDecimal getRepaymentTotal() {
        return repaymentTotal;
    }

    public void setRepaymentTotal(BigDecimal repaymentTotal) {
        this.repaymentTotal = repaymentTotal;
    }

    public BigDecimal getRepaymentCapital() {
        return repaymentCapital;
    }

    public void setRepaymentCapital(BigDecimal repaymentCapital) {
        this.repaymentCapital = repaymentCapital;
    }

    public Date getRepaymentCapitalTime() {
        return repaymentCapitalTime;
    }

    public void setRepaymentCapitalTime(Date repaymentCapitalTime) {
        this.repaymentCapitalTime = repaymentCapitalTime;
    }

    public BigDecimal getRepaymentInterest() {
        return repaymentInterest;
    }

    public void setRepaymentInterest(BigDecimal repaymentInterest) {
        this.repaymentInterest = repaymentInterest;
    }

    public Date getRepaymentInterestTime() {
        return repaymentInterestTime;
    }

    public void setRepaymentInterestTime(Date repaymentInterestTime) {
        this.repaymentInterestTime = repaymentInterestTime;
    }

    public Integer getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

    public Short getPeriod() {
        return period;
    }

    public void setPeriod(Short period) {
        this.period = period;
    }

    public BigDecimal getOverdueInterestTotal() {
        return overdueInterestTotal;
    }

    public void setOverdueInterestTotal(BigDecimal overdueInterestTotal) {
        this.overdueInterestTotal = overdueInterestTotal;
    }

    public BigDecimal getOverdueCapital() {
        return overdueCapital;
    }

    public void setOverdueCapital(BigDecimal overdueCapital) {
        this.overdueCapital = overdueCapital;
    }

    public BigDecimal getOverdueInterest() {
        return overdueInterest;
    }

    public void setOverdueInterest(BigDecimal overdueInterest) {
        this.overdueInterest = overdueInterest;
    }

    public BigDecimal getCompensate() {
        return compensate;
    }

    public void setCompensate(BigDecimal compensate) {
        this.compensate = compensate;
    }

    public BigDecimal getFeesTotal() {
        return feesTotal;
    }

    public void setFeesTotal(BigDecimal feesTotal) {
        this.feesTotal = feesTotal;
    }

    public BigDecimal getSeviceFee() {
        return seviceFee;
    }

    public void setSeviceFee(BigDecimal seviceFee) {
        this.seviceFee = seviceFee;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public BigDecimal getAdvanceTotal() {
        return advanceTotal;
    }

    public void setAdvanceTotal(BigDecimal advanceTotal) {
        this.advanceTotal = advanceTotal;
    }

    public BigDecimal getAdvanceCapital() {
        return advanceCapital;
    }

    public void setAdvanceCapital(BigDecimal advanceCapital) {
        this.advanceCapital = advanceCapital;
    }

    public BigDecimal getAdvanceInterest() {
        return advanceInterest;
    }

    public void setAdvanceInterest(BigDecimal advanceInterest) {
        this.advanceInterest = advanceInterest;
    }

    public Date getRepaymentActualTime() {
        return repaymentActualTime;
    }

    public void setRepaymentActualTime(Date repaymentActualTime) {
        this.repaymentActualTime = repaymentActualTime;
    }

    public Date getRepaymentedTime() {
        return repaymentedTime;
    }

    public void setRepaymentedTime(Date repaymentedTime) {
        this.repaymentedTime = repaymentedTime;
    }

    public Date getCapitalTime() {
        return capitalTime;
    }

    public void setCapitalTime(Date capitalTime) {
        this.capitalTime = capitalTime;
    }

    public Date getInterestTime() {
        return interestTime;
    }

    public void setInterestTime(Date interestTime) {
        this.interestTime = interestTime;
    }

    public BigDecimal getPaidTotal() {
        return paidTotal;
    }

    public void setPaidTotal(BigDecimal paidTotal) {
        this.paidTotal = paidTotal;
    }

    public BigDecimal getPaidCapital() {
        return paidCapital;
    }

    public void setPaidCapital(BigDecimal paidCapital) {
        this.paidCapital = paidCapital;
    }

    public BigDecimal getPaidInterest() {
        return paidInterest;
    }

    public void setPaidInterest(BigDecimal paidInterest) {
        this.paidInterest = paidInterest;
    }

    public BigDecimal getPaidOverdue() {
        return paidOverdue;
    }

    public void setPaidOverdue(BigDecimal paidOverdue) {
        this.paidOverdue = paidOverdue;
    }

    public BigDecimal getPaidCompensate() {
        return paidCompensate;
    }

    public void setPaidCompensate(BigDecimal paidCompensate) {
        this.paidCompensate = paidCompensate;
    }

    public String getInterestStart() {
        return interestStart;
    }

    public void setInterestStart(String interestStart) {
        this.interestStart = interestStart == null ? null : interestStart.trim();
    }

    public String getInterestEnd() {
        return interestEnd;
    }

    public void setInterestEnd(String interestEnd) {
        this.interestEnd = interestEnd == null ? null : interestEnd.trim();
    }

    public BigDecimal getInterestSpread() {
        return interestSpread;
    }

    public void setInterestSpread(BigDecimal interestSpread) {
        this.interestSpread = interestSpread;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser == null ? null : lastUser.trim();
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
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
}