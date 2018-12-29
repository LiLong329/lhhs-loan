/**
 * Project Name:loan-dao
 * File Name:PayPlanVo.java
 * Package Name:com.lhhs.loan.dao.domain.vo
 * Date:2017年7月28日下午4:18:30
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.dao.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.jeecgframework.poi.excel.annotation.Excel;

import com.lhhs.loan.dao.domain.LoanPayPlan;

/**
 * ClassName:PayPlanVo <br/>
 * Function: 还款计划表查询列表时的Vo类<br/>
 * Date:     2017年7月28日 下午4:18:30 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
@SuppressWarnings("all")
public class PayPlanVo extends LoanPayPlan {
	private String provienceName;//省名称
	
	private String cityName;//城市名称
	
	private String companyName;//分公司名称
	
	private String productType;//产品类型
	
	private String productName;//产品名称

	private BigDecimal amount;//借款金额
	
	private BigDecimal rate;//借款利率
	
	private String rateUnit;//借款利率单位

	private Short periodTotal;//总期数
	
	private Short paidPeriod;//已还期数
	
	private Date lendingTime;//放款时间

	private String department;//部门名称
	
	private String amountRate;//放款利率
	
	private String amountRateUnit;//放款利率danwei
	
	
	public String getAmountRate() {
		return amountRate; 
	}

	public void setAmountRate(String amountRate) {
		this.amountRate = amountRate;
	}

	public String getAmountRateUnit() {
		return amountRateUnit;
	}

	public void setAmountRateUnit(String amountRateUnit) {
		this.amountRateUnit = amountRateUnit;
	}

	public String getProvienceName() {
		return provienceName;
	}

	public void setProvienceName(String provienceName) {
		this.provienceName = provienceName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getRateUnit() {
		return rateUnit;
	}

	public void setRateUnit(String rateUnit) {
		this.rateUnit = rateUnit;
	}

	public Short getPeriodTotal() {
		return periodTotal;
	}

	public void setPeriodTotal(Short periodTotal) {
		this.periodTotal = periodTotal;
	}

	public Short getPaidPeriod() {
		return paidPeriod;
	}

	public void setPaidPeriod(Short paidPeriod) {
		this.paidPeriod = paidPeriod;
	}

	public Date getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
}

