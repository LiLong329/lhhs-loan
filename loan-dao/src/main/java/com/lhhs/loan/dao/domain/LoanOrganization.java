package com.lhhs.loan.dao.domain;

import java.util.Date;
import java.util.List;

import com.lhhs.loan.common.shared.page.BaseObject;

@SuppressWarnings("all")
public class LoanOrganization extends BaseObject{
    private Long orgId;

    private String orgUsername;

    private String orgPassword;

    private String orgName;

    private String orgWholeName;

    private String orgCode;

    private Date orgAddTime;

    private Byte orgCategories;

    private Byte orgBusinessType;

    private Byte orgType;

    private Byte orgOutfitFrom;

    private String orgLegalRepresentative;

    private String orgProfile;

    private String orgWebsite;

    private String orgLogo;

    private String orgLeaderName;

    private String orgLeaderMobile;

    private String orgLeaderEmail;

    private String orgAccountName;

    private String orgAccountBank;

    private String orgAccountBanchBank;

    private String orgAccountNo;

    private String orgBusinessLicenseNo;

    private String orgState;

    private String registeredAddress;

    private String regProvinceNo;

    private String regCityNo;

    private String orgAddress;

    private String addProvinceNo;

    private String addCityNo;

    private String provinceNo;

    private String provinceName;

    private String cityNo;

    private String cityName;

    private String companyNature;

    private Date foundTime;

    private String companyMobile;

    private Integer employeeNum;

    private String orgLegalSex;

    private String orgLegalMobile;

    private String customerName;

    private String customerSex;

    private String customerMobile;

    private String idNum;

    private String customerId;

    private String category;

    private String unionId;
    
    private String isCompany;

    private String companyId;

    private String status;

    private String createUser;

    private Date createTime;

    private String lastUser;

    private Date lastModifyTime;

    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;

    private String businessScope;

    //冗余字段
    
	private List<LoanOrgSupportAreas> supportCityList;
	private String cityList;
    
    public List<LoanOrgSupportAreas> getSupportCityList() {
		return supportCityList;
	}

	public void setSupportCityList(List<LoanOrgSupportAreas> supportCityList) {
		this.supportCityList = supportCityList;
	}

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgUsername() {
        return orgUsername;
    }

    public void setOrgUsername(String orgUsername) {
        this.orgUsername = orgUsername == null ? null : orgUsername.trim();
    }

    public String getOrgPassword() {
        return orgPassword;
    }

    public void setOrgPassword(String orgPassword) {
        this.orgPassword = orgPassword == null ? null : orgPassword.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgWholeName() {
        return orgWholeName;
    }

    public void setOrgWholeName(String orgWholeName) {
        this.orgWholeName = orgWholeName == null ? null : orgWholeName.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public Date getOrgAddTime() {
        return orgAddTime;
    }

    public void setOrgAddTime(Date orgAddTime) {
        this.orgAddTime = orgAddTime;
    }

    public Byte getOrgCategories() {
        return orgCategories;
    }

    public void setOrgCategories(Byte orgCategories) {
        this.orgCategories = orgCategories;
    }

    public Byte getOrgBusinessType() {
        return orgBusinessType;
    }

    public void setOrgBusinessType(Byte orgBusinessType) {
        this.orgBusinessType = orgBusinessType;
    }

    public Byte getOrgType() {
        return orgType;
    }

    public void setOrgType(Byte orgType) {
        this.orgType = orgType;
    }

    public Byte getOrgOutfitFrom() {
        return orgOutfitFrom;
    }

    public void setOrgOutfitFrom(Byte orgOutfitFrom) {
        this.orgOutfitFrom = orgOutfitFrom;
    }

    public String getOrgLegalRepresentative() {
        return orgLegalRepresentative;
    }

    public void setOrgLegalRepresentative(String orgLegalRepresentative) {
        this.orgLegalRepresentative = orgLegalRepresentative == null ? null : orgLegalRepresentative.trim();
    }

    public String getOrgProfile() {
        return orgProfile;
    }

    public void setOrgProfile(String orgProfile) {
        this.orgProfile = orgProfile == null ? null : orgProfile.trim();
    }

    public String getOrgWebsite() {
        return orgWebsite;
    }

    public void setOrgWebsite(String orgWebsite) {
        this.orgWebsite = orgWebsite == null ? null : orgWebsite.trim();
    }

    public String getOrgLogo() {
        return orgLogo;
    }

    public void setOrgLogo(String orgLogo) {
        this.orgLogo = orgLogo == null ? null : orgLogo.trim();
    }

    public String getOrgLeaderName() {
        return orgLeaderName;
    }

    public void setOrgLeaderName(String orgLeaderName) {
        this.orgLeaderName = orgLeaderName == null ? null : orgLeaderName.trim();
    }

    public String getOrgLeaderMobile() {
        return orgLeaderMobile;
    }

    public void setOrgLeaderMobile(String orgLeaderMobile) {
        this.orgLeaderMobile = orgLeaderMobile == null ? null : orgLeaderMobile.trim();
    }

    public String getOrgLeaderEmail() {
        return orgLeaderEmail;
    }

    public void setOrgLeaderEmail(String orgLeaderEmail) {
        this.orgLeaderEmail = orgLeaderEmail == null ? null : orgLeaderEmail.trim();
    }

    public String getOrgAccountName() {
        return orgAccountName;
    }

    public void setOrgAccountName(String orgAccountName) {
        this.orgAccountName = orgAccountName == null ? null : orgAccountName.trim();
    }

    public String getOrgAccountBank() {
        return orgAccountBank;
    }

    public void setOrgAccountBank(String orgAccountBank) {
        this.orgAccountBank = orgAccountBank == null ? null : orgAccountBank.trim();
    }

    public String getOrgAccountBanchBank() {
        return orgAccountBanchBank;
    }

    public void setOrgAccountBanchBank(String orgAccountBanchBank) {
        this.orgAccountBanchBank = orgAccountBanchBank == null ? null : orgAccountBanchBank.trim();
    }

    public String getOrgAccountNo() {
        return orgAccountNo;
    }

    public void setOrgAccountNo(String orgAccountNo) {
        this.orgAccountNo = orgAccountNo == null ? null : orgAccountNo.trim();
    }

    public String getOrgBusinessLicenseNo() {
        return orgBusinessLicenseNo;
    }

    public void setOrgBusinessLicenseNo(String orgBusinessLicenseNo) {
        this.orgBusinessLicenseNo = orgBusinessLicenseNo == null ? null : orgBusinessLicenseNo.trim();
    }

    public String getOrgState() {
        return orgState;
    }

    public void setOrgState(String orgState) {
        this.orgState = orgState == null ? null : orgState.trim();
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress == null ? null : registeredAddress.trim();
    }

    public String getRegProvinceNo() {
        return regProvinceNo;
    }

    public void setRegProvinceNo(String regProvinceNo) {
        this.regProvinceNo = regProvinceNo == null ? null : regProvinceNo.trim();
    }

    public String getRegCityNo() {
        return regCityNo;
    }

    public void setRegCityNo(String regCityNo) {
        this.regCityNo = regCityNo == null ? null : regCityNo.trim();
    }

    public String getOrgAddress() {
        return orgAddress;
    }

    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress == null ? null : orgAddress.trim();
    }

    public String getAddProvinceNo() {
        return addProvinceNo;
    }

    public void setAddProvinceNo(String addProvinceNo) {
        this.addProvinceNo = addProvinceNo == null ? null : addProvinceNo.trim();
    }

    public String getAddCityNo() {
        return addCityNo;
    }

    public void setAddCityNo(String addCityNo) {
        this.addCityNo = addCityNo == null ? null : addCityNo.trim();
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

    public String getCompanyNature() {
        return companyNature;
    }

    public void setCompanyNature(String companyNature) {
        this.companyNature = companyNature == null ? null : companyNature.trim();
    }

    public Date getFoundTime() {
        return foundTime;
    }

    public void setFoundTime(Date foundTime) {
        this.foundTime = foundTime;
    }

    public String getCompanyMobile() {
        return companyMobile;
    }

    public void setCompanyMobile(String companyMobile) {
        this.companyMobile = companyMobile == null ? null : companyMobile.trim();
    }

    public Integer getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(Integer employeeNum) {
        this.employeeNum = employeeNum;
    }

    public String getOrgLegalSex() {
        return orgLegalSex;
    }

    public void setOrgLegalSex(String orgLegalSex) {
        this.orgLegalSex = orgLegalSex == null ? null : orgLegalSex.trim();
    }

    public String getOrgLegalMobile() {
        return orgLegalMobile;
    }

    public void setOrgLegalMobile(String orgLegalMobile) {
        this.orgLegalMobile = orgLegalMobile == null ? null : orgLegalMobile.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerSex() {
        return customerSex;
    }

    public void setCustomerSex(String customerSex) {
        this.customerSex = customerSex == null ? null : customerSex.trim();
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile == null ? null : customerMobile.trim();
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum == null ? null : idNum.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    public String getIsCompany() {
		return isCompany;
	}

	public void setIsCompany(String isCompany) {
		this.isCompany = isCompany == null ? null : isCompany.trim();
	}

	public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser == null ? null : lastUser.trim();
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
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

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope == null ? null : businessScope.trim();
    }

	public String getCityList() {
		return cityList;
	}

	public void setCityList(String cityList) {
		this.cityList = cityList;
	}
}