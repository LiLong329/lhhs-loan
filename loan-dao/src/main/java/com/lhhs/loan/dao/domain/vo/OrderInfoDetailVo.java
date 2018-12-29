package com.lhhs.loan.dao.domain.vo;

import java.util.Date;
import java.util.List;

import com.lhhs.loan.dao.domain.LoanExTaskWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderBorrowerExtendWithBLOBs;
import com.lhhs.loan.dao.domain.LoanOrderCarExtend;
import com.lhhs.loan.dao.domain.LoanOrderHouseExtend;
import com.lhhs.loan.dao.domain.LoanProductCredentials;

public class OrderInfoDetailVo {
	
	
	
	// 订单编号 : 订单编号
	private String orderNo;
	// 订单编号 : 订单编号
	private String procInsId;
	// 业务类型
	private String orgBusinessType;
	// 申请时间 : 申请时间
	private Date applyDate;
	//客户来源
	private String customerOrigin;
	// 客户经理
	private String leStaffName;
	// 报单人
	private String providerName;
	// 借款人手机号
	private String mobileNo;
	// 资金方
	private String orgName;
	
	private String productType;
	private String productName ;
	private String loanAmount ;
	private String loanTerm ;
	private String loanTermUnit ;
	private String loanRate ;
	private String loanRateUnit ;
	private String repayment ;
	
	
	private Integer orderStatus;
	private String childProductNo;
	private String customerManager;
	
	private String companyId;
	private String unionId;
	// 借款人信息//联系人信息
	LoanOrderBorrowerExtendWithBLOBs borrowerExtendWithBLOBs;
	
	//处理记录
	private List<LoanExTaskWithBLOBs> loanExTaskWithBLOBList;
	//车抵
	private List<LoanOrderCarExtend> carExtendList;
	//房抵
	private List<LoanOrderHouseExtend> houseExtendList;
	//资质清单
	private List<LoanProductCredentials> credentialsList;
	
	public String getCustomerManager() {
		return customerManager;
	}
	public void setCustomerManager(String customerManager) {
		this.customerManager = customerManager;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getOrgBusinessType() {
		return orgBusinessType;
	}
	public void setOrgBusinessType(String orgBusinessType) {
		this.orgBusinessType = orgBusinessType;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public String getCustomerOrigin() {
		return customerOrigin;
	}
	public void setCustomerOrigin(String customerOrigin) {
		this.customerOrigin = customerOrigin;
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
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
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
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanTerm() {
		return loanTerm;
	}
	public void setLoanTerm(String loanTerm) {
		this.loanTerm = loanTerm;
	}
	
	public String getLoanTermUnit() {
		return loanTermUnit;
	}
	public void setLoanTermUnit(String loanTermUnit) {
		this.loanTermUnit = loanTermUnit;
	}
	public String getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(String loanRate) {
		this.loanRate = loanRate;
	}
	public String getLoanRateUnit() {
		return loanRateUnit;
	}
	public void setLoanRateUnit(String loanRateUnit) {
		this.loanRateUnit = loanRateUnit;
	}
	public String getRepayment() {
		return repayment;
	}
	public void setRepayment(String repayment) {
		this.repayment = repayment;
	}
	public String getChildProductNo() {
		return childProductNo;
	}
	public void setChildProductNo(String childProductNo) {
		this.childProductNo = childProductNo;
	}
	
	public LoanOrderBorrowerExtendWithBLOBs getBorrowerExtendWithBLOBs() {
		return borrowerExtendWithBLOBs;
	}
	public void setBorrowerExtendWithBLOBs(LoanOrderBorrowerExtendWithBLOBs borrowerExtendWithBLOBs) {
		this.borrowerExtendWithBLOBs = borrowerExtendWithBLOBs;
	}
	public List<LoanExTaskWithBLOBs> getLoanExTaskWithBLOBList() {
		return loanExTaskWithBLOBList;
	}
	public void setLoanExTaskWithBLOBList(List<LoanExTaskWithBLOBs> loanExTaskWithBLOBList) {
		this.loanExTaskWithBLOBList = loanExTaskWithBLOBList;
	}
	public List<LoanOrderCarExtend> getCarExtendList() {
		return carExtendList;
	}
	public void setCarExtendList(List<LoanOrderCarExtend> carExtendList) {
		this.carExtendList = carExtendList;
	}
	public List<LoanOrderHouseExtend> getHouseExtendList() {
		return houseExtendList;
	}
	public void setHouseExtendList(List<LoanOrderHouseExtend> houseExtendList) {
		this.houseExtendList = houseExtendList;
	}
	public List<LoanProductCredentials> getCredentialsList() {
		return credentialsList;
	}
	public void setCredentialsList(List<LoanProductCredentials> credentialsList) {
		this.credentialsList = credentialsList;
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
