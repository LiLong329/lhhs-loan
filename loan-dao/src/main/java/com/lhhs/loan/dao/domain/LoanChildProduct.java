package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class LoanChildProduct {
	
    private String productId;

    private String productParentNo;

    private String productName;

    private Long fundOwner;

    private Integer minAmount;

    private Integer maxAmount;

    private Integer minExpires;

    private Integer maxExpires;

    private String expiresUnit;

    private BigDecimal minRate;

    private BigDecimal maxRate;

    private String rateUnit;

    private BigDecimal minRateTwo;

    private BigDecimal maxRateTwo;

    private String rateUnitTwo;

    private String litimgUrl;

    private String productImage;

    private Date createTime;

    private String productStatus;
    
	/*****临时字段*********/
    private String orgName;
    
    private String productType;
	//资质模板
	private List<LoanCredentials> credentialList;
	//产品关联的资质模板
    private List<LoanProductCredentials> productCredentials;
    //二级产品关联的区域
    private List<LoanProductSupportAreas> productSupportAreas;
    
	public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }
    
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductParentNo() {
        return productParentNo;
    }

    public void setProductParentNo(String productParentNo) {
        this.productParentNo = productParentNo == null ? null : productParentNo.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Long getFundOwner() {
        return fundOwner;
    }

    public void setFundOwner(Long fundOwner) {
        this.fundOwner = fundOwner;
    }

    public Integer getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Integer getMinExpires() {
        return minExpires;
    }

    public void setMinExpires(Integer minExpires) {
        this.minExpires = minExpires;
    }

    public Integer getMaxExpires() {
        return maxExpires;
    }

    public void setMaxExpires(Integer maxExpires) {
        this.maxExpires = maxExpires;
    }

    public String getExpiresUnit() {
        return expiresUnit;
    }

    public void setExpiresUnit(String expiresUnit) {
        this.expiresUnit = expiresUnit == null ? null : expiresUnit.trim();
    }

    public BigDecimal getMinRate() {
		return minRate;
	}

	public void setMinRate(BigDecimal minRate) {
		this.minRate = minRate;
	}

	public BigDecimal getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(BigDecimal maxRate) {
		this.maxRate = maxRate;
	}

	public BigDecimal getMinRateTwo() {
		return minRateTwo;
	}

	public void setMinRateTwo(BigDecimal minRateTwo) {
		this.minRateTwo = minRateTwo;
	}

	public BigDecimal getMaxRateTwo() {
		return maxRateTwo;
	}

	public void setMaxRateTwo(BigDecimal maxRateTwo) {
		this.maxRateTwo = maxRateTwo;
	}

	public String getRateUnit() {
        return rateUnit;
    }

    public void setRateUnit(String rateUnit) {
        this.rateUnit = rateUnit == null ? null : rateUnit.trim();
    }

    public String getRateUnitTwo() {
        return rateUnitTwo;
    }

    public void setRateUnitTwo(String rateUnitTwo) {
        this.rateUnitTwo = rateUnitTwo == null ? null : rateUnitTwo.trim();
    }

    public String getLitimgUrl() {
        return litimgUrl;
    }

    public void setLitimgUrl(String litimgUrl) {
        this.litimgUrl = litimgUrl == null ? null : litimgUrl.trim();
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus == null ? null : productStatus.trim();
    }
    
	public List<LoanProductCredentials> getProductCredentials() {
		return productCredentials;
	}

	public void setProductCredentials(
			List<LoanProductCredentials> productCredentials) {
		this.productCredentials = productCredentials;
	}

	public List<LoanProductSupportAreas> getProductSupportAreas() {
		return productSupportAreas;
	}

	public void setProductSupportAreas(
			List<LoanProductSupportAreas> productSupportAreas) {
		this.productSupportAreas = productSupportAreas;
	}

	public List<LoanCredentials> getCredentialList() {
		return credentialList;
	}

	public void setCredentialList(List<LoanCredentials> credentialList) {
		this.credentialList = credentialList;
	}
    
}