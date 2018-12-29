package com.lhhs.loan.dao.domain;

import java.util.Date;

import com.lhhs.loan.common.shared.page.BaseObject;

public class SipAccount extends BaseObject{

	private static final long serialVersionUID = -472523003278971095L;

	private Integer id;

    private String accountsid;

    private String sipnumber;

    private Date cts;

    private String password;

    private String userId;

    private String deptId;

    private String companyId;

    private String unionId;

    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountsid() {
        return accountsid;
    }

    public void setAccountsid(String accountsid) {
        this.accountsid = accountsid == null ? null : accountsid.trim();
    }

    public String getSipnumber() {
        return sipnumber;
    }

    public void setSipnumber(String sipnumber) {
        this.sipnumber = sipnumber == null ? null : sipnumber.trim();
    }

    public Date getCts() {
        return cts;
    }

    public void setCts(Date cts) {
        this.cts = cts;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
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