package com.lhhs.loan.dao.domain;

import java.util.Date;

public class LoanRole {
    private Integer lrRoleId;

    private String lrName;

    private Date lrTime;

    private String lrStatus;

    public Integer getLrRoleId() {
        return lrRoleId;
    }

    public void setLrRoleId(Integer lrRoleId) {
        this.lrRoleId = lrRoleId;
    }

    public String getLrName() {
        return lrName;
    }

    public void setLrName(String lrName) {
        this.lrName = lrName == null ? null : lrName.trim();
    }

    public Date getLrTime() {
        return lrTime;
    }

    public void setLrTime(Date lrTime) {
        this.lrTime = lrTime;
    }

    public String getLrStatus() {
        return lrStatus;
    }

    public void setLrStatus(String lrStatus) {
        this.lrStatus = lrStatus == null ? null : lrStatus.trim();
    }
}