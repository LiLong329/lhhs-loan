package com.lhhs.loan.dao.domain;

import java.io.Serializable;
import java.util.Date;

public class LoanAuthgroup implements Serializable ,Cloneable{
    
	private static final long serialVersionUID = -1302895942421216413L;

	private Integer groupId;

    private String groupName;

    private String status;

    private String type;

    private String dataZone;
    
    private String unionId;
    
    private String companyId;
    
    private String isSelect;//是否选中
   
    private Date creatTime;
    
    private String unionName;
    private String companyName;
    
    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDataZone() {
        return dataZone;
    }

    public void setDataZone(String dataZone) {
        this.dataZone = dataZone == null ? null : dataZone.trim();
    }

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public String getUnionName() {
		return unionName;
	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
}