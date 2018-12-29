package com.lhhs.loan.dao.domain;

public class LoanRoleCompany {
    private Integer lrcId;

    private String roleId;

    private String companyId;

    public Integer getLrcId() {
        return lrcId;
    }

    public void setLrcId(Integer lrcId) {
        this.lrcId = lrcId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }
}