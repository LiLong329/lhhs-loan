package com.lhhs.loan.dao.domain;

import java.util.Date;

public class LoanOrgSupportAreas {
    
	private Long id;

    private Long orgId;

    private String areaNo;

    private String areaName;

    private Integer managerId;

    private String orgName;

    private String orgCode;

    private Byte orgCategories;

    private Byte orgType;

    private Date createDate;
    
    private String province;

    private String provinceNo;

    private String provinceName;

    private String city;
    
    private String cityNo;

    private String cityName;
    
    private String leStaffName;// 冗余字段,客户经理

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo == null ? null : areaNo.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Byte getOrgCategories() {
        return orgCategories;
    }

    public void setOrgCategories(Byte orgCategories) {
        this.orgCategories = orgCategories;
    }

    public Byte getOrgType() {
        return orgType;
    }

    public void setOrgType(Byte orgType) {
        this.orgType = orgType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }
    
    public String getProvinceNo() {
    	return provinceNo;
    }
    
    public void setProvinceNo(String provinceNo) {
    	this.provinceNo = provinceNo == null ? null : provinceNo.trim();
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName == null ? null : provinceName.trim();
    }

    public String getCity() {
    	return city;
    }
    
    public void setCity(String city) {
    	this.city = city == null ? null : city.trim();
    }
    
    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo == null ? null : cityNo.trim();
    }
    
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

	public String getLeStaffName() {
		return leStaffName;
	}

	public void setLeStaffName(String leStaffName) {
		this.leStaffName = leStaffName;
	}
    
}