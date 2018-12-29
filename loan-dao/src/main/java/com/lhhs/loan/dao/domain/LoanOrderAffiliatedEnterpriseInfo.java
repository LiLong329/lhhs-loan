package com.lhhs.loan.dao.domain;

import java.util.List;

public class LoanOrderAffiliatedEnterpriseInfo {
    private Long affiliatedEnterpriseId;

    private String orderNo;

    private String companyName;

    private String name;

    private String position;

    private String companyAddress;

    private String companyExplain;
    
    private List<LoanAffiliatedEnterpriseUrl> urlList;
    
    private String urlIdList;

    public Long getAffiliatedEnterpriseId() {
        return affiliatedEnterpriseId;
    }

    public void setAffiliatedEnterpriseId(Long affiliatedEnterpriseId) {
        this.affiliatedEnterpriseId = affiliatedEnterpriseId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress == null ? null : companyAddress.trim();
    }

    public String getCompanyExplain() {
        return companyExplain;
    }

    public void setCompanyExplain(String companyExplain) {
        this.companyExplain = companyExplain == null ? null : companyExplain.trim();
    }

	public List<LoanAffiliatedEnterpriseUrl> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<LoanAffiliatedEnterpriseUrl> urlList) {
		this.urlList = urlList;
	}

	public String getUrlIdList() {
		return urlIdList;
	}

	public void setUrlIdList(String urlIdList) {
		this.urlIdList = urlIdList;
	}

}