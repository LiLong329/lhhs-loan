package com.lhhs.loan.dao.domain.vo;

import java.math.BigDecimal;
import java.util.List;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanTransRecordVo extends BaseObject{
	private static final long serialVersionUID = 1L;
	
	private String provienceNo; //省编号
	private String provienceName;
	private String cityNo;
	private String cityName;
    private String companyName;//公司名
    private String department;//部门
    private String accountManager;//客户经理
    private String lendingTime;//放款时间
    private BigDecimal loanAmount;//放款金额
    
    
    private String borrower;//借款人姓名 
    private String customerMobile;//客户手机
    private BigDecimal amount;//实际借款金额
	private String borrowCustomerType;//客户性质
	private List borrowCustomerTypeList;//客户性质
    
    
    private String lenderName;//放款人姓名
    private String transType;//交易类型   （放款类型）
    private String loanTime;//放款时间
	private String field4;
	private String customerNature;
    
	 public String getCustomerNature() {
		return customerNature;
	}
	public void setCustomerNature(String customerNature) {
		this.customerNature = customerNature;
	}
	public String getField4() {
			return field4;
		}
		public void setField4(String field4) {
			this.field4 = field4;
		}
    public String getBorrowCustomerType() {
		return borrowCustomerType;
	}
	public void setBorrowCustomerType(String borrowCustomerType) {
		this.borrowCustomerType = borrowCustomerType;
	}
	public List getBorrowCustomerTypeList() {
		return borrowCustomerTypeList;
	}
	public void setBorrowCustomerTypeList(List borrowCustomerTypeList) {
		this.borrowCustomerTypeList = borrowCustomerTypeList;
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
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getAccountManager() {
		return accountManager;
	}
	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}
	public String getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(String lendingTime) {
		this.lendingTime = lendingTime;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getBorrower() {
		return borrower;
	}
	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getLenderName() {
		return lenderName;
	}
	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getLoanTime() {
		return loanTime;
	}
	public void setLoanTime(String loanTime) {
		this.loanTime = loanTime;
	}
}
