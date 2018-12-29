/**
 * Project Name:loan-dao
 * File Name:LoanOrderInfoVo.java
 * Package Name:com.lhhs.loan.dao.domain.vo
 * Date:2017年8月7日下午8:25:47
 * Copyright (c) 2017, @author All Rights Reserved.  
 *
*/
package com.lhhs.loan.dao.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.lhhs.loan.dao.domain.LoanOrderInfo;

/**
 * ClassName:LoanOrderInfoVo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     2017年8月7日 下午8:25:47 <br/>
 * @author   kernel.org
 * @version
 * @since    JDK 1.8
 * @see
 */
public class LoanOrderInfoVo extends LoanOrderInfo {

	private String bname;
	
	private String mobile;
	
	private String leStaffName;
	
	private String providerName;
	
	private String productName;
	
	private Integer approvalNode;

	private String leAccount;
	
	private String leCity;
	
	private Integer leDeptId;
	
	private BigDecimal loanAmount;
	
	private BigDecimal loanRate;
	
	private String loanRateUnit;
	
	private Integer loanTerm;
	
	private String loanTermUnit;
	
	private String repayment;
	
	private String reportName;
	
	private String abutmentName;
	
	private String ldName;
	
	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getLeStaffName() {
		return leStaffName;
	}

	public void setLeStaffName(String leStaffName) {
		this.leStaffName = leStaffName;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public Integer getApprovalNode() {
		return approvalNode;
	}

	public void setApprovalNode(Integer approvalNode) {
		this.approvalNode = approvalNode;
	}
	
	public String getLeAccount() {
		return leAccount;
	}

	public void setLeAccount(String leAccount) {
		this.leAccount = leAccount;
	}
	
	public String getLeCity() {
		return leCity;
	}

	public void setLeCity(String leCity) {
		this.leCity = leCity;
	}
	
	public Integer getLeDeptId() {
		return leDeptId;
	}

	public void setLeDeptId(Integer leDeptId) {
		this.leDeptId = leDeptId;
	}
	
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(BigDecimal loanRate) {
		this.loanRate = loanRate;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}

	public String getRepayment() {
		return repayment;
	}

	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getAbutmentName() {
		return abutmentName;
	}

	public void setAbutmentName(String abutmentName) {
		this.abutmentName = abutmentName;
	}

	public String getLdName() {
		return ldName;
	}

	public void setLdName(String ldName) {
		this.ldName = ldName;
	}

	public String getLoanRateUnit() {
		return loanRateUnit;
	}

	public void setLoanRateUnit(String loanRateUnit) {
		this.loanRateUnit = loanRateUnit;
	}

	public String getLoanTermUnit() {
		return loanTermUnit;
	}

	public void setLoanTermUnit(String loanTermUnit) {
		this.loanTermUnit = loanTermUnit;
	}
	
}

