package com.lhhs.loan.dao.domain;

public class LoanExTaskWithBLOBs extends LoanExTask {
    private String letSuggestion;

    private String letRemark;

    public String getLetSuggestion() {
        return letSuggestion;
    }

    public void setLetSuggestion(String letSuggestion) {
        this.letSuggestion = letSuggestion == null ? null : letSuggestion.trim();
    }

    public String getLetRemark() {
        return letRemark;
    }

    public void setLetRemark(String letRemark) {
        this.letRemark = letRemark == null ? null : letRemark.trim();
    }
}