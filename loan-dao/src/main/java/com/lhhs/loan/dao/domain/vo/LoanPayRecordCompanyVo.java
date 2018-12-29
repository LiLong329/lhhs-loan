/**
 * Project Name:loan-dao
 * File Name:PayRecordVo.java
 * Package Name:com.lhhs.loan.dao.domain.vo
 * Date:2017年7月31日上午11:09:44
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.dao.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.lhhs.loan.dao.domain.LoanPayRecord;
import com.lhhs.loan.dao.domain.LoanPayRecordCompany;

/**
 * ClassName:PayRecordVo <br/>
 * Function: 已还款记录查询Vo<br/>
 * Date:     2017年7月31日 上午11:09:44 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@SuppressWarnings("all")
public class LoanPayRecordCompanyVo extends LoanPayRecordCompany {
	//下面字段来自LoanTransMain
	private String borrowerId;

    private String productType;

    private String productNo;

    private String productName;

    private String productId;

    private Date lendingTime;

    private Date overTime;

    private BigDecimal amount;

    private Short term;

    private String termUnit;

    private String rateUnit;

    private BigDecimal rate;

    private String borrowCustomerType;

    private String provienceNo;

    private String provienceName;

    private String cityNo;

    private String cityName;

    private String businessId;

    private String transType;

    private Short paidPeriod;

    private BigDecimal paidCapitalAmount;

    private BigDecimal paidInterestAmount;

    private BigDecimal paidOverdueAmount;

    private BigDecimal paidCompensateAmount;

    private BigDecimal investCapitalAmount;

    private BigDecimal investInterestAmount;

    private BigDecimal investIoverdueAmount;

    private BigDecimal paidAdvancedCapitalAmount;

    private BigDecimal paidAdvancedInterestAmount;

    private BigDecimal advancedAmount;

    private BigDecimal paidFeesAmount;

    private BigDecimal outgoingsFeesAmount;

    private String isOverdue;

    private String isGuarantee;

    private String isAdvanced;

    private String interestWay;

    private Date payInterestWay;

    private String overRate;

    private String debtRate;

    private String breachRate;

    private String setRemark;

    private String accountManager;

    private String department;

    private String companyId;

    private String unionId;

    private BigDecimal advanceAmount;//提前部分还本金额
    
    private BigDecimal investAmount;//剩余本金
    
	public BigDecimal getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	public BigDecimal getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(BigDecimal advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public String getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(String borrowerId) {
		this.borrowerId = borrowerId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Date getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Short getTerm() {
		return term;
	}

	public void setTerm(Short term) {
		this.term = term;
	}

	public String getTermUnit() {
		return termUnit;
	}

	public void setTermUnit(String termUnit) {
		this.termUnit = termUnit;
	}

	public String getRateUnit() {
		return rateUnit;
	}

	public void setRateUnit(String rateUnit) {
		this.rateUnit = rateUnit;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getBorrowCustomerType() {
		return borrowCustomerType;
	}

	public void setBorrowCustomerType(String borrowCustomerType) {
		this.borrowCustomerType = borrowCustomerType;
	}

	public String getProvienceNo() {
		return provienceNo;
	}

	public void setProvienceNo(String provienceNo) {
		this.provienceNo = provienceNo;
	}

	public String getProvienceName() {
		return provienceName;
	}

	public void setProvienceName(String provienceName) {
		this.provienceName = provienceName;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Short getPaidPeriod() {
		return paidPeriod;
	}

	public void setPaidPeriod(Short paidPeriod) {
		this.paidPeriod = paidPeriod;
	}

	public BigDecimal getPaidCapitalAmount() {
		return paidCapitalAmount;
	}

	public void setPaidCapitalAmount(BigDecimal paidCapitalAmount) {
		this.paidCapitalAmount = paidCapitalAmount;
	}

	public BigDecimal getPaidInterestAmount() {
		return paidInterestAmount;
	}

	public void setPaidInterestAmount(BigDecimal paidInterestAmount) {
		this.paidInterestAmount = paidInterestAmount;
	}

	public BigDecimal getPaidOverdueAmount() {
		return paidOverdueAmount;
	}

	public void setPaidOverdueAmount(BigDecimal paidOverdueAmount) {
		this.paidOverdueAmount = paidOverdueAmount;
	}

	public BigDecimal getPaidCompensateAmount() {
		return paidCompensateAmount;
	}

	public void setPaidCompensateAmount(BigDecimal paidCompensateAmount) {
		this.paidCompensateAmount = paidCompensateAmount;
	}

	public BigDecimal getInvestCapitalAmount() {
		return investCapitalAmount;
	}

	public void setInvestCapitalAmount(BigDecimal investCapitalAmount) {
		this.investCapitalAmount = investCapitalAmount;
	}

	public BigDecimal getInvestInterestAmount() {
		return investInterestAmount;
	}

	public void setInvestInterestAmount(BigDecimal investInterestAmount) {
		this.investInterestAmount = investInterestAmount;
	}

	public BigDecimal getInvestIoverdueAmount() {
		return investIoverdueAmount;
	}

	public void setInvestIoverdueAmount(BigDecimal investIoverdueAmount) {
		this.investIoverdueAmount = investIoverdueAmount;
	}

	public BigDecimal getPaidAdvancedCapitalAmount() {
		return paidAdvancedCapitalAmount;
	}

	public void setPaidAdvancedCapitalAmount(BigDecimal paidAdvancedCapitalAmount) {
		this.paidAdvancedCapitalAmount = paidAdvancedCapitalAmount;
	}

	public BigDecimal getPaidAdvancedInterestAmount() {
		return paidAdvancedInterestAmount;
	}

	public void setPaidAdvancedInterestAmount(BigDecimal paidAdvancedInterestAmount) {
		this.paidAdvancedInterestAmount = paidAdvancedInterestAmount;
	}

	public BigDecimal getAdvancedAmount() {
		return advancedAmount;
	}

	public void setAdvancedAmount(BigDecimal advancedAmount) {
		this.advancedAmount = advancedAmount;
	}

	public BigDecimal getPaidFeesAmount() {
		return paidFeesAmount;
	}

	public void setPaidFeesAmount(BigDecimal paidFeesAmount) {
		this.paidFeesAmount = paidFeesAmount;
	}

	public BigDecimal getOutgoingsFeesAmount() {
		return outgoingsFeesAmount;
	}

	public void setOutgoingsFeesAmount(BigDecimal outgoingsFeesAmount) {
		this.outgoingsFeesAmount = outgoingsFeesAmount;
	}

	public String getIsOverdue() {
		return isOverdue;
	}

	public void setIsOverdue(String isOverdue) {
		this.isOverdue = isOverdue;
	}

	public String getIsGuarantee() {
		return isGuarantee;
	}

	public void setIsGuarantee(String isGuarantee) {
		this.isGuarantee = isGuarantee;
	}

	public String getIsAdvanced() {
		return isAdvanced;
	}

	public void setIsAdvanced(String isAdvanced) {
		this.isAdvanced = isAdvanced;
	}

	public String getInterestWay() {
		return interestWay;
	}

	public void setInterestWay(String interestWay) {
		this.interestWay = interestWay;
	}

	public Date getPayInterestWay() {
		return payInterestWay;
	}

	public void setPayInterestWay(Date payInterestWay) {
		this.payInterestWay = payInterestWay;
	}

	public String getOverRate() {
		return overRate;
	}

	public void setOverRate(String overRate) {
		this.overRate = overRate;
	}

	public String getDebtRate() {
		return debtRate;
	}

	public void setDebtRate(String debtRate) {
		this.debtRate = debtRate;
	}

	public String getBreachRate() {
		return breachRate;
	}

	public void setBreachRate(String breachRate) {
		this.breachRate = breachRate;
	}

	public String getSetRemark() {
		return setRemark;
	}

	public void setSetRemark(String setRemark) {
		this.setRemark = setRemark;
	}

	public String getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
}

