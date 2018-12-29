package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

public class LoanAdvance extends LoanTransMain{

	private static final long serialVersionUID = 1577040366949339572L;

	private String advanceId;

    private Long planId;

    private String orderNo;

    private String customerId;

    private String customerName;

    private String accountId;

    private String transMainId;

    private Short period;

    private BigDecimal advanceTotal;

    private BigDecimal advanceCapital;

    private BigDecimal advanceInterest;

    private Date advanceTime;

    private String advanceCustomerId;

    private String advanceCustomerName;

    private String advanceAccountId;

    private String advanceRate;

    private Integer overdueDays;

    private BigDecimal repaymentInterest;

    private BigDecimal paidTotal;

    private BigDecimal paidCapital;

    private BigDecimal paidInterest;

    private Date paidAdvanceTime;

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
    //交易备注
    private String transRemark;
    //待还金额
    private BigDecimal waitAmountTotal;
    //还款方式
    private String repaymentMethod;
    //应还金额
    private BigDecimal repaymentTotal;
    private Date repaymentCapitalTime;
    private Date repaymentInterestTime;
    //等还金额
    private BigDecimal advanceTotalAll;
    //垫付利息 控制成使用
    private String isInterest;
    //垫付本金
    private String isCapita;
    //当前还债时间 计算债务利息临时使用
    private Date nowPaidAdvanceTime;
    //剩余应还债务利息金额
    private BigDecimal remainRepaymentInterest;
    //剩余垫付总额
    private BigDecimal remainAdvanceTotal;
    public BigDecimal getAdvanceTotalAll() {
		return advanceTotalAll;
	}

	public void setAdvanceTotalAll(BigDecimal advanceTotalAll) {
		this.advanceTotalAll = advanceTotalAll;
	}

	public Date getRepaymentCapitalTime() {
		return repaymentCapitalTime;
	}

	public void setRepaymentCapitalTime(Date repaymentCapitalTime) {
		this.repaymentCapitalTime = repaymentCapitalTime;
	}

	public Date getRepaymentInterestTime() {
		return repaymentInterestTime;
	}

	public void setRepaymentInterestTime(Date repaymentInterestTime) {
		this.repaymentInterestTime = repaymentInterestTime;
	}

	public BigDecimal getRepaymentTotal() {
		return repaymentTotal;
	}

	public void setRepaymentTotal(BigDecimal repaymentTotal) {
		this.repaymentTotal = repaymentTotal;
	}

	public String getRepaymentMethod() {
		return repaymentMethod;
	}

	public void setRepaymentMethod(String repaymentMethod) {
		this.repaymentMethod = repaymentMethod;
	}

	public BigDecimal getWaitAmountTotal() {
		return waitAmountTotal;
	}

	public void setWaitAmountTotal(BigDecimal waitAmountTotal) {
		this.waitAmountTotal = waitAmountTotal;
	}

	public String getAdvanceId() {
        return advanceId;
    }

    public void setAdvanceId(String advanceId) {
        this.advanceId = advanceId == null ? null : advanceId.trim();
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
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

    public Short getPeriod() {
        return period;
    }

    public void setPeriod(Short period) {
        this.period = period;
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

    public Date getAdvanceTime() {
        return advanceTime;
    }

    public void setAdvanceTime(Date advanceTime) {
        this.advanceTime = advanceTime;
    }

    public String getAdvanceCustomerId() {
        return advanceCustomerId;
    }

    public void setAdvanceCustomerId(String advanceCustomerId) {
        this.advanceCustomerId = advanceCustomerId == null ? null : advanceCustomerId.trim();
    }

    public String getAdvanceCustomerName() {
        return advanceCustomerName;
    }

    public void setAdvanceCustomerName(String advanceCustomerName) {
        this.advanceCustomerName = advanceCustomerName == null ? null : advanceCustomerName.trim();
    }

    public String getAdvanceAccountId() {
        return advanceAccountId;
    }

    public void setAdvanceAccountId(String advanceAccountId) {
        this.advanceAccountId = advanceAccountId == null ? null : advanceAccountId.trim();
    }

    public String getAdvanceRate() {
        return advanceRate;
    }

    public void setAdvanceRate(String advanceRate) {
        this.advanceRate = advanceRate == null ? null : advanceRate.trim();
    }

    public Integer getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(Integer overdueDays) {
        this.overdueDays = overdueDays;
    }

    public BigDecimal getRepaymentInterest() {
        return repaymentInterest;
    }

    public void setRepaymentInterest(BigDecimal repaymentInterest) {
        this.repaymentInterest = repaymentInterest;
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

    public Date getPaidAdvanceTime() {
        return paidAdvanceTime;
    }

    public void setPaidAdvanceTime(Date paidAdvanceTime) {
        this.paidAdvanceTime = paidAdvanceTime;
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

	public String getTransRemark() {
		return transRemark;
	}

	public void setTransRemark(String transRemark) {
		this.transRemark = transRemark;
	}

	public String getIsInterest() {
		return isInterest;
	}

	public void setIsInterest(String isInterest) {
		this.isInterest = isInterest;
	}

	public String getIsCapita() {
		return isCapita;
	}

	public void setIsCapita(String isCapita) {
		this.isCapita = isCapita;
	}

	public Date getNowPaidAdvanceTime() {
		return nowPaidAdvanceTime;
	}

	public void setNowPaidAdvanceTime(Date nowPaidAdvanceTime) {
		this.nowPaidAdvanceTime = nowPaidAdvanceTime;
	}

	public BigDecimal getRemainRepaymentInterest() {
		return remainRepaymentInterest;
	}

	public void setRemainRepaymentInterest(BigDecimal remainRepaymentInterest) {
		this.remainRepaymentInterest = remainRepaymentInterest;
	}

	public BigDecimal getRemainAdvanceTotal() {
		return remainAdvanceTotal;
	}

	public void setRemainAdvanceTotal(BigDecimal remainAdvanceTotal) {
		this.remainAdvanceTotal = remainAdvanceTotal;
	}
  
}