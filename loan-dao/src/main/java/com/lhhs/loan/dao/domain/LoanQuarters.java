package com.lhhs.loan.dao.domain;

import java.util.Date;

public class LoanQuarters {
    private Integer lqQuartersId;

    private String lqName;
    
    private String lqEnglishName;
    
    private String lqUnionId;

    private String lqDepict;

    private Integer lqParentId;

    private Integer lqDeptId;
    
    private Integer lqGrade;

    private Date lqTime;

    private String lqStatus;

    private String lqRemark;
    
    private String lqParentName;
   
    private String lqDeptName;
    
    private String  lqLimit;

    public Integer getLqQuartersId() {
        return lqQuartersId;
    }

    public void setLqQuartersId(Integer lqQuartersId) {
        this.lqQuartersId = lqQuartersId;
    }

    public String getLqName() {
        return lqName;
    }

    public void setLqName(String lqName) {
        this.lqName = lqName == null ? null : lqName.trim();
    }
    
	public String getLqEnglishName() {
		return lqEnglishName;
	}

	public void setLqEnglishName(String lqEnglishName) {
		this.lqEnglishName = lqEnglishName;
	}
	
	
	public String getLqUnionId() {
		return lqUnionId;
	}

	
	public void setLqUnionId(String lqUnionId) {
		this.lqUnionId = lqUnionId;
	}

	public String getLqDepict() {
        return lqDepict;
    }

    public void setLqDepict(String lqDepict) {
        this.lqDepict = lqDepict == null ? null : lqDepict.trim();
    }

    public Integer getLqParentId() {
        return lqParentId;
    }

    public void setLqParentId(Integer lqParentId) {
        this.lqParentId = lqParentId;
    }

    public Integer getLqDeptId() {
        return lqDeptId;
    }

    public void setLqDeptId(Integer lqDeptId) {
        this.lqDeptId = lqDeptId;
    }
	
	public Integer getLqGrade() {
		return lqGrade;
	}

	public void setLqGrade(Integer lqGrade) {
		this.lqGrade = lqGrade;
	}

	public Date getLqTime() {
        return lqTime;
    }

    public void setLqTime(Date lqTime) {
        this.lqTime = lqTime;
    }

    public String getLqStatus() {
        return lqStatus;
    }

    public void setLqStatus(String lqStatus) {
        this.lqStatus = lqStatus == null ? null : lqStatus.trim();
    }

    public String getLqRemark() {
        return lqRemark;
    }

    public void setLqRemark(String lqRemark) {
        this.lqRemark = lqRemark == null ? null : lqRemark.trim();
    }

	public String getLqParentName() {
		return lqParentName;
	}

	public void setLqParentName(String lqParentName) {
		this.lqParentName = lqParentName;
	}

	public String getLqDeptName() {
		return lqDeptName;
	}

	public void setLqDeptName(String lqDeptName) {
		this.lqDeptName = lqDeptName;
	}

	public String getLqLimit() {
		return lqLimit;
	}

	public void setLqLimit(String lqLimit) {
		this.lqLimit = lqLimit;
	}
    
    
}