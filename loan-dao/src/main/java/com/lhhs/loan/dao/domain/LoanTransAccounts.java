package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanTransAccounts extends BaseObject{
    
	private static final long serialVersionUID = 8814504202539928771L;

	private String transId;

    private String transMainId;

    private String rechargeOrderNo;

    private String outAccountCustType;

    private String outAccountCustProperty;

    private String outAccountCustId;

    private String outAccountMobile;

    private String outAccountRealName;

    private String outAccountCardholder;

    private String outAccountBankCard;

    private BigDecimal outAccountPreBalance;

    private BigDecimal outAccountSufBalance;

    private String inAccountCustType;

    private String inAccountCustProperty;

    private String inAccountCustId;

    private String inAccountMobile;

    private String inAccountRealName;

    private String inAccountCardholder;

    private String inAccountBankCard;

    private BigDecimal inAccountPreBalance;

    private BigDecimal inAccountSufBalance;

    private BigDecimal financialAmount;

    private Long financialTerm;

    private String financialTermUnit;

    private BigDecimal financialInterestRate;

    private String financialInterestRateUnit;

    private String repaymentMethod;

    private String interestPaymentMethod;

    private Date interestPaymentDay;

    private BigDecimal repaymentPenaltyInterestRate;

    private BigDecimal overduePenaltyInterestRate;

    private Date actualLendingTime;

    private String contractNo;

    private Date contractStartTime;

    private Date contractEndTime;

    private Long contractTerm;

    private String contractTermUnit;

    private BigDecimal contractAmount;

    private BigDecimal contractInterestRate;

    private String contractInterestRateUnit;

    private String accountManagerNo;

    private String accountManagerName;

    private String accountManagerDepartmentNo;

    private String accountManagerDepartmentName;

    private String contractCardholder;

    private String contractCardholderIdCard;

    private String contractCardholderBankCard;

    private String outAccountRealSummary;

    private String inAccountRealSummary;

    private Date transferTime;

    private BigDecimal inAccount;

    private String status;

    private String createUser;

    private Date createTime;
    private Date createEndTime;

    private String lastUser;

    private Date lastmodifyTime;

    private String procInstId;

    private String nextAuditor;

    private String companyId;

    private String unionId;

    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;

	private BigDecimal financialAmountSum;//转行发生额总额== 理财金额总额
    
    private String organiser;//申请人
    
    private String organiserName;//申请人名称
    
    private String assigneeName;//审批人名称
    
	private String msg;//审批意见
	
	private String transType;//转账类型 1007：固定理财转账;1008：转账记账
	
	private String remarks ;//备注
	
	private String outPropertyName ;//转出账户性质名称
	
	private String outTypeName ;//转出账户客户类型名称
	
	private String inPropertyName ;//转入账户性质名称
	
	private String inTypeName ;//入账户客户类型名称
	
	private String AuditPass ;//审核结果： 1通过；0不通过
	
	private Date AuditPassTime ;//审核时间

	private String pass ;//工作流程审核结果： 90成功/通过；91失败/不通过
	
	private Date taskEndDate ;//工作流程审核时间
	private Date taskCreateDate ;//申请时间
	
	private String  accountId;//账户编码
	
	private String  outSubjectName;//转出摘要  
	
	private String  inSubjectName;//转入摘要
	
	private String  leCompanyId;//登录操作人-所属集团
	private String  leStaffName;//登录操作人-员工姓名
	private String  leGroupId;//登录操作人-部门编号
	private String  branchCompanyId;//登录操作人-所属分公司
	
	private Date  appointLendingTime;//指定日付息时间
	
	private String accountType;//账户类型 
	
	private String outAccountCustPropertySed;
	
	public String getOutAccountCustPropertySed() {
		return outAccountCustPropertySed;
	}

	public void setOutAccountCustPropertySed(String outAccountCustPropertySed) {
		this.outAccountCustPropertySed = outAccountCustPropertySed;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Date getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(Date createEndTime) {
		this.createEndTime = createEndTime;
	}

	public Date getAppointLendingTime() {
		return appointLendingTime;
	}

	public void setAppointLendingTime(Date appointLendingTime) {
		this.appointLendingTime = appointLendingTime;
	}

	public String getLeCompanyId() {
		return leCompanyId;
	}

	public void setLeCompanyId(String leCompanyId) {
		this.leCompanyId = leCompanyId;
	}

	public String getLeStaffName() {
		return leStaffName;
	}

	public void setLeStaffName(String leStaffName) {
		this.leStaffName = leStaffName;
	}

	public String getLeGroupId() {
		return leGroupId;
	}

	public void setLeGroupId(String leGroupId) {
		this.leGroupId = leGroupId;
	}

	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}

	public String getOutSubjectName() {
		return outSubjectName;
	}

	public void setOutSubjectName(String outSubjectName) {
		this.outSubjectName = outSubjectName;
	}

	public String getInSubjectName() {
		return inSubjectName;
	}

	public void setInSubjectName(String inSubjectName) {
		this.inSubjectName = inSubjectName;
	}

	public Date getTaskCreateDate() {
		return taskCreateDate;
	}

	public void setTaskCreateDate(Date taskCreateDate) {
		this.taskCreateDate = taskCreateDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Date getTaskEndDate() {
		return taskEndDate;
	}

	public void setTaskEndDate(Date taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	public Date getAuditPassTime() {
		return AuditPassTime;
	}

	public void setAuditPassTime(Date auditPassTime) {
		AuditPassTime = auditPassTime;
	}

	public String getAuditPass() {
		return AuditPass;
	}

	public void setAuditPass(String auditPass) {
		AuditPass = auditPass;
	}

	public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId == null ? null : transId.trim();
    }

    public String getTransMainId() {
        return transMainId;
    }

    public void setTransMainId(String transMainId) {
        this.transMainId = transMainId == null ? null : transMainId.trim();
    }

    public String getRechargeOrderNo() {
        return rechargeOrderNo;
    }

    public void setRechargeOrderNo(String rechargeOrderNo) {
        this.rechargeOrderNo = rechargeOrderNo == null ? null : rechargeOrderNo.trim();
    }

    public String getOutAccountCustType() {
        return outAccountCustType;
    }

    public void setOutAccountCustType(String outAccountCustType) {
        this.outAccountCustType = outAccountCustType == null ? null : outAccountCustType.trim();
    }

    public String getOutAccountCustProperty() {
        return outAccountCustProperty;
    }

    public void setOutAccountCustProperty(String outAccountCustProperty) {
        this.outAccountCustProperty = outAccountCustProperty == null ? null : outAccountCustProperty.trim();
    }

    public String getOutAccountCustId() {
        return outAccountCustId;
    }

    public void setOutAccountCustId(String outAccountCustId) {
        this.outAccountCustId = outAccountCustId == null ? null : outAccountCustId.trim();
    }

    public String getOutAccountMobile() {
        return outAccountMobile;
    }

    public void setOutAccountMobile(String outAccountMobile) {
        this.outAccountMobile = outAccountMobile == null ? null : outAccountMobile.trim();
    }

    public String getOutAccountRealName() {
        return outAccountRealName;
    }

    public void setOutAccountRealName(String outAccountRealName) {
        this.outAccountRealName = outAccountRealName == null ? null : outAccountRealName.trim();
    }

    public String getOutAccountCardholder() {
        return outAccountCardholder;
    }

    public void setOutAccountCardholder(String outAccountCardholder) {
        this.outAccountCardholder = outAccountCardholder == null ? null : outAccountCardholder.trim();
    }

    public String getOutAccountBankCard() {
        return outAccountBankCard;
    }

    public void setOutAccountBankCard(String outAccountBankCard) {
        this.outAccountBankCard = outAccountBankCard == null ? null : outAccountBankCard.trim();
    }

    public BigDecimal getOutAccountPreBalance() {
        return outAccountPreBalance;
    }

    public void setOutAccountPreBalance(BigDecimal outAccountPreBalance) {
        this.outAccountPreBalance = outAccountPreBalance;
    }

    public BigDecimal getOutAccountSufBalance() {
        return outAccountSufBalance;
    }

    public void setOutAccountSufBalance(BigDecimal outAccountSufBalance) {
        this.outAccountSufBalance = outAccountSufBalance;
    }

    public String getInAccountCustType() {
        return inAccountCustType;
    }

    public void setInAccountCustType(String inAccountCustType) {
        this.inAccountCustType = inAccountCustType == null ? null : inAccountCustType.trim();
    }

    public String getInAccountCustProperty() {
        return inAccountCustProperty;
    }

    public void setInAccountCustProperty(String inAccountCustProperty) {
        this.inAccountCustProperty = inAccountCustProperty == null ? null : inAccountCustProperty.trim();
    }

    public String getInAccountCustId() {
        return inAccountCustId;
    }

    public void setInAccountCustId(String inAccountCustId) {
        this.inAccountCustId = inAccountCustId == null ? null : inAccountCustId.trim();
    }

    public String getInAccountMobile() {
        return inAccountMobile;
    }

    public void setInAccountMobile(String inAccountMobile) {
        this.inAccountMobile = inAccountMobile == null ? null : inAccountMobile.trim();
    }

    public String getInAccountRealName() {
        return inAccountRealName;
    }

    public void setInAccountRealName(String inAccountRealName) {
        this.inAccountRealName = inAccountRealName == null ? null : inAccountRealName.trim();
    }

    public String getInAccountCardholder() {
        return inAccountCardholder;
    }

    public void setInAccountCardholder(String inAccountCardholder) {
        this.inAccountCardholder = inAccountCardholder == null ? null : inAccountCardholder.trim();
    }

    public String getInAccountBankCard() {
        return inAccountBankCard;
    }

    public void setInAccountBankCard(String inAccountBankCard) {
        this.inAccountBankCard = inAccountBankCard == null ? null : inAccountBankCard.trim();
    }

    public BigDecimal getInAccountPreBalance() {
        return inAccountPreBalance;
    }

    public void setInAccountPreBalance(BigDecimal inAccountPreBalance) {
        this.inAccountPreBalance = inAccountPreBalance;
    }

    public BigDecimal getInAccountSufBalance() {
        return inAccountSufBalance;
    }

    public void setInAccountSufBalance(BigDecimal inAccountSufBalance) {
        this.inAccountSufBalance = inAccountSufBalance;
    }

    public BigDecimal getFinancialAmount() {
        return financialAmount;
    }

    public void setFinancialAmount(BigDecimal financialAmount) {
        this.financialAmount = financialAmount;
    }

    public Long getFinancialTerm() {
        return financialTerm;
    }

    public void setFinancialTerm(Long financialTerm) {
        this.financialTerm = financialTerm;
    }

    public String getFinancialTermUnit() {
        return financialTermUnit;
    }

    public void setFinancialTermUnit(String financialTermUnit) {
        this.financialTermUnit = financialTermUnit == null ? null : financialTermUnit.trim();
    }


    public String getFinancialInterestRateUnit() {
        return financialInterestRateUnit;
    }

    public void setFinancialInterestRateUnit(String financialInterestRateUnit) {
        this.financialInterestRateUnit = financialInterestRateUnit == null ? null : financialInterestRateUnit.trim();
    }

    public String getRepaymentMethod() {
        return repaymentMethod;
    }

    public void setRepaymentMethod(String repaymentMethod) {
        this.repaymentMethod = repaymentMethod == null ? null : repaymentMethod.trim();
    }

    public String getInterestPaymentMethod() {
        return interestPaymentMethod;
    }

    public void setInterestPaymentMethod(String interestPaymentMethod) {
        this.interestPaymentMethod = interestPaymentMethod == null ? null : interestPaymentMethod.trim();
    }

    public Date getInterestPaymentDay() {
        return interestPaymentDay;
    }

    public void setInterestPaymentDay(Date interestPaymentDay) {
        this.interestPaymentDay = interestPaymentDay;
    }


    public Date getActualLendingTime() {
        return actualLendingTime;
    }

    public void setActualLendingTime(Date actualLendingTime) {
        this.actualLendingTime = actualLendingTime;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public Date getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(Date contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public Date getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(Date contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public Long getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(Long contractTerm) {
        this.contractTerm = contractTerm;
    }

    public String getContractTermUnit() {
        return contractTermUnit;
    }

    public void setContractTermUnit(String contractTermUnit) {
        this.contractTermUnit = contractTermUnit == null ? null : contractTermUnit.trim();
    }

    
    public BigDecimal getFinancialInterestRate() {
		return financialInterestRate;
	}

	public void setFinancialInterestRate(BigDecimal financialInterestRate) {
		this.financialInterestRate = financialInterestRate;
	}

	public BigDecimal getRepaymentPenaltyInterestRate() {
		return repaymentPenaltyInterestRate;
	}

	public void setRepaymentPenaltyInterestRate(BigDecimal repaymentPenaltyInterestRate) {
		this.repaymentPenaltyInterestRate = repaymentPenaltyInterestRate;
	}

	public BigDecimal getOverduePenaltyInterestRate() {
		return overduePenaltyInterestRate;
	}

	public void setOverduePenaltyInterestRate(BigDecimal overduePenaltyInterestRate) {
		this.overduePenaltyInterestRate = overduePenaltyInterestRate;
	}

	public BigDecimal getContractInterestRate() {
		return contractInterestRate;
	}

	public void setContractInterestRate(BigDecimal contractInterestRate) {
		this.contractInterestRate = contractInterestRate;
	}

	public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getContractInterestRateUnit() {
        return contractInterestRateUnit;
    }

    public void setContractInterestRateUnit(String contractInterestRateUnit) {
        this.contractInterestRateUnit = contractInterestRateUnit == null ? null : contractInterestRateUnit.trim();
    }

    public String getAccountManagerNo() {
        return accountManagerNo;
    }

    public void setAccountManagerNo(String accountManagerNo) {
        this.accountManagerNo = accountManagerNo == null ? null : accountManagerNo.trim();
    }

    public String getAccountManagerName() {
        return accountManagerName;
    }

    public void setAccountManagerName(String accountManagerName) {
        this.accountManagerName = accountManagerName == null ? null : accountManagerName.trim();
    }

    public String getAccountManagerDepartmentNo() {
        return accountManagerDepartmentNo;
    }

    public void setAccountManagerDepartmentNo(String accountManagerDepartmentNo) {
        this.accountManagerDepartmentNo = accountManagerDepartmentNo == null ? null : accountManagerDepartmentNo.trim();
    }

    public String getAccountManagerDepartmentName() {
        return accountManagerDepartmentName;
    }

    public void setAccountManagerDepartmentName(String accountManagerDepartmentName) {
        this.accountManagerDepartmentName = accountManagerDepartmentName == null ? null : accountManagerDepartmentName.trim();
    }

    public String getContractCardholder() {
        return contractCardholder;
    }

    public void setContractCardholder(String contractCardholder) {
        this.contractCardholder = contractCardholder == null ? null : contractCardholder.trim();
    }

    public String getContractCardholderIdCard() {
        return contractCardholderIdCard;
    }

    public void setContractCardholderIdCard(String contractCardholderIdCard) {
        this.contractCardholderIdCard = contractCardholderIdCard == null ? null : contractCardholderIdCard.trim();
    }

    public String getContractCardholderBankCard() {
        return contractCardholderBankCard;
    }

    public void setContractCardholderBankCard(String contractCardholderBankCard) {
        this.contractCardholderBankCard = contractCardholderBankCard == null ? null : contractCardholderBankCard.trim();
    }

    public String getOutAccountRealSummary() {
        return outAccountRealSummary;
    }

    public void setOutAccountRealSummary(String outAccountRealSummary) {
        this.outAccountRealSummary = outAccountRealSummary == null ? null : outAccountRealSummary.trim();
    }

    public String getInAccountRealSummary() {
        return inAccountRealSummary;
    }

    public void setInAccountRealSummary(String inAccountRealSummary) {
        this.inAccountRealSummary = inAccountRealSummary == null ? null : inAccountRealSummary.trim();
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public BigDecimal getInAccount() {
        return inAccount;
    }

    public void setInAccount(BigDecimal inAccount) {
        this.inAccount = inAccount;
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

    public Date getLastmodifyTime() {
        return lastmodifyTime;
    }

    public void setLastmodifyTime(Date lastmodifyTime) {
        this.lastmodifyTime = lastmodifyTime;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId == null ? null : procInstId.trim();
    }

    public String getNextAuditor() {
        return nextAuditor;
    }

    public void setNextAuditor(String nextAuditor) {
        this.nextAuditor = nextAuditor == null ? null : nextAuditor.trim();
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

	public BigDecimal getFinancialAmountSum() {
		return financialAmountSum;
	}

	public void setFinancialAmountSum(BigDecimal financialAmountSum) {
		this.financialAmountSum = financialAmountSum;
	}

	public String getOrganiser() {
		return organiser;
	}

	public void setOrganiser(String organiser) {
		this.organiser = organiser;
	}

	public String getOrganiserName() {
		return organiserName;
	}

	public void setOrganiserName(String organiserName) {
		this.organiserName = organiserName;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getOutPropertyName() {
		return outPropertyName;
	}

	public void setOutPropertyName(String outPropertyName) {
		this.outPropertyName = outPropertyName;
	}

	public String getOutTypeName() {
		return outTypeName;
	}

	public void setOutTypeName(String outTypeName) {
		this.outTypeName = outTypeName;
	}

	public String getInPropertyName() {
		return inPropertyName;
	}

	public void setInPropertyName(String inPropertyName) {
		this.inPropertyName = inPropertyName;
	}

	public String getInTypeName() {
		return inTypeName;
	}

	public void setInTypeName(String inTypeName) {
		this.inTypeName = inTypeName;
	}
    
}