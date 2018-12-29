package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

public class LoanCapitalInfo {
	// 资金方基本信息id : 资金方基本信息id
	private String capitalInfoId;
	// 账户信息表的账户id，用于关联账户信息表
	private String accountId;
	// 帐户名 : 银行账户名
	private String accountName;
	// 银行卡号 : 银行卡号
	private String bankcardId;
	// 所属银行 : 银行卡所属银行
	private String bankName;
	// 开户行 : 银行卡的开户行名称
	private String branchBank;
	// 放款金额 : 放款金额，精确到百分位
	private BigDecimal amountPaid;
	// 放款利率 : 放款利率,精确到百分位
	private BigDecimal amountRate;
	// 放款金额利率单位 : 放款金额利率单位 (次 天 月)
	private String amountRateUnit;
	// 组织机构id : 组织机构的id
	private String orgId;
	// 组织机构名称
	private String orgName;
	// 订单编号 : 资金方下关联银主的订单
	private String orderNo;
	// 客户类型 : (个人：10、企业：20、机构：30、其他90;)
	private String customerType;
	// 客户性质
	private String customerNature;
	// 放款申请时间
	private Date lciApplyDate;
	// 手机号
	private String mobile;
	// 可用余额
	private BigDecimal balance;

	private String typeName; // 客户性质
	
	private String accountType; //保存使用
	
	private String unionId;  //保存使用
	
	private String cardId;  //保存使用
	
	private String ownerId;
	private String ownerName;
	//已放金额
	private BigDecimal amountPaidAlready;
	//本次放款金额
	private BigDecimal amountPaidThis;
	
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }


	public String getCapitalInfoId() {
		return capitalInfoId;
	}

	public void setCapitalInfoId(String capitalInfoId) {
		this.capitalInfoId = capitalInfoId == null ? null : capitalInfoId.trim();
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName == null ? null : accountName.trim();
	}

	public String getBankcardId() {
		return bankcardId;
	}

	public void setBankcardId(String bankcardId) {
		this.bankcardId = bankcardId == null ? null : bankcardId.trim();
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName == null ? null : bankName.trim();
	}

	public String getBranchBank() {
		return branchBank;
	}

	public void setBranchBank(String branchBank) {
		this.branchBank = branchBank == null ? null : branchBank.trim();
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	public BigDecimal getAmountRate() {
		return amountRate;
	}

	public void setAmountRate(BigDecimal amountRate) {
		this.amountRate = amountRate;
	}

	public String getAmountRateUnit() {
		return amountRateUnit;
	}

	public void setAmountRateUnit(String amountRateUnit) {
		this.amountRateUnit = amountRateUnit == null ? null : amountRateUnit.trim();
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId == null ? null : orgId.trim();
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(String customerNature) {
		this.customerNature = customerNature;
	}

	public Date getLciApplyDate() {
		return lciApplyDate;
	}

	public void setLciApplyDate(Date lciApplyDate) {
		this.lciApplyDate = lciApplyDate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public BigDecimal getAmountPaidAlready() {
		return amountPaidAlready;
	}

	public void setAmountPaidAlready(BigDecimal amountPaidAlready) {
		this.amountPaidAlready = amountPaidAlready;
	}

	public BigDecimal getAmountPaidThis() {
		return amountPaidThis;
	}

	public void setAmountPaidThis(BigDecimal amountPaidThis) {
		this.amountPaidThis = amountPaidThis;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

}