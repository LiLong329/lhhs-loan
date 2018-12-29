package com.lhhs.loan.dao.domain;

import java.util.Date;
import java.util.List;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanDept extends BaseObject{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5961119010975189612L;

	private Integer ldDeptId;

    private String ldName;

    private String ldDepict;

    private String ldRemark;

    private Date ldTime;

    private String ldStatus;

    private Integer layer;

    private Integer sort;

    private Integer parentId;

    private String parentIds;

    private String ldCompany;

    private String ldUnion;

    private String field1;

    private String field2;
    
    //扩展字段
    private String companyName;
    
    private List<LoanDept> subDeptList;

    public Integer getLdDeptId() {
        return ldDeptId;
    }

    public void setLdDeptId(Integer ldDeptId) {
        this.ldDeptId = ldDeptId;
    }

    public String getLdName() {
        return ldName;
    }

    public void setLdName(String ldName) {
        this.ldName = ldName == null ? null : ldName.trim();
    }

    public String getLdDepict() {
        return ldDepict;
    }

    public void setLdDepict(String ldDepict) {
        this.ldDepict = ldDepict == null ? null : ldDepict.trim();
    }

    public String getLdRemark() {
        return ldRemark;
    }

    public void setLdRemark(String ldRemark) {
        this.ldRemark = ldRemark == null ? null : ldRemark.trim();
    }

    public Date getLdTime() {
        return ldTime;
    }

    public void setLdTime(Date ldTime) {
        this.ldTime = ldTime;
    }

    public String getLdStatus() {
        return ldStatus;
    }

    public void setLdStatus(String ldStatus) {
        this.ldStatus = ldStatus == null ? null : ldStatus.trim();
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }

    public String getLdCompany() {
        return ldCompany;
    }

    public void setLdCompany(String ldCompany) {
        this.ldCompany = ldCompany == null ? null : ldCompany.trim();
    }

    public String getLdUnion() {
        return ldUnion;
    }

    public void setLdUnion(String ldUnion) {
        this.ldUnion = ldUnion == null ? null : ldUnion.trim();
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

	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
    
    

	public List<LoanDept> getSubDeptList() {
		return subDeptList;
	}

	public void setSubDeptList(List<LoanDept> subDeptList) {
		this.subDeptList = subDeptList;
	}
}