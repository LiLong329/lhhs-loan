package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.lhhs.loan.common.shared.page.BaseObject;
/**
 * 资金报备放款
 */
public class LoanBuzFundApply extends BaseObject{
	private static final long serialVersionUID = 1L;

	//
	private String lbfaId;
	//订单号
	private String lbfaOrderNo;
	//产品名称
	private String productName;
	//账户名称
	private String custName;
	//出借人
	private String lenderName;
	//放款金额
	private BigDecimal loanAmount;
	//资金调拨申请日期
	private Date applyDate;
	//资金调拨到账日期
	private Date replyDate;
	//借款期限
	private Integer loanTerm;
	//借款到期日期
	private Date expireDate;
	//是否报备
	private String isReport;
	//分公司账号
	private String branchAccount;
	//帐户名
	private String accountName;
	//开户行
	private String branchBank;

	/**
	 * 设置：
	 */
	public void setLbfaId (String lbfaId) {
		this.lbfaId = lbfaId;
	}
	/**
	 * 获取：
	 */
	public String getLbfaId() {
		return this.lbfaId;
	}
	/**
	 * 设置：订单号
	 */
	public void setLbfaOrderNo (String lbfaOrderNo) {
		this.lbfaOrderNo = lbfaOrderNo;
	}
	/**
	 * 获取：订单号
	 */
	public String getLbfaOrderNo() {
		return this.lbfaOrderNo;
	}
	/**
	 * 设置：产品名称
	 */
	public void setProductName (String productName) {
		this.productName = productName;
	}
	/**
	 * 获取：产品名称
	 */
	public String getProductName() {
		return this.productName;
	}
	/**
	 * 设置：账户名称
	 */
	public void setCustName (String custName) {
		this.custName = custName;
	}
	/**
	 * 获取：账户名称
	 */
	public String getCustName() {
		return this.custName;
	}
	/**
	 * 设置：出借人
	 */
	public void setLenderName (String lenderName) {
		this.lenderName = lenderName;
	}
	/**
	 * 获取：出借人
	 */
	public String getLenderName() {
		return this.lenderName;
	}
	/**
	 * 设置：放款金额
	 */
	public void setLoanAmount (BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	/**
	 * 获取：放款金额
	 */
	public BigDecimal getLoanAmount() {
		return this.loanAmount;
	}
	/**
	 * 设置：资金调拨申请日期
	 */
	public void setApplyDate (Date applyDate) {
		this.applyDate = applyDate;
	}
	/**
	 * 获取：资金调拨申请日期
	 */
	public Date getApplyDate() {
		return this.applyDate;
	}
	/**
	 * 设置：资金调拨到账日期
	 */
	public void setReplyDate (Date replyDate) {
		this.replyDate = replyDate;
	}
	/**
	 * 获取：资金调拨到账日期
	 */
	public Date getReplyDate() {
		return this.replyDate;
	}
	/**
	 * 设置：借款期限
	 */
	public void setLoanTerm (Integer loanTerm) {
		this.loanTerm = loanTerm;
	}
	/**
	 * 获取：借款期限
	 */
	public Integer getLoanTerm() {
		return this.loanTerm;
	}
	/**
	 * 设置：借款到期日期
	 */
	public void setExpireDate (Date expireDate) {
		this.expireDate = expireDate;
	}
	/**
	 * 获取：借款到期日期
	 */
	public Date getExpireDate() {
		return this.expireDate;
	}
	/**
	 * 设置：是否报备
	 */
	public void setIsReport (String isReport) {
		this.isReport = isReport;
	}
	/**
	 * 获取：是否报备
	 */
	public String getIsReport() {
		return this.isReport;
	}
	/**
	 * 设置：分公司账号
	 */
	public void setBranchAccount (String branchAccount) {
		this.branchAccount = branchAccount;
	}
	/**
	 * 获取：分公司账号
	 */
	public String getBranchAccount() {
		return this.branchAccount;
	}
	/**
	 * 设置：帐户名
	 */
	public void setAccountName (String accountName) {
		this.accountName = accountName;
	}
	/**
	 * 获取：帐户名
	 */
	public String getAccountName() {
		return this.accountName;
	}
	/**
	 * 设置：开户行
	 */
	public void setBranchBank (String branchBank) {
		this.branchBank = branchBank;
	}
	/**
	 * 获取：开户行
	 */
	public String getBranchBank() {
		return this.branchBank;
	}
}