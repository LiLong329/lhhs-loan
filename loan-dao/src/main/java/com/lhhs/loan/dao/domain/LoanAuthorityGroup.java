package com.lhhs.loan.dao.domain;

import java.io.Serializable;
import java.util.Date;

public class LoanAuthorityGroup extends LoanAuthgroup implements Serializable ,Cloneable{
    
	private static final long serialVersionUID = 5302430427876216463L;

	private Integer authorityGroupId;

    private String authorityId;

    private Integer groupId;

    private String orgId;

    private String deptId;

    private String dataOwner;

    private String status;

    private Date creatTime;
   
    public Integer getAuthorityGroupId() {
		return authorityGroupId;
	}

	public void setAuthorityGroupId(Integer authorityGroupId) {
		this.authorityGroupId = authorityGroupId;
	}

	public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId == null ? null : authorityId.trim();
    }

   
    public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getDataOwner() {
        return dataOwner;
    }

    public void setDataOwner(String dataOwner) {
        this.dataOwner = dataOwner == null ? null : dataOwner.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}