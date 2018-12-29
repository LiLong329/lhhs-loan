package com.lhhs.loan.dao.domain;

import java.io.Serializable;

public class LoanAuthgroupUser extends LoanAuthorityGroup implements Serializable ,Cloneable{
	
	private static final long serialVersionUID = -8745205620452051397L;

	private Integer id;

    private Integer groupId;

    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}