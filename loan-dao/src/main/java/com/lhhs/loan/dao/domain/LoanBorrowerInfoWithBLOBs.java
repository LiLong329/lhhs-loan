package com.lhhs.loan.dao.domain;

import java.util.ArrayList;
import java.util.List;

public class LoanBorrowerInfoWithBLOBs extends LoanBorrowerInfo {
    private String spouseCredit;

    private String litigaStatus;

    private String loadPurpose;

    private String payment;

    private String spouseLitiga;
    
    private String creditStatus;
    
    private String customerMobile;
    
    private String customerNature;
    
    private String hidBankCardNo;
    
    private Long bankCardId;//账户卡号关联信息表id
    
    private String bankCardNo;

    private String bankId;

    private String bankName;
    
    private String branchName;//开户支行名称

    private String managerId;//客户经理id
    
    private String deptId;//客户经理归属部门或分组
    
    private String[] bankList;
    
	public String[] getBankList() {
		return bankList;
	}

	public void setBankList(String[] bankList) {
		this.bankList = bankList;
	}

	public Long getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(Long bankCardId) {
		this.bankCardId = bankCardId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getHidBankCardNo() {
		return hidBankCardNo;
	}

	public void setHidBankCardNo(String hidBankCardNo) {
		this.hidBankCardNo = hidBankCardNo;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSpouseCredit() {
        return spouseCredit;
    }

    public void setSpouseCredit(String spouseCredit) {
        this.spouseCredit = spouseCredit == null ? null : spouseCredit.trim();
    }

    public String getLitigaStatus() {
        return litigaStatus;
    }

    public void setLitigaStatus(String litigaStatus) {
        this.litigaStatus = litigaStatus == null ? null : litigaStatus.trim();
    }

    public String getLoadPurpose() {
        return loadPurpose;
    }

    public void setLoadPurpose(String loadPurpose) {
        this.loadPurpose = loadPurpose == null ? null : loadPurpose.trim();
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
    }

    public String getSpouseLitiga() {
        return spouseLitiga;
    }

    public void setSpouseLitiga(String spouseLitiga) {
        this.spouseLitiga = spouseLitiga == null ? null : spouseLitiga.trim();
    }
    
    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus == null ? null : creditStatus.trim();
    }
    
    public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerNature() {
		return customerNature;
	}

	public void setCustomerNature(String customerNature) {
		this.customerNature = customerNature;
	}
}