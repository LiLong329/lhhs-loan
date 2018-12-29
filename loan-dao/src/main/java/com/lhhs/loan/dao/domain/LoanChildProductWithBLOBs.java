package com.lhhs.loan.dao.domain;

public class LoanChildProductWithBLOBs extends LoanChildProduct {
    private String productAdvantage;

    private String feeDescription;

    private String applicationConditions;

    private String materialRequested;

    public String getProductAdvantage() {
        return productAdvantage;
    }

    public void setProductAdvantage(String productAdvantage) {
        this.productAdvantage = productAdvantage == null ? null : productAdvantage.trim();
    }

    public String getFeeDescription() {
        return feeDescription;
    }

    public void setFeeDescription(String feeDescription) {
        this.feeDescription = feeDescription == null ? null : feeDescription.trim();
    }

    public String getApplicationConditions() {
        return applicationConditions;
    }

    public void setApplicationConditions(String applicationConditions) {
        this.applicationConditions = applicationConditions == null ? null : applicationConditions.trim();
    }

    public String getMaterialRequested() {
        return materialRequested;
    }

    public void setMaterialRequested(String materialRequested) {
        this.materialRequested = materialRequested == null ? null : materialRequested.trim();
    }
}