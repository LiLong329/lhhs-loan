package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanAccountInOutInfo extends BaseObject{
    private String transNo;

    private String customerId;

    private String customerType;
    
    private String customerTypeSed;

    private String customerNature;
    
    private String customerNatureSed;

    private String customerName;

    private String certificateNo;

    private String mobile;

    private BigDecimal amount;

    private String accountId;

    private String coAccountId;

    private String bankCardNo;

    private String accountHolder;

    private String bankId;

    private String bankName;

    private Date actualPayTime;

    private String auditId;

    private String transType;

    private String companyId;

    private String unionId;

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
    
    private String orderNo;
    
    private String subjectName;
    
    
    public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public void setCustomerNature(String customerNature) {
		this.customerNature = customerNature;
	}

	public String getCustomerTypeSed() {
    	String str="";
		if(null!=this.getCustomerType()){
			switch(Integer.parseInt(this.getCustomerType())){
			case 10:
				str ="个人";
			    break;
			case 20:
				str = "企业";
			    break;
			case 30:
				str = "机构";
			    break;
			case 40:
				str = "公司";
			    break;
			 default:
				 str = this.getCustomerType()+"";
			    break;
			}
		}
		return str;
	}

	public void setCustomerTypeSed(String customerTypeSed) {
		this.customerTypeSed = customerTypeSed;
	}

	public String getCustomerNatureSed() {
		String str="";
		if(null!=this.getCustomerNature()){
			switch(Integer.parseInt(this.getCustomerNature())){
			case 11:
				str ="个人";
			    break;
			case 12:
				str = "固定银主";
			    break;
			case 13:
				str = "对接银主";
			    break;
			case 14:
				str = "投资人";
			    break;
			case 15:
				str = "公户";
			    break;
			case 31:
				str = "机构";
			    break;
			case 33:
				str = "同行";
				break;
			case 41:
				str = "公司";
				break;
			case 21:
				str = "企业";
				break;
			 default:
				 str = this.getCustomerNature()+"";
			    break;
			}
		}
		return str;
	}

	public void setCustomerNatureSed(String customerNatureSed) {
		this.customerNatureSed = customerNatureSed;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo == null ? null : transNo.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getCustomerType() {
        return customerType;
    }
    public String getCustomerNature() {
        return customerNature;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo == null ? null : certificateNo.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getCoAccountId() {
        return coAccountId;
    }

    public void setCoAccountId(String coAccountId) {
        this.coAccountId = coAccountId == null ? null : coAccountId.trim();
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo == null ? null : bankCardNo.trim();
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder == null ? null : accountHolder.trim();
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId == null ? null : bankId.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public Date getActualPayTime() {
        return actualPayTime;
    }

    public void setActualPayTime(Date actualPayTime) {
        this.actualPayTime = actualPayTime;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId == null ? null : auditId.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
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