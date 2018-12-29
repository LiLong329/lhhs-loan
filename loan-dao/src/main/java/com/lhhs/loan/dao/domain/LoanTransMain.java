package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanTransMain extends BaseObject{
    
	private static final long serialVersionUID = -2637394841993778950L;
	/**
     * 还款计划列表
     */
    private List<LoanPayPlan> listLoanPayPlan;
    /**
     * 还款计划临时表
     */
    private List<LoanPayPlanTemp> listLoanPayPlanTemp;
    /**
     * 付款款计划列表
     */
    private List<LoanPayPlanCompany> listLoanPayPlanCompany;
    /**
     * 付款款计划临时表
     */
    private List<LoanPayPlanCompanyTemp> listLoanPayPlanCompanyTemp;
    /**
     * 放款记录列表
     */
    private List<LoanRecord> listLoanRecord;
    /**
     * 放款记录临时表
     */
    private List<LoanRecordTemp> listLoanRecordTemp;
    /**
     * 收入计划列表
     */
    private List<LoanFeesPlan> listFeesPlan_in;
    /**
     * 支出计划列表
     */
    private List<LoanFeesPlan> listFeesPlan_out;
    
    /**
     * 还款记录列表
     */
    private List<LoanPayRecord> listLoanPayRecord;
    /**
     * 付款款记录列表
     */
    private List<LoanPayRecordCompany> listLoanPayRecordCompany;
    /**
     * 收入支出记录列表
     */
    private List<LoanFeesRecord> listLoanFeesRecord;
    /**
     * 账户冻结编号
     */
    private String freezeId;
   
	//冗余字段
	private BigDecimal totalAmount;

    private String transMainId;
    /**
     * 借款人（收款人）
     */
    private String borrower;
    /**
     * 借款人（收款人）ID
     */
    private String borrowerId;

    private String borrowerAccountId;

    private String productType;

    private String productNo;

    private String productName;

    private String productId;

    private Date lendingTime;
    /**
     * 合同到期人
     */
    private Date overTime;
    /**
     * 借款金额、理财金额
     */
    private BigDecimal borrowerAmount;
    /**
     * 实际放款金额、理财金额
     */
    private BigDecimal amount;
    /**
     * 放款期数
     */
    private Short term;

    private String termUnit;

    private String rateUnit;

    private BigDecimal rate;

    private String borrowCustomerType;

    private String provienceNo;

    private String provienceName;

    private String cityNo;

    private String cityName;
    /**
     * 业务编号/报单编号
     */
    private String businessId;

    private String transType;

    private String repaymentMethod;

    private Short periodTotal;

    private Short paidPeriod;
    
    private Short investPeriod;

    private BigDecimal paidCapitalAmount;

    private BigDecimal paidInterestAmount;

    private BigDecimal paidOverdueAmount;

    private BigDecimal paidCompensateAmount;

    private BigDecimal investCapitalAmount;

    private BigDecimal investInterestAmount;

    private BigDecimal investOverdueAmount;
    
    private BigDecimal investCompensateAmount;

    private BigDecimal paidAdvancedCapitalAmount;

    private BigDecimal paidAdvancedInterestAmount;

    private BigDecimal advancedAmount;

    private BigDecimal paidFeesAmount;

    private BigDecimal outgoingsFeesAmount;
    /**已收息差总额**/
    private BigDecimal interestSpreadAmount;

    private String isOverdue;

    private String isGuarantee;

    private String isAdvanced;

    private String interestWay;

    private String payInterestWay;


    private String setRemark;

    private String accountManager;

    private String department;

    private String companyId;

    private String unionId;

    private Date cleanUpTime;

    private Date payOffTime;

    private String cleanUpStatus;

    private String status;
    
    private String payerId;

    private String payerName;

    private String payerAccountId;

    private String createUser;

    private Date createTime;

    private String lastUser;

    private Date lastModifyTime;

    private String field1;

    private String field2;

	private String field3;

    private String field4;

    private String field5;
    private String orgId;
    private String orgName;

    
    //新增字段：
    /**在途金额**/
    private BigDecimal onTheWayAmount;

    
    private String orderNo;
    private String lenderName;
    private BigDecimal loanAmount;
    private BigDecimal loanRate;
    private Short period;
    private BigDecimal paidTotal;
    private BigDecimal paidCapital;
    private BigDecimal paidInterest;
    private Date loanTime;
    private Date repaymentCapitalTime;
    private Date repaymentInterestTime;
    private Date companyRepaymentCapitalTime;
    private Date companyRepaymentInterestTime;
    private String beginTimeThree;
    private String endTimeThree;
    private String beginTimeFour;
    private String endTimeFour;
    private String rateAndUnit;
    private String periodTotalAll;
    private String field6;
    private String amountRate;
    private String customerName;
    private String customerNature;
    private String userName;
    private BigDecimal repaymentTotal;
    private BigDecimal feesAmount;//支出
    private BigDecimal feesPlanPaidAmount;//收入
    
    public BigDecimal getFeesPlanPaidAmount() {
		return feesPlanPaidAmount;
	}
	public void setFeesPlanPaidAmount(BigDecimal feesPlanPaidAmount) {
		this.feesPlanPaidAmount = feesPlanPaidAmount;
	}
	public String getCustomerNature() {
		return customerNature;
	}
	public void setCustomerNature(String customerNature) {
		this.customerNature = customerNature;
	}
	
	public String getBeginTimeThree() {
		return beginTimeThree;
	}
	public void setBeginTimeThree(String beginTimeThree) {
		this.beginTimeThree = beginTimeThree;
	}
	public String getEndTimeThree() {
		return endTimeThree;
	}
	public void setEndTimeThree(String endTimeThree) {
		this.endTimeThree = endTimeThree;
	}
	public String getBeginTimeFour() {
		return beginTimeFour;
	}
	public void setBeginTimeFour(String beginTimeFour) {
		this.beginTimeFour = beginTimeFour;
	}
	public String getEndTimeFour() {
		return endTimeFour;
	}
	public void setEndTimeFour(String endTimeFour) {
		this.endTimeFour = endTimeFour;
	}
	public BigDecimal getFeesAmount() {
		return feesAmount;
	}
	public void setFeesAmount(BigDecimal feesAmount) {
		this.feesAmount = feesAmount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCompanyRepaymentCapitalTime() {
		return companyRepaymentCapitalTime;
	}
	public void setCompanyRepaymentCapitalTime(Date companyRepaymentCapitalTime) {
		this.companyRepaymentCapitalTime = companyRepaymentCapitalTime;
	}
	public Date getCompanyRepaymentInterestTime() {
		return companyRepaymentInterestTime;
	}
	public void setCompanyRepaymentInterestTime(Date companyRepaymentInterestTime) {
		this.companyRepaymentInterestTime = companyRepaymentInterestTime;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAmountRate() {
		return amountRate;
	}
	public void setAmountRate(String amountRate) {
		this.amountRate = amountRate;
	}
	public Date getRepaymentCapitalTime() {
		return repaymentCapitalTime;
	}
	public void setRepaymentCapitalTime(Date repaymentCapitalTime) {
		this.repaymentCapitalTime = repaymentCapitalTime;
	}
	public Date getRepaymentInterestTime() {
		return repaymentInterestTime;
	}
	public void setRepaymentInterestTime(Date repaymentInterestTime) {
		this.repaymentInterestTime = repaymentInterestTime;
	}
	public BigDecimal getRepaymentTotal() {
		return repaymentTotal;
	}
	public void setRepaymentTotal(BigDecimal repaymentTotal) {
		this.repaymentTotal = repaymentTotal;
	}
	public String getField6() {
		return field6;
	}
	public void setField6(String field6) {
		this.field6 = field6;
	}
	public String getRateAndUnit() {
		return rateAndUnit;
	}
	public void setRateAndUnit(String rateAndUnit) {
		this.rateAndUnit = rateAndUnit;
	}
	public String getPeriodTotalAll() {
		return periodTotalAll;
	}
	public void setPeriodTotalAll(String periodTotalAll) {
		this.periodTotalAll = periodTotalAll;
	}
	private BigDecimal gatheringTotal;
    public BigDecimal getGatheringTotal() {
		return gatheringTotal;
	}
	public void setGatheringTotal(BigDecimal gatheringTotal) {
		this.gatheringTotal = gatheringTotal;
	}
	/**罚息息差收入**/
    private BigDecimal overdueInterestSpreadAmount;
    
    
    private String isPayPlanTemp;//是否生成还款计划
    
    private String isLoanRecordTemp;//是否生成放款记录
    
    private String isPayPlanCompanyTemp;//是否生成待付款计划
    

    private Date dateOfInterest;//结息指定日期

    private Date dateOfPayInterest;//付息指定日期

    private BigDecimal sumFeesTotal;//还款总额
    
    private BigDecimal sumFeesTotalPay;//待付款总额
    
    private Short sumTerm;//总期数
    /**逾期贷款利率**/
    private String overRate;
    /**债务利率**/
    private String debtRate;
    /**还款违约金率**/
    private String breachRate;
    /**付款违约金率**/
    private String investBreachRate;
    /**逾期付款利率**/
    private String investOverRate;
    private String statusList;
    
    /**
     * 已还总额
     *
     */
	public BigDecimal getPaidAmount() {
		BigDecimal paidTotal=new BigDecimal(0) ;
		if(paidCapitalAmount!=null)paidTotal=paidTotal.add(paidCapitalAmount);
		if(paidInterestAmount!=null)paidTotal=paidTotal.add(paidInterestAmount);
		if(paidOverdueAmount!=null)paidTotal=paidTotal.add(paidOverdueAmount);
		if(paidCompensateAmount!=null)paidTotal=paidTotal.add(paidCompensateAmount);
		return paidTotal;
	}
	/**
     * 已付总额
     *
     */
	public BigDecimal getInvestAmount() {
		BigDecimal total=new BigDecimal(0) ;
		if(investCapitalAmount!=null)total=total.add(investCapitalAmount);
		if(investInterestAmount!=null)total=total.add(investInterestAmount);
		if(investOverdueAmount!=null)total=total.add(investOverdueAmount);
		if(investCompensateAmount!=null)total=total.add(investCompensateAmount);
		return total;
	}
	public String getOrderNo() {
 		return orderNo;
 	}

 	public void setOrderNo(String orderNo) {
 		this.orderNo = orderNo;
 	}

 	public String getLenderName() {
 		return lenderName;
 	}

 	public void setLenderName(String lenderName) {
 		this.lenderName = lenderName;
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

 	public Short getPeriod() {
 		if(period==null){
 			period=-1;
 		}
 		return period;
 	}

 	public void setPeriod(Short period) {
 		this.period = period;
 	}

 	public BigDecimal getPaidTotal() {
 		return paidTotal;
 	}

 	public void setPaidTotal(BigDecimal paidTotal) {
 		this.paidTotal = paidTotal;
 	}

 	public BigDecimal getPaidCapital() {
 		return paidCapital;
 	}

 	public void setPaidCapital(BigDecimal paidCapital) {
 		this.paidCapital = paidCapital;
 	}

 	public BigDecimal getPaidInterest() {
 		return paidInterest;
 	}

 	public void setPaidInterest(BigDecimal paidInterest) {
 		this.paidInterest = paidInterest;
 	}

 	public Date getLoanTime() {
 		return loanTime;
 	}

 	public void setLoanTime(Date loanTime) {
 		this.loanTime = loanTime;
 	}
    
    
    public String getTransMainId() {
        return transMainId;
    }

    public void setTransMainId(String transMainId) {
        this.transMainId = transMainId == null ? null : transMainId.trim();
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower == null ? null : borrower.trim();
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(String borrowerId) {
        this.borrowerId = borrowerId == null ? null : borrowerId.trim();
    }

    public String getBorrowerAccountId() {
        return borrowerAccountId;
    }

    public void setBorrowerAccountId(String borrowerAccountId) {
        this.borrowerAccountId = borrowerAccountId == null ? null : borrowerAccountId.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
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
        this.termUnit = termUnit == null ? null : termUnit.trim();
    }

    public String getRateUnit() {
        return rateUnit;
    }

    public void setRateUnit(String rateUnit) {
        this.rateUnit = rateUnit == null ? null : rateUnit.trim();
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
        this.borrowCustomerType = borrowCustomerType == null ? null : borrowCustomerType.trim();
    }

    public String getProvienceNo() {
        return provienceNo;
    }

    public void setProvienceNo(String provienceNo) {
        this.provienceNo = provienceNo == null ? null : provienceNo.trim();
    }

    public String getProvienceName() {
        return provienceName;
    }

    public void setProvienceName(String provienceName) {
        this.provienceName = provienceName == null ? null : provienceName.trim();
    }

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo == null ? null : cityNo.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod == null ? null : repaymentMethod.trim();
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

    public BigDecimal getInvestOverdueAmount() {
        return investOverdueAmount;
    }

    public void setInvestOverdueAmount(BigDecimal investOverdueAmount) {
        this.investOverdueAmount = investOverdueAmount;
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
        this.isOverdue = isOverdue == null ? null : isOverdue.trim();
    }

    public String getIsGuarantee() {
        return isGuarantee;
    }

    public void setIsGuarantee(String isGuarantee) {
        this.isGuarantee = isGuarantee == null ? null : isGuarantee.trim();
    }

    public String getIsAdvanced() {
        return isAdvanced;
    }

    public void setIsAdvanced(String isAdvanced) {
        this.isAdvanced = isAdvanced == null ? null : isAdvanced.trim();
    }

    public String getInterestWay() {
        return interestWay;
    }

    public void setInterestWay(String interestWay) {
        this.interestWay = interestWay == null ? null : interestWay.trim();
    }

    public String getPayInterestWay() {
        return payInterestWay;
    }

    public void setPayInterestWay(String payInterestWay) {
        this.payInterestWay = payInterestWay == null ? null : payInterestWay.trim();
    }

    public String getOverRate() {
        return overRate;
    }

    public void setOverRate(String overRate) {
        this.overRate = overRate == null ? null : overRate.trim();
    }

    public String getDebtRate() {
        return debtRate;
    }

    public void setDebtRate(String debtRate) {
        this.debtRate = debtRate == null ? null : debtRate.trim();
    }

    public String getBreachRate() {
        return breachRate;
    }

    public void setBreachRate(String breachRate) {
        this.breachRate = breachRate == null ? null : breachRate.trim();
    }

    public String getSetRemark() {
        return setRemark;
    }

    public void setSetRemark(String setRemark) {
        this.setRemark = setRemark == null ? null : setRemark.trim();
    }

    public String getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(String accountManager) {
        this.accountManager = accountManager == null ? null : accountManager.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    public Date getCleanUpTime() {
        return cleanUpTime;
    }

    public void setCleanUpTime(Date cleanUpTime) {
        this.cleanUpTime = cleanUpTime;
    }

    public Date getPayOffTime() {
        return payOffTime;
    }

    public void setPayOffTime(Date payOffTime) {
        this.payOffTime = payOffTime;
    }

    public String getCleanUpStatus() {
        return cleanUpStatus;
    }

    public void setCleanUpStatus(String cleanUpStatus) {
        this.cleanUpStatus = cleanUpStatus == null ? null : cleanUpStatus.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId == null ? null : payerId.trim();
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName == null ? null : payerName.trim();
    }

    public String getPayerAccountId() {
        return payerAccountId;
    }

    public void setPayerAccountId(String payerAccountId) {
        this.payerAccountId = payerAccountId == null ? null : payerAccountId.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser == null ? null : lastUser.trim();
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1 == null ? null : field1.trim();
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2 == null ? null : field2.trim();
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3 == null ? null : field3.trim();
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4 == null ? null : field4.trim();
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5 == null ? null : field5.trim();
    }

	public List<LoanPayPlan> getListLoanPayPlan() {
		return listLoanPayPlan;
	}

	public void setListLoanPayPlan(List<LoanPayPlan> listLoanPayPlan) {
		this.listLoanPayPlan = listLoanPayPlan;
	}

	public List<LoanPayPlanCompany> getListLoanPayPlanCompany() {
		return listLoanPayPlanCompany;
	}

	public void setListLoanPayPlanCompany(
			List<LoanPayPlanCompany> listLoanPayPlanCompany) {
		this.listLoanPayPlanCompany = listLoanPayPlanCompany;
	}

	public List<LoanRecord> getListLoanRecord() {
		return listLoanRecord;
	}

	public void setListLoanRecord(List<LoanRecord> listLoanRecord) {
		this.listLoanRecord = listLoanRecord;
	}

	public List<LoanFeesPlan> getListFeesPlan_in() {
		return listFeesPlan_in;
	}

	public void setListFeesPlan_in(List<LoanFeesPlan> listFeesPlan_in) {
		this.listFeesPlan_in = listFeesPlan_in;
	}

	public List<LoanFeesPlan> getListFeesPlan_out() {
		return listFeesPlan_out;
	}

	public void setListFeesPlan_out(List<LoanFeesPlan> listFeesPlan_out) {
		this.listFeesPlan_out = listFeesPlan_out;
	}

	public List<LoanPayRecord> getListLoanPayRecord() {
		return listLoanPayRecord;
	}

	public void setListLoanPayRecord(List<LoanPayRecord> listLoanPayRecord) {
		this.listLoanPayRecord = listLoanPayRecord;
	}

	public List<LoanPayRecordCompany> getListLoanPayRecordCompany() {
		return listLoanPayRecordCompany;
	}

	public void setListLoanPayRecordCompany(
			List<LoanPayRecordCompany> listLoanPayRecordCompany) {
		this.listLoanPayRecordCompany = listLoanPayRecordCompany;
	}

	public List<LoanFeesRecord> getListLoanFeesRecord() {
		return listLoanFeesRecord;
	}

	public void setListLoanFeesRecord(List<LoanFeesRecord> listLoanFeesRecord) {
		this.listLoanFeesRecord = listLoanFeesRecord;
	}

	public String getFreezeId() {
		return freezeId;
	}

	public void setFreezeId(String freezeId) {
		this.freezeId = freezeId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Short getInvestPeriod() {
		return investPeriod;
	}

	public void setInvestPeriod(Short investPeriod) {
		this.investPeriod = investPeriod;
	}

	public List<LoanRecordTemp> getListLoanRecordTemp() {
		return listLoanRecordTemp;
	}

	public void setListLoanRecordTemp(List<LoanRecordTemp> listLoanRecordTemp) {
		this.listLoanRecordTemp = listLoanRecordTemp;
	}

	public BigDecimal getInterestSpreadAmount() {
		return interestSpreadAmount;
	}

	public void setInterestSpreadAmount(BigDecimal interestSpreadAmount) {
		this.interestSpreadAmount = interestSpreadAmount;
	}

	public BigDecimal getInvestCompensateAmount() {
		return investCompensateAmount;
	}

	public void setInvestCompensateAmount(BigDecimal investCompensateAmount) {
		this.investCompensateAmount = investCompensateAmount;
	}


	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public BigDecimal getOnTheWayAmount() {
		return onTheWayAmount;
	}
	public void setOnTheWayAmount(BigDecimal onTheWayAmount) {
		this.onTheWayAmount = onTheWayAmount;
	}
	public BigDecimal getOverdueInterestSpreadAmount() {
		return overdueInterestSpreadAmount;
	}
	public void setOverdueInterestSpreadAmount(
			BigDecimal overdueInterestSpreadAmount) {
		this.overdueInterestSpreadAmount = overdueInterestSpreadAmount;
	}


	public List<LoanPayPlanCompanyTemp> getListLoanPayPlanCompanyTemp() {
		return listLoanPayPlanCompanyTemp;
	}


	public void setListLoanPayPlanCompanyTemp(List<LoanPayPlanCompanyTemp> listLoanPayPlanCompanyTemp) {
		this.listLoanPayPlanCompanyTemp = listLoanPayPlanCompanyTemp;
	}
	public String getIsPayPlanTemp() {
		return isPayPlanTemp;
	}
	public void setIsPayPlanTemp(String isPayPlanTemp) {
		this.isPayPlanTemp = isPayPlanTemp;
	}
	public String getIsLoanRecordTemp() {
		return isLoanRecordTemp;
	}
	public void setIsLoanRecordTemp(String isLoanRecordTemp) {
		this.isLoanRecordTemp = isLoanRecordTemp;
	}
	public String getIsPayPlanCompanyTemp() {
		return isPayPlanCompanyTemp;
	}
	public void setIsPayPlanCompanyTemp(String isPayPlanCompanyTemp) {
		this.isPayPlanCompanyTemp = isPayPlanCompanyTemp;
	}
	public List<LoanPayPlanTemp> getListLoanPayPlanTemp() {
		return listLoanPayPlanTemp;
	}
	public void setListLoanPayPlanTemp(List<LoanPayPlanTemp> listLoanPayPlanTemp) {
		this.listLoanPayPlanTemp = listLoanPayPlanTemp;
	}
	/**
	 * @return the dateOfInterest
	 */
	public Date getDateOfInterest() {
		return dateOfInterest;
	}
	/**
	 * @param dateOfInterest the dateOfInterest to set
	 */
	public void setDateOfInterest(Date dateOfInterest) {
		this.dateOfInterest = dateOfInterest;
	}
	/**
	 * @return the dateOfPayInterest
	 */
	public Date getDateOfPayInterest() {
		return dateOfPayInterest;
	}
	/**
	 * @param dateOfPayInterest the dateOfPayInterest to set
	 */
	public void setDateOfPayInterest(Date dateOfPayInterest) {
		this.dateOfPayInterest = dateOfPayInterest;
	}
	/**
	 * @return the sumFeesTotal
	 */
	public BigDecimal getSumFeesTotal() {
		return sumFeesTotal;
	}
	/**
	 * @param sumFeesTotal the sumFeesTotal to set
	 */
	public void setSumFeesTotal(BigDecimal sumFeesTotal) {
		this.sumFeesTotal = sumFeesTotal;
	}
	/**
	 * @return the sumTerm
	 */
	public Short getSumTerm() {
		return sumTerm;
	}
	/**
	 * @param sumTerm the sumTerm to set
	 */
	public void setSumTerm(Short sumTerm) {
		this.sumTerm = sumTerm;
	}
	/**
	 * @return the sumFeesTotalPay
	 */
	public BigDecimal getSumFeesTotalPay() {
		return sumFeesTotalPay;
	}
	/**
	 * @param sumFeesTotalPay the sumFeesTotalPay to set
	 */
	public void setSumFeesTotalPay(BigDecimal sumFeesTotalPay) {
		this.sumFeesTotalPay = sumFeesTotalPay;
	}
	public String getInvestBreachRate() {
		return investBreachRate;
	}
	public void setInvestBreachRate(String investBreachRate) {
		this.investBreachRate = investBreachRate;
	}
	public String getInvestOverRate() {
		return investOverRate;
	}
	public void setInvestOverRate(String investOverRate) {
		this.investOverRate = investOverRate;
	}
	public String getStatusList() {
		return statusList;
	}
	public void setStatusList(String statusList) {
		this.statusList = statusList;
	}
	public BigDecimal getBorrowerAmount() {
		return borrowerAmount;
	}
	public void setBorrowerAmount(BigDecimal borrowerAmount) {
		this.borrowerAmount = borrowerAmount;
	}
	
}