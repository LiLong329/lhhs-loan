package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.lhhs.loan.common.shared.page.BaseObject;


/**
 * 
 * 账号基本信息
 */
public class LoanAccountInfo extends BaseObject{

	private static final long serialVersionUID = 3328099907441683038L;
	private String accountId;

    private String currency;

    private String useType;

    private String ownerId;

    private String ownerName;

    private String certificateNo;

    private String accountIdParent;

    private String level;

    private String accountHolder;

    private String mobile;

    private String accountType;

    private String provienceNo;

    private String provienceName;

    private String cityNo;

    private String cityName;

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
    
    //扩展字段

    private BigDecimal assetTotal;//资产总额

    private BigDecimal liabilitiesTotal;//负债总额
    
	
    private BigDecimal repaymentCapital;//应回本金
    
    private BigDecimal repaymentInterest;//应回利息
    
    private BigDecimal amount;//账户余额
    
    private BigDecimal freezeFinancing;//理财冻结

    private BigDecimal freezeInvest;//投资冻结

    private BigDecimal freezeWithdrawals;//提现冻结
    
    private BigDecimal transitTotal;//在途金额
    
    private String recharge;//充值金额
    
	private String orgName;//机构名称
	
	private String orgCategories;//机构类别
	
	private String isGroup;
    
    public String getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup;
	}

	/**
     * 卡列表
     */
    private List<LoanAccountCard> cardList;
    /**
     * 账户余额、资产信息
     */
    private LoanAccountsTotal loanAccountsTotal;
    /**
     * 账户科目总账信息
     */
    private LoanAccountsSubjectAmount loanAccountsSubjectAmount;
   
	public List<LoanAccountCard> getCardList() {
		return cardList;
	}

	public void setCardList(List<LoanAccountCard> cardList) {
		this.cardList = cardList;
	}

	public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType == null ? null : useType.trim();
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

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo == null ? null : certificateNo.trim();
    }

    public String getAccountIdParent() {
        return accountIdParent;
    }

    public void setAccountIdParent(String accountIdParent) {
        this.accountIdParent = accountIdParent == null ? null : accountIdParent.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder == null ? null : accountHolder.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
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

	public BigDecimal getAssetTotal() {
		return assetTotal;
	}

	public void setAssetTotal(BigDecimal assetTotal) {
		this.assetTotal = assetTotal;
	}

	public BigDecimal getLiabilitiesTotal() {
		return liabilitiesTotal;
	}

	public void setLiabilitiesTotal(BigDecimal liabilitiesTotal) {
		this.liabilitiesTotal = liabilitiesTotal;
	}

	public BigDecimal getRepaymentCapital() {
		return repaymentCapital;
	}

	public void setRepaymentCapital(BigDecimal repaymentCapital) {
		this.repaymentCapital = repaymentCapital;
	}

	public BigDecimal getRepaymentInterest() {
		return repaymentInterest;
	}

	public void setRepaymentInterest(BigDecimal repaymentInterest) {
		this.repaymentInterest = repaymentInterest;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFreezeFinancing() {
		return freezeFinancing;
	}

	public void setFreezeFinancing(BigDecimal freezeFinancing) {
		this.freezeFinancing = freezeFinancing;
	}

	public BigDecimal getFreezeInvest() {
		return freezeInvest;
	}

	public void setFreezeInvest(BigDecimal freezeInvest) {
		this.freezeInvest = freezeInvest;
	}

	public BigDecimal getFreezeWithdrawals() {
		return freezeWithdrawals;
	}

	public void setFreezeWithdrawals(BigDecimal freezeWithdrawals) {
		this.freezeWithdrawals = freezeWithdrawals;
	}

	public LoanAccountsTotal getLoanAccountsTotal() {
		return loanAccountsTotal;
	}

	public void setLoanAccountsTotal(LoanAccountsTotal loanAccountsTotal) {
		this.loanAccountsTotal = loanAccountsTotal;
	}

	public LoanAccountsSubjectAmount getLoanAccountsSubjectAmount() {
		return loanAccountsSubjectAmount;
	}

	public void setLoanAccountsSubjectAmount(
			LoanAccountsSubjectAmount loanAccountsSubjectAmount) {
		this.loanAccountsSubjectAmount = loanAccountsSubjectAmount;
	}

	/**
	 * @return the transitTotal
	 */
	public BigDecimal getTransitTotal() {
		return transitTotal;
	}

	/**
	 * @param transitTotal the transitTotal to set
	 */
	public void setTransitTotal(BigDecimal transitTotal) {
		this.transitTotal = transitTotal;
	}

	/**
	 * @return the recharge
	 */
	public String getRecharge() {
		return recharge;
	}

	/**
	 * @param recharge the recharge to set
	 */
	public void setRecharge(String recharge) {
		this.recharge = recharge;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCategories() {
		return orgCategories;
	}

	public void setOrgCategories(String orgCategories) {
		this.orgCategories = orgCategories;
	}
	
}