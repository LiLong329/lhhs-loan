package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.lhhs.loan.common.shared.page.BaseObject;

/**
 * 
 * 账户交易记录表（个人、机构、公司）
 */
public class LoanAccountsTrans extends BaseObject{
    
	private static final long serialVersionUID = -3423976878030021685L;
    private String transId;

    private String accountId;

    private String accountName;

    private String opponentAccountId;

    private String opponentAccountName;

    private String subjectId;

    private String subjectName;

    private String transMainId;

    private BigDecimal amount;

    private BigDecimal debitAmount;

    private BigDecimal creditAmount;

    private String direction;

    private Date transTime;

    private String transRemark;

    private String transYear;

    private String currency;

    private String transCertificateId;

    private String ownerId;

    private String ownerName;

    private String businessId;

    private String orderNo;

    private String transType;

    private String transName;

    private String companyId;

    private String unionId;

    private BigDecimal amountTotal;

    private BigDecimal balance;

    private String businessType;

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
    //原交易流水号、冻结流水号
    private String oldTransId;
    //冲销解冻标识
    private String writeOffFlag;
    /**增量冻结标识**/
    private String updateFlag;
    private String provienceNo;

    private String provienceName;

    private String cityNo;

    //辅助字段
    private String cityName;//城市
    
    private String mobile;//手机号
    /**息差**/
    private BigDecimal interestSpread;
    //在途金额
    private BigDecimal onTheWayAmount;
    //交易对手科目编号
    private String opponentSubjectId;
    //交易对手科目名称
    private String opponentSubjectName;
    //账户所属用户分类
    private String accountType;
    
    /**科目编号列表**/
    private List<String> listSubjectId;
    /**科目编号列表**/
    private String subjectIds;
    //不显示冻结交易标识 Y
    private String  transTypeFlag;
    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId == null ? null : transId.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getOpponentAccountId() {
        return opponentAccountId;
    }

    public void setOpponentAccountId(String opponentAccountId) {
        this.opponentAccountId = opponentAccountId == null ? null : opponentAccountId.trim();
    }

    public String getOpponentAccountName() {
        return opponentAccountName;
    }

    public void setOpponentAccountName(String opponentAccountName) {
        this.opponentAccountName = opponentAccountName == null ? null : opponentAccountName.trim();
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId == null ? null : subjectId.trim();
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    public String getTransMainId() {
        return transMainId;
    }

    public void setTransMainId(String transMainId) {
        this.transMainId = transMainId == null ? null : transMainId.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(BigDecimal debitAmount) {
        this.debitAmount = debitAmount;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getTransRemark() {
        return transRemark;
    }

    public void setTransRemark(String transRemark) {
        this.transRemark = transRemark == null ? null : transRemark.trim();
    }

    public String getTransYear() {
        return transYear;
    }

    public void setTransYear(String transYear) {
        this.transYear = transYear == null ? null : transYear.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getTransCertificateId() {
        return transCertificateId;
    }

    public void setTransCertificateId(String transCertificateId) {
        this.transCertificateId = transCertificateId == null ? null : transCertificateId.trim();
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId == null ? null : ownerId.trim();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId == null ? null : businessId.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName == null ? null : transName.trim();
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

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOldTransId() {
		return oldTransId;
	}

	public void setOldTransId(String oldTransId) {
		this.oldTransId = oldTransId;
	}

	public String getWriteOffFlag() {
		return writeOffFlag;
	}

	public void setWriteOffFlag(String writeOffFlag) {
		this.writeOffFlag = writeOffFlag;
	}

	public String getProvienceNo() {
		return provienceNo;
	}

	public void setProvienceNo(String provienceNo) {
		this.provienceNo = provienceNo;
	}

	public String getProvienceName() {
		return provienceName;
	}

	public void setProvienceName(String provienceName) {
		this.provienceName = provienceName;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public BigDecimal getInterestSpread() {
		return interestSpread;
	}

	public void setInterestSpread(BigDecimal interestSpread) {
		this.interestSpread = interestSpread;
	}

	public BigDecimal getOnTheWayAmount() {
		return onTheWayAmount;
	}

	public void setOnTheWayAmount(BigDecimal onTheWayAmount) {
		this.onTheWayAmount = onTheWayAmount;
	}

	public String getOpponentSubjectId() {
		return opponentSubjectId;
	}

	public void setOpponentSubjectId(String opponentSubjectId) {
		this.opponentSubjectId = opponentSubjectId;
	}

	public String getOpponentSubjectName() {
		return opponentSubjectName;
	}

	public void setOpponentSubjectName(String opponentSubjectName) {
		this.opponentSubjectName = opponentSubjectName;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public List<String> getListSubjectId() {
		return listSubjectId;
	}

	public void setListSubjectId(List<String> listSubjectId) {
		this.listSubjectId = listSubjectId;
	}

	public String getSubjectIds() {
		return subjectIds;
	}

	public void setSubjectIds(String subjectIds) {
		this.subjectIds = subjectIds;
	}

	public String getTransTypeFlag() {
		return transTypeFlag;
	}

	public void setTransTypeFlag(String transTypeFlag) {
		this.transTypeFlag = transTypeFlag;
	}
	
}