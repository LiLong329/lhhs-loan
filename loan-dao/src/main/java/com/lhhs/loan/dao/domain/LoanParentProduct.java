package com.lhhs.loan.dao.domain;

import java.util.Date;

public class LoanParentProduct {
    private String productNo;

    private String productType;

    private String productMoney;

    private String productTerm;

    private String productTermUnit;

    private String productInterest;

    private String thumbnailPicture;

    private String productPicture;

    private String productState;

    private Date createDate;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo == null ? null : productNo.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getProductMoney() {
        return productMoney;
    }

    public void setProductMoney(String productMoney) {
        this.productMoney = productMoney == null ? null : productMoney.trim();
    }

    public String getProductTerm() {
        return productTerm;
    }

    public void setProductTerm(String productTerm) {
        this.productTerm = productTerm == null ? null : productTerm.trim();
    }

    public String getProductTermUnit() {
        return productTermUnit;
    }

    public void setProductTermUnit(String productTermUnit) {
        this.productTermUnit = productTermUnit == null ? null : productTermUnit.trim();
    }

    public String getProductInterest() {
        return productInterest;
    }

    public void setProductInterest(String productInterest) {
        this.productInterest = productInterest == null ? null : productInterest.trim();
    }

    public String getThumbnailPicture() {
        return thumbnailPicture;
    }

    public void setThumbnailPicture(String thumbnailPicture) {
        this.thumbnailPicture = thumbnailPicture == null ? null : thumbnailPicture.trim();
    }

    public String getProductPicture() {
        return productPicture;
    }

    public void setProductPicture(String productPicture) {
        this.productPicture = productPicture == null ? null : productPicture.trim();
    }

    public String getProductState() {
        return productState;
    }

    public void setProductState(String productState) {
        this.productState = productState == null ? null : productState.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}