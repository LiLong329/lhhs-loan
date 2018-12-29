package com.lhhs.loan.dao.domain;

import java.math.BigDecimal;
import java.util.Date;

public class LoanAccountsTotalHis {
    private String accountId;

    private BigDecimal assetTotal;

    private BigDecimal liabilitiesTotal;

    private BigDecimal balance;

    private BigDecimal freezeTotal;

    private BigDecimal transitTotal;

    private BigDecimal payableTotal;

    private BigDecimal receivableTotal;

    private Date belongDate;

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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getFreezeTotal() {
        return freezeTotal;
    }

    public void setFreezeTotal(BigDecimal freezeTotal) {
        this.freezeTotal = freezeTotal;
    }

    public BigDecimal getTransitTotal() {
        return transitTotal;
    }

    public void setTransitTotal(BigDecimal transitTotal) {
        this.transitTotal = transitTotal;
    }

    public BigDecimal getPayableTotal() {
        return payableTotal;
    }

    public void setPayableTotal(BigDecimal payableTotal) {
        this.payableTotal = payableTotal;
    }

    public BigDecimal getReceivableTotal() {
        return receivableTotal;
    }

    public void setReceivableTotal(BigDecimal receivableTotal) {
        this.receivableTotal = receivableTotal;
    }

    public Date getBelongDate() {
        return belongDate;
    }

    public void setBelongDate(Date belongDate) {
        this.belongDate = belongDate;
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