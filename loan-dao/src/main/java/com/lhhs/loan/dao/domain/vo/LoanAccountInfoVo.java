package com.lhhs.loan.dao.domain.vo;

import java.math.BigDecimal;

import com.lhhs.loan.dao.domain.LoanAccountInfo;

@SuppressWarnings("all")
public class LoanAccountInfoVo extends LoanAccountInfo{

	private BigDecimal balance;//账户可用余额
	
	private BigDecimal freezeTotal;//冻结金额
	
	private BigDecimal freezeWithdrawals;//提现冻结金额
	
	private BigDecimal transitTotal;//在途金额
	
	private String orgName;//机构名称
	
	private String orgCategories;//机构类别

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getFreezeTotal() {
		return freezeTotal;
	}

	public void setFreezeTotal(BigDecimal freezeTotal) {
		this.freezeTotal = freezeTotal;
	}

	public BigDecimal getFreezeWithdrawals() {
		return freezeWithdrawals;
	}

	public void setFreezeWithdrawals(BigDecimal freezeWithdrawals) {
		this.freezeWithdrawals = freezeWithdrawals;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}



	public String getOrgCategories() {
		return orgCategories;
	}

	public void setOrgCategories(String orgCategories) {
		this.orgCategories = orgCategories;
	}

	public BigDecimal getTransitTotal() {
		return transitTotal;
	}

	public void setTransitTotal(BigDecimal transitTotal) {
		this.transitTotal = transitTotal;
	}

}