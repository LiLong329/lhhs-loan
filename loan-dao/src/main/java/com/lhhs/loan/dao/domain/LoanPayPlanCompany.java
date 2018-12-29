package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanPayPlanCompany extends BaseObject{
   
	private static final long serialVersionUID = -2455709384796698376L;

	private Long id;

    private String orderNo;

    private String customerId;

    private String customerName;

    private String accountId;

    private String transMainId;

    private String repaymentMethod;

    private BigDecimal repaymentTotal=new BigDecimal(0.00);

    private BigDecimal repaymentCapital=new BigDecimal(0.00);
    
    private BigDecimal repaymentCapitalSed=new BigDecimal(0.00);

    private Date repaymentCapitalTime;

    private BigDecimal repaymentInterest=new BigDecimal(0.00);

    private Date repaymentInterestTime;

    private Integer overdueDays;

    private Short period;
    private BigDecimal overdueInterestTotal=new BigDecimal(0.00);
    private BigDecimal overdueCapital=new BigDecimal(0.00);
    private BigDecimal overdueInterest=new BigDecimal(0.00);

    private BigDecimal compensate=new BigDecimal(0.00);

    private BigDecimal feesTotal=new BigDecimal(0.00);

    private BigDecimal seviceFee=new BigDecimal(0.00);

    private BigDecimal advanceTotal=new BigDecimal(0.00);

    private BigDecimal advanceCapital=new BigDecimal(0.00);

    private BigDecimal advanceInterest=new BigDecimal(0.00);

    private Date repaymentActualTime;

    private Date repaymentedTime;

    private Date capitalTime;

    private Date interestTime;

    private String transType;

    private BigDecimal paidTotal=new BigDecimal(0.00);

    private BigDecimal paidCapital=new BigDecimal(0.00);

    private BigDecimal paidInterest=new BigDecimal(0.00);

    private BigDecimal paidOverdue=new BigDecimal(0.00);

    private BigDecimal paidCompensate=new BigDecimal(0.00);

    private String interestStart;

    private String interestEnd;

    private BigDecimal interestSpread=new BigDecimal(0.00);

    private String status;

    private String createUser;

    private Date createTime;

    private String lastUser;

    private Date lastModifyTime;

    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;
    
    
    
	/******冗余字段**********/
    
    private String productName;
    private String provienceName;
    private String cityName;
    private Short periodTotal;
    private Short investPeriod;
    private String productType;
    private String periodTotalAll;
    private  Date repaymentInterestStartTime;//应收息时间
    private  Date repaymentInterestEndTime;//应收息时间
    private  Date repaymentCapitalStartTime;//应收ben时间
    private  Date repaymentCapitalEndTime;//应收ben时间
    private  BigDecimal borrowerAmount=new BigDecimal(0.00);//理财金额
    private Date lendingTime;//放款时间
    private Date overTime;//合同截止时间
    private String term;
    private String accountManager;
    private String department;
    private String borrower;
    private String termUnit;	
    private BigDecimal rate;
    private BigDecimal loanAmount;//放款金额
    private String rateUnit;
    private String searcherId;
    private String typeName;//客户性质
    private String rateAndUnit;//理财利率-导出
    private String termAndUnit;//期限-导出
    private String docking;//对接银住放款统计
    private String isCompany;//是否是分公司
    private String organizationName;//出借机构的机构名称
    
	public BigDecimal getRepaymentCapitalSed() {
		return repaymentCapitalSed;
	}

	public void setRepaymentCapitalSed(BigDecimal repaymentCapitalSed) {
		this.repaymentCapitalSed = repaymentCapitalSed;
	}

	public String getIsCompany() {
		return isCompany;
	}

	public void setIsCompany(String isCompany) {
		this.isCompany = isCompany;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getDocking() {
		return docking;
	}

	public void setDocking(String docking) {
		this.docking = docking;
	}

	public String getTermAndUnit() {
		return termAndUnit;
	}

	public void setTermAndUnit(String termAndUnit) {
		this.termAndUnit = termAndUnit;
	}

	public String getRateAndUnit() {
		return rateAndUnit;
	}

	public void setRateAndUnit(String rateAndUnit) {
		this.rateAndUnit = rateAndUnit;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSearcherId() {
		return searcherId;
	}

	public void setSearcherId(String searcherId) {
		this.searcherId = searcherId;
	}

	public String getTermUnit() {
		return termUnit;
	}

	public void setTermUnit(String termUnit) {
		this.termUnit = termUnit;
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

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getAccountManager() {
		return accountManager;
	}

	public void setAccountManager(String accountManager) {
		this.accountManager = accountManager;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public BigDecimal getBorrowerAmount() {
		return borrowerAmount;
	}

	public void setBorrowerAmount(BigDecimal borrowerAmount) {
		this.borrowerAmount = borrowerAmount;
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

	public Date getRepaymentCapitalStartTime() {
		return repaymentCapitalStartTime;
	}

	public void setRepaymentCapitalStartTime(Date repaymentCapitalStartTime) {
		this.repaymentCapitalStartTime = repaymentCapitalStartTime;
	}

	public Date getRepaymentCapitalEndTime() {
		return repaymentCapitalEndTime;
	}

	public void setRepaymentCapitalEndTime(Date repaymentCapitalEndTime) {
		this.repaymentCapitalEndTime = repaymentCapitalEndTime;
	}

	public Date getRepaymentInterestStartTime() {
  		return repaymentInterestStartTime;
  	}

  	public void setRepaymentInterestStartTime(Date repaymentInterestStartTime) {
  		this.repaymentInterestStartTime = repaymentInterestStartTime;
  	}

  	public Date getRepaymentInterestEndTime() {
  		return repaymentInterestEndTime;
  	}

  	public void setRepaymentInterestEndTime(Date repaymentInterestEndTime) {
  		this.repaymentInterestEndTime = repaymentInterestEndTime;
  	}
  	
  	
    /**
     * 交易备注
     */
    private String transRemark;
    public String getPeriodTotalAll() {
		return periodTotalAll;
	}

	public void setPeriodTotalAll(String periodTotalAll) {
		this.periodTotalAll = periodTotalAll;
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

	public String getProvienceName() {
		return provienceName;
	}

	public void setProvienceName(String provienceName) {
		this.provienceName = provienceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Short getPeriodTotal() {
		return periodTotal;
	}

	public void setPeriodTotal(Short periodTotal) {
		this.periodTotal = periodTotal;
	}

	public Short getInvestPeriod() {
		return investPeriod;
	}

	public void setInvestPeriod(Short investPeriod) {
		this.investPeriod = investPeriod;
	}

	/**付款总额=本金+利息+逾期罚息+违约金**/
    private BigDecimal paidAllTotal;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getTransMainId() {
        return transMainId;
    }

    public void setTransMainId(String transMainId) {
        this.transMainId = transMainId == null ? null : transMainId.trim();
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod == null ? null : repaymentMethod.trim();
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

    public Short getPeriod() {
        return period;
    }

    public void setPeriod(Short period) {
        this.period = period;
    }

    public BigDecimal getOverdueInterest() {
        return overdueInterest;
    }

    public void setOverdueInterest(BigDecimal overdueInterest) {
        this.overdueInterest = overdueInterest;
    }

    public BigDecimal getCompensate() {
        return compensate;
    }

    public void setCompensate(BigDecimal compensate) {
        this.compensate = compensate;
    }

    public BigDecimal getFeesTotal() {
        return feesTotal;
    }

    public void setFeesTotal(BigDecimal feesTotal) {
        this.feesTotal = feesTotal;
    }

    public BigDecimal getSeviceFee() {
        return seviceFee;
    }

    public void setSeviceFee(BigDecimal seviceFee) {
        this.seviceFee = seviceFee;
    }

    public BigDecimal getAdvanceTotal() {
        return advanceTotal;
    }

    public void setAdvanceTotal(BigDecimal advanceTotal) {
        this.advanceTotal = advanceTotal;
    }

    public BigDecimal getAdvanceCapital() {
        return advanceCapital;
    }

    public void setAdvanceCapital(BigDecimal advanceCapital) {
        this.advanceCapital = advanceCapital;
    }

    public BigDecimal getAdvanceInterest() {
        return advanceInterest;
    }

    public void setAdvanceInterest(BigDecimal advanceInterest) {
        this.advanceInterest = advanceInterest;
    }

    public Date getRepaymentActualTime() {
        return repaymentActualTime;
    }

    public void setRepaymentActualTime(Date repaymentActualTime) {
        this.repaymentActualTime = repaymentActualTime;
    }

    public Date getRepaymentedTime() {
        return repaymentedTime;
    }

    public void setRepaymentedTime(Date repaymentedTime) {
        this.repaymentedTime = repaymentedTime;
    }

    public Date getCapitalTime() {
        return capitalTime;
    }

    public void setCapitalTime(Date capitalTime) {
        this.capitalTime = capitalTime;
    }

    public Date getInterestTime() {
        return interestTime;
    }

    public void setInterestTime(Date interestTime) {
        this.interestTime = interestTime;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
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

    public BigDecimal getPaidOverdue() {
        return paidOverdue;
    }

    public void setPaidOverdue(BigDecimal paidOverdue) {
        this.paidOverdue = paidOverdue;
    }

    public BigDecimal getPaidCompensate() {
        return paidCompensate;
    }

    public void setPaidCompensate(BigDecimal paidCompensate) {
        this.paidCompensate = paidCompensate;
    }

    public String getInterestStart() {
        return interestStart;
    }

    public void setInterestStart(String interestStart) {
        this.interestStart = interestStart == null ? null : interestStart.trim();
    }

    public String getInterestEnd() {
        return interestEnd;
    }

    public void setInterestEnd(String interestEnd) {
        this.interestEnd = interestEnd == null ? null : interestEnd.trim();
    }

    public BigDecimal getInterestSpread() {
        return interestSpread;
    }

    public void setInterestSpread(BigDecimal interestSpread) {
        this.interestSpread = interestSpread;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

	public BigDecimal getOverdueInterestTotal() {
		return overdueInterestTotal;
	}

	public void setOverdueInterestTotal(BigDecimal overdueInterestTotal) {
		this.overdueInterestTotal = overdueInterestTotal;
	}

	public BigDecimal getOverdueCapital() {
		return overdueCapital;
	}

	public void setOverdueCapital(BigDecimal overdueCapital) {
		this.overdueCapital = overdueCapital;
	}

	public BigDecimal getPaidAllTotal() {
		return paidAllTotal;
	}

	public void setPaidAllTotal(BigDecimal paidAllTotal) {
		this.paidAllTotal = paidAllTotal;
	}

	public String getTransRemark() {
		return transRemark;
	}

	public void setTransRemark(String transRemark) {
		this.transRemark = transRemark;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
    
}