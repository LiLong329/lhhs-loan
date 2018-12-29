package com.lhhs.loan.dao.domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class LoanEmp implements  UserDetails{
  
	private static final long serialVersionUID = -169874947164858546L;

	private Integer leEmpId;

    private String leAccount;

    private String lePassword;

    private String leStaffName;

    private String leMobile;

    private String leSex;

    private Date leTime;

    private String leStatus;

    private Integer leDeptId;

    private Integer leQuartersId;

    private Integer leGroupId;

    private String leRemark;
    
    private String leCity;
    
    private String ldName;
    
    private String lqName;
    
    private Integer leGrade;
    
    private String companyId;//所属集团
    
    private String branchCompanyId;// 所属分公司
    
    private List<Integer> lrRoleId;//角色列表
    
    private List<Integer> lqQuartersId;//岗位列表
    
    private List<String> deptGroupList;//管理部门或组别列表
    
    private List<String> companyIdList;//分公司列表
    
    private String businessCompanyId;// 业务分公司编号---面审会签使用
    
    private List<? extends GrantedAuthority> authorities;
    
    private String provinceName;// 所属分公司
    
    //扩展字段
    private String  lrStatus;//角色状态
    
    private String  lrName;//角色名称
    
    private String  lgStatus;//组状态
    
    private String  lqStatus;//岗位状态
    
    private String  ldStatus; //部门状态
    
    private List<String> quartersNames;//岗位名称列表
    
    private List<LoanAuthority> operateAuthList;//操作权限列表
    
    private List<LoanAuthgroupUser> authgroupList;//数据权限组列表
    
    private List<LoanAuthgroupUser> menuAuthgroupList;//菜单数据权限组列表
    
    private List<Integer> authgroupIds;//数据权限组id列表
	
	public Integer getLeGrade() {
		return leGrade;
	}
	
	public void setLeGrade(Integer leGrade) {
		this.leGrade = leGrade;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getBranchCompanyId() {
		return branchCompanyId;
	}

	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}

    public String getBusinessCompanyId() {
		return businessCompanyId;
	}

	public void setBusinessCompanyId(String businessCompanyId) {
		this.businessCompanyId = businessCompanyId;
	}

	public String getLdName() {
		return ldName;
	}

	public void setLdName(String ldName) {
		this.ldName = ldName;
	}

	public String getLqName() {
		return lqName;
	}

	public void setLqName(String lqName) {
		this.lqName = lqName;
	}

	public String getLeCity() {
		return leCity;
	}

	public void setLeCity(String leCity) {
		this.leCity = leCity;
	}

	public Integer getLeEmpId() {
        return leEmpId;
    }

    public void setLeEmpId(Integer leEmpId) {
        this.leEmpId = leEmpId;
    }

    public String getLeAccount() {
        return leAccount;
    }

    public void setLeAccount(String leAccount) {
        this.leAccount = leAccount == null ? null : leAccount.trim();
    }

    public String getLePassword() {
        return lePassword;
    }

    public void setLePassword(String lePassword) {
        this.lePassword = lePassword == null ? null : lePassword.trim();
    }

    public String getLeStaffName() {
        return leStaffName;
    }

    public void setLeStaffName(String leStaffName) {
        this.leStaffName = leStaffName == null ? null : leStaffName.trim();
    }

    public String getLeMobile() {
        return leMobile;
    }

    public void setLeMobile(String leMobile) {
        this.leMobile = leMobile == null ? null : leMobile.trim();
    }

    public String getLeSex() {
        return leSex;
    }

    public void setLeSex(String leSex) {
        this.leSex = leSex == null ? null : leSex.trim();
    }

    public Date getLeTime() {
        return leTime;
    }

    public void setLeTime(Date leTime) {
        this.leTime = leTime;
    }

    public String getLeStatus() {
        return leStatus;
    }

    public void setLeStatus(String leStatus) {
        this.leStatus = leStatus == null ? null : leStatus.trim();
    }

    public Integer getLeDeptId() {
        return leDeptId;
    }

    public void setLeDeptId(Integer leDeptId) {
        this.leDeptId = leDeptId;
    }

    public Integer getLeQuartersId() {
        return leQuartersId;
    }

    public void setLeQuartersId(Integer leQuartersId) {
        this.leQuartersId = leQuartersId;
    }

    public Integer getLeGroupId() {
        return leGroupId;
    }

    public void setLeGroupId(Integer leGroupId) {
        this.leGroupId = leGroupId;
    }

    public String getLeRemark() {
        return leRemark;
    }

    public void setLeRemark(String leRemark) {
        this.leRemark = leRemark == null ? null : leRemark.trim();
    }

	public void setAuthorities(List<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public String getLdStatus() {
		return ldStatus;
	}

	public void setLdStatus(String ldStatus) {
		this.ldStatus = ldStatus;
	}

	public String getLrStatus() {
		return lrStatus;
	}

	public void setLrStatus(String lrStatus) {
		this.lrStatus = lrStatus;
	}

	public String getLrName() {
		return lrName;
	}

	public void setLrName(String lrName) {
		this.lrName = lrName;
	}

	public String getLgStatus() {
		return lgStatus;
	}

	public void setLgStatus(String lgStatus) {
		this.lgStatus = lgStatus;
	}

	public String getLqStatus() {
		return lqStatus;
	}

	public void setLqStatus(String lqStatus) {
		this.lqStatus = lqStatus;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return lePassword;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return leAccount;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public List<Integer> getLrRoleId() {
		return lrRoleId;
	}

	public void setLrRoleId(List<Integer> lrRoleId) {
		this.lrRoleId = lrRoleId;
	}

	public List<String> getDeptGroupList() {
		return deptGroupList;
	}
	
	public void setDeptGroupList(List<String> deptGroupList) {
		this.deptGroupList = deptGroupList;
	}

	public List<String> getCompanyIdList() {
		return companyIdList;
	}

	public void setCompanyIdList(List<String> companyIdList) {
		this.companyIdList = companyIdList;
	}

	public List<Integer> getLqQuartersId() {
		return lqQuartersId;
	}

	
	public void setLqQuartersId(List<Integer> lqQuartersId) {
		this.lqQuartersId = lqQuartersId;
	}
	
	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	
	public List<String> getQuartersNames() {
		return quartersNames;
	}

	public void setQuartersNames(List<String> quartersNames) {
		this.quartersNames = quartersNames;
	}

	public List<LoanAuthgroupUser> getAuthgroupList() {
		return authgroupList;
	}

	public void setAuthgroupList(List<LoanAuthgroupUser> authgroupList) {
		this.authgroupList = authgroupList;
	}

	public List<Integer> getAuthgroupIds() {
		return authgroupIds;
	}

	public void setAuthgroupIds(List<Integer> authgroupIds) {
		this.authgroupIds = authgroupIds;
	}

	public List<LoanAuthority> getOperateAuthList() {
		return operateAuthList;
	}

	public void setOperateAuthList(List<LoanAuthority> operateAuthList) {
		this.operateAuthList = operateAuthList;
	}

	public List<LoanAuthgroupUser> getMenuAuthgroupList() {
		return menuAuthgroupList;
	}

	public void setMenuAuthgroupList(List<LoanAuthgroupUser> menuAuthgroupList) {
		this.menuAuthgroupList = menuAuthgroupList;
	}
	
}