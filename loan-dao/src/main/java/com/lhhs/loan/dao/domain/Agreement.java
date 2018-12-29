package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.lhhs.loan.common.shared.page.BaseObject;

public class Agreement extends BaseObject{
	
	private static final long serialVersionUID = -3069360198674276039L;

	private Integer id;

    private String agreementNo;

    private String orderNo;

    private String borrowerName;

    private String borrowerIdNum;

    private String investorName;

    private String capitalName;

    private BigDecimal loanAmount;

    private BigDecimal amount;

    private Integer loanTerm;

    private String loanTermUnit;

    private Date startDate;

    private Date endDate;

    private Date createTime;

    private Date updateTime;

    private String auditingStatus;

    private String remark;

    private String companyId;

    private String unionId;

    private String deptId;

    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;

    private String type;
    
    /**
     * 联系人列表
     */
    private List<RelevantPersonAgreement> contactsList;
    /**
     * 共借人列表
     */
    private List<RelevantPersonAgreement> totalLoanList;
    /**
     * 担保人列表
     */
    private List<RelevantPersonAgreement> guaranteeList;
    
    private String rpaList;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgreementNo() {
        return agreementNo;
    }

    public void setAgreementNo(String agreementNo) {
        this.agreementNo = agreementNo == null ? null : agreementNo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName == null ? null : borrowerName.trim();
    }

    public String getBorrowerIdNum() {
        return borrowerIdNum;
    }

    public void setBorrowerIdNum(String borrowerIdNum) {
        this.borrowerIdNum = borrowerIdNum == null ? null : borrowerIdNum.trim();
    }

    public String getInvestorName() {
        return investorName;
    }

    public void setInvestorName(String investorName) {
        this.investorName = investorName == null ? null : investorName.trim();
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName == null ? null : capitalName.trim();
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public String getLoanTermUnit() {
        return loanTermUnit;
    }

    public void setLoanTermUnit(String loanTermUnit) {
        this.loanTermUnit = loanTermUnit == null ? null : loanTermUnit.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAuditingStatus() {
        return auditingStatus;
    }

    public void setAuditingStatus(String auditingStatus) {
        this.auditingStatus = auditingStatus == null ? null : auditingStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

	public List<RelevantPersonAgreement> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<RelevantPersonAgreement> contactsList) {
		this.contactsList = contactsList;
	}

	public List<RelevantPersonAgreement> getTotalLoanList() {
		return totalLoanList;
	}

	public void setTotalLoanList(List<RelevantPersonAgreement> totalLoanList) {
		this.totalLoanList = totalLoanList;
	}

	public List<RelevantPersonAgreement> getGuaranteeList() {
		return guaranteeList;
	}

	public void setGuaranteeList(List<RelevantPersonAgreement> guaranteeList) {
		this.guaranteeList = guaranteeList;
	}

	public String getRpaList() {
		return rpaList;
	}

	public void setRpaList(String rpaList) {
		this.rpaList = rpaList;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}