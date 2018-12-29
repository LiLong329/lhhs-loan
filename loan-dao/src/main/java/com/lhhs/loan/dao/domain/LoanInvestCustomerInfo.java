package com.lhhs.loan.dao.domain;

import java.util.Date;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanInvestCustomerInfo extends BaseObject{
    private String id;

    private String customerId;

    private String investCustomerType;

    private String investCustomerNature;

    private String investCustomerName;

    private String investCustomerMobile;

    private String idNum;

    private String affiliatedCompanyId;

    private String affiliatedCompany;

    private String accountId;

    private String unionId;

    private String companyId;

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
    
    private String sex;//性别
    
    private String custType;//客户类型
    
	private String bankCardNo ;//银行卡号
	
	private String branchName ;//开户行支行
	
	private String bankName ;//开户行名称
	
	private String bankId ;//开户行id
	
	private String customerNatureName;//投资人客户性质
	
	private String customerTypeName;//客户类型
	
	private String CorName;//所在公司

    private String accountManagerNo;

    private String accountManagerName;

    private String accountManagerDepartmentNo;

    private String accountManagerDepartmentName;
    
    private String custId;//个人信息表主键
    
    private String accountManagerGroupId;//组别名称id
    
    private String accountManagerGroupName;//组别名称
    
    
    public String getAccountManagerGroupName() {
		return accountManagerGroupName;
	}

	public void setAccountManagerGroupName(String accountManagerGroupName) {
		this.accountManagerGroupName = accountManagerGroupName;
	}

	public String getAccountManagerGroupId() {
		return accountManagerGroupId;
	}

	public void setAccountManagerGroupId(String accountManagerGroupId) {
		this.accountManagerGroupId = accountManagerGroupId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getAccountManagerNo() {
		return accountManagerNo;
	}

	public void setAccountManagerNo(String accountManagerNo) {
		this.accountManagerNo = accountManagerNo;
	}

	public String getAccountManagerName() {
		return accountManagerName;
	}

	public void setAccountManagerName(String accountManagerName) {
		this.accountManagerName = accountManagerName;
	}

	public String getAccountManagerDepartmentNo() {
		return accountManagerDepartmentNo;
	}

	public void setAccountManagerDepartmentNo(String accountManagerDepartmentNo) {
		this.accountManagerDepartmentNo = accountManagerDepartmentNo;
	}

	public String getAccountManagerDepartmentName() {
		return accountManagerDepartmentName;
	}

	public void setAccountManagerDepartmentName(String accountManagerDepartmentName) {
		this.accountManagerDepartmentName = accountManagerDepartmentName;
	}

	public String getCorName() {
		return CorName;
	}

	public void setCorName(String corName) {
		CorName = corName;
	}

	public String getCustomerNatureName() {
		return customerNatureName;
	}

	public void setCustomerNatureName(String customerNatureName) {
		this.customerNatureName = customerNatureName;
	}

	public String getCustomerTypeName() {
		return customerTypeName;
	}

	public void setCustomerTypeName(String customerTypeName) {
		this.customerTypeName = customerTypeName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getInvestCustomerType() {
        return investCustomerType;
    }

    public void setInvestCustomerType(String investCustomerType) {
        this.investCustomerType = investCustomerType == null ? null : investCustomerType.trim();
    }

    public String getInvestCustomerNature() {
        return investCustomerNature;
    }

    public void setInvestCustomerNature(String investCustomerNature) {
        this.investCustomerNature = investCustomerNature == null ? null : investCustomerNature.trim();
    }

    public String getInvestCustomerName() {
        return investCustomerName;
    }

    public void setInvestCustomerName(String investCustomerName) {
        this.investCustomerName = investCustomerName == null ? null : investCustomerName.trim();
    }

    public String getInvestCustomerMobile() {
        return investCustomerMobile;
    }

    public void setInvestCustomerMobile(String investCustomerMobile) {
        this.investCustomerMobile = investCustomerMobile == null ? null : investCustomerMobile.trim();
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum == null ? null : idNum.trim();
    }

    public String getAffiliatedCompanyId() {
        return affiliatedCompanyId;
    }

    public void setAffiliatedCompanyId(String affiliatedCompanyId) {
        this.affiliatedCompanyId = affiliatedCompanyId == null ? null : affiliatedCompanyId.trim();
    }

    public String getAffiliatedCompany() {
        return affiliatedCompany;
    }

    public void setAffiliatedCompany(String affiliatedCompany) {
        this.affiliatedCompany = affiliatedCompany == null ? null : affiliatedCompany.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
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
}