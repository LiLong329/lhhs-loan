/**
 * Project Name:loan-dao
 * File Name:AdvancePayFinishedExcelVo.java
 * Package Name:com.lhhs.loan.dao.domain.excel
 * Date:2017年8月24日下午12:30:00
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.dao.domain.excel;

import java.math.BigDecimal;
import java.util.Date;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * ClassName:AdvancePayFinishedExcelVo <br/>
 * Function: 提前还款记录列表<br/>
 * Date:     2017年8月24日 下午12:30:00 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public class AdvancePayFinishedExcelVo {
	@Excel(name = "报单编号", orderNum = "1")
	private String businessId;
	    
	@Excel(name = "省市", orderNum = "2")
    private String cityName;
	
	@Excel(name = "分公司", orderNum = "3")
	private String companyName;//分公司名称--导出
    
	@Excel(name = "产品类型", orderNum = "4")
    private String productType;

    @Excel(name = "产品名称", orderNum = "5")
    private String productName;
    
	@Excel(name = "借款人姓名", orderNum = "6")
    private String borrower;
	
	@Excel(name = "借款金额", orderNum = "7")
	private BigDecimal amount;

    @Excel(name = "借款利率", orderNum = "8")
	private String rateAndUnit;//借款利率%/单位---导出
   
	@Excel(name = "还款方式", orderNum = "9",replace = {"每月还息到期还本_0","等额本息_1","一次性还款_2","_null"})
    private String repaymentMethod;
	
	@Excel(name = "付息方式", orderNum = "10",replace = {"放款日结息_0","到期日结息_1","固定日结息_2","_null"})
	private String interestWay;//还款--付息方式

    @Excel(name = "当期/总期数", orderNum = "11")
	private String periodTotalAll;//当期/总期数---导出
    
    @Excel(name = "还款总额", orderNum = "12")
    private BigDecimal totalAmount;//还款总额
    
	@Excel(name = "还款本金", orderNum = "13")
	private BigDecimal paidCapitalAmount;//还款本金

    @Excel(name = "还款利息", orderNum = "14")
	private BigDecimal paidInterestAmount;//还款利息
    
    @Excel(name = "提前还清时间", orderNum = "15",exportFormat = "yyyy/MM/dd")
    private Date cleanUpTime;// 实际还清时间

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
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

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRateAndUnit() {
		return rateAndUnit;
	}

	public void setRateAndUnit(String rateAndUnit) {
		this.rateAndUnit = rateAndUnit;
	}

	public String getRepaymentMethod() {
		return repaymentMethod;
	}

	public void setRepaymentMethod(String repaymentMethod) {
		this.repaymentMethod = repaymentMethod;
	}

	public String getInterestWay() {
		return interestWay;
	}

	public void setInterestWay(String interestWay) {
		this.interestWay = interestWay;
	}

	public String getPeriodTotalAll() {
		return periodTotalAll;
	}

	public void setPeriodTotalAll(String periodTotalAll) {
		this.periodTotalAll = periodTotalAll;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
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

	public Date getCleanUpTime() {
		return cleanUpTime;
	}

	public void setCleanUpTime(Date cleanUpTime) {
		this.cleanUpTime = cleanUpTime;
	}
}

