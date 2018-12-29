package com.lhhs.loan.dao.domain;

public class LoanOrderBorrowerExtendWithBLOBs extends LoanOrderBorrowerExtend {
    private String spouseCredit;

    private String litigaStatus;

    private String loadPurpose;

    private String payment;

    private String spouseLitiga;

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
}