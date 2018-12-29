/**
 * Project Name:loan-dao
 * File Name:PayPlanExcelVo.java
 * Package Name:com.lhhs.loan.dao.domain.excel
 * Date:2017年8月24日上午11:23:44
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.dao.domain.excel;

import java.math.BigDecimal;
import java.util.Date;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * ClassName:PayPlanExcelVo <br/>
 * Function: 待还款计划表excel导出VO <br/>
 * Date:     2017年8月24日 上午11:23:44 <br/>
 * @author   xiangfeng
 * @version
 * @since    JDK 1.8
 * @see
 */
public class PayPlanExcelVo {
	@Excel(name = "报单编号", orderNum = "1")
    private String orderNo;
	
	@Excel(name = "省市", orderNum = "2")
	private String cityName;//城市名称
	
	@Excel(name = "分公司", orderNum = "3")
	private String companyName;//分公司名称
	
	@Excel(name = "产品类型", orderNum = "4")
	private String productType;//产品类型
	
	@Excel(name = "产品名称", orderNum = "5")
	private String productName;//产品名称
	@Excel(name = "借款人姓名", orderNum = "6")
	private String borrower;//借款人
	@Excel(name = "借款金额", orderNum = "7")
	private BigDecimal amount;//借款金额

	@Excel(name = "借款利率", orderNum = "8")
	private String rateAndUnit;//借款利率%/单位
	
	@Excel(name = "还款方式", orderNum = "9",replace = {"每月还息到期还本_0","等额本息_1","一次性还款_2","_null"})
    private String repaymentMethod;//还款方式

	@Excel(name = "期数/总期数", orderNum = "10")
	private String periodTotalAll;//当期/总期数
    @Excel(name = "应还总额（元）", orderNum = "11")
    private BigDecimal repaymentTotal = new BigDecimal(0.00);//应还总额（元）

    @Excel(name = "应还本金", orderNum = "12")
    private BigDecimal repaymentCapital = new BigDecimal(0.00);//应还本金

    @Excel(name = "应还本时间", orderNum = "13",exportFormat = "yyyy/MM/dd")
    private Date repaymentCapitalTime;//应还本时间

    @Excel(name = "应还利息（元）", orderNum = "14")
    private BigDecimal repaymentInterest = new BigDecimal(0.00);//应还利息（元）

    @Excel(name = "应还息时间", orderNum = "15",exportFormat = "yyyy/MM/dd")
    private Date repaymentInterestTime;//应还息时间

    @Excel(name = "逾期天数", orderNum = "16")
    private Integer overdueDays;//逾期天数

    @Excel(name = "应还逾期罚息", orderNum = "17")
    private BigDecimal overdueInterestTotal= new BigDecimal(0.00);//应还逾期罚息

	@Excel(name = "放款时间", orderNum = "18",exportFormat = "yyyy/MM/dd")
	private Date lendingTime;//放款时间

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getPeriodTotalAll() {
		return periodTotalAll;
	}

	public void setPeriodTotalAll(String periodTotalAll) {
		this.periodTotalAll = periodTotalAll;
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

	public BigDecimal getOverdueInterestTotal() {
		return overdueInterestTotal;
	}

	public void setOverdueInterestTotal(BigDecimal overdueInterestTotal) {
		this.overdueInterestTotal = overdueInterestTotal;
	}

	public Date getLendingTime() {
		return lendingTime;
	}

	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	
}

