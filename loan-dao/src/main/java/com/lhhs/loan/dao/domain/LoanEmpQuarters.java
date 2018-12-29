package com.lhhs.loan.dao.domain;

import com.lhhs.loan.common.shared.page.BaseObject;

@SuppressWarnings("all")
public class LoanEmpQuarters  extends BaseObject{
    private Integer leqEqId;

    private Integer leqEmpId;

    private Integer leqQuartersId;
    
    private String  leqEnglishName;
    
    private String  leStaffName;//员工名称
    
    private String  lqName;//岗位名称
    
    private String  companyName;//公司名称
    
    private String  deptId;//部门id
    
    private String  ldName;//部门名称
    
    private String  branchCompanyId;//公司id
    
    public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}

	public String getLdName() {
		return ldName;
	}

	public void setLdName(String ldName) {
		this.ldName = ldName;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLeStaffName() {
		return leStaffName;
	}

	public void setLeStaffName(String leStaffName) {
		this.leStaffName = leStaffName;
	}

	public String getLqName() {
		return lqName;
	}

	public void setLqName(String lqName) {
		this.lqName = lqName;
	}

	public Integer getLeqEqId() {
        return leqEqId;
    }

    public void setLeqEqId(Integer leqEqId) {
        this.leqEqId = leqEqId;
    }

    public Integer getLeqEmpId() {
        return leqEmpId;
    }

    public void setLeqEmpId(Integer leqEmpId) {
        this.leqEmpId = leqEmpId;
    }

    public Integer getLeqQuartersId() {
        return leqQuartersId;
    }

    public void setLeqQuartersId(Integer leqQuartersId) {
        this.leqQuartersId = leqQuartersId;
    }

	public String getLeqEnglishName() {
		return leqEnglishName;
	}

	public void setLeqEnglishName(String leqEnglishName) {
		this.leqEnglishName = leqEnglishName;
	}
    
}