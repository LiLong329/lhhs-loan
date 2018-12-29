package com.lhhs.loan.dao.domain;

import java.util.Date;

import com.lhhs.loan.common.shared.page.BaseObject;

public class LoanBorrowerInfo extends BaseObject {
    /**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.8
	 */
	private static final long serialVersionUID = 3050605998803161748L;

	private String custId;

    private String bname;

    private String sex;

    private String mobile;

    private String idNum;

    private String education;

    private String corName;

    private String marStatus;

    private String relationOne;

    private String relationNameOne;

    private String relationModeOne;

    private String relationTwo;

    private String relationNameTwo;

    private String relationModeTwo;

    private String accountNature;

    private String health;

    private String driving;

    private String unitProperty;

    private String workYears;

    private String technicalTitle;

    private String monthlyIncome;

    private String mailbox;

    private String spouseName;

    private String spouseContact;

    private String averageIncome;

    private String averagePay;

    private String debtIncomeRatio;

    private String supportPersonNo;

    private String housingSituation;

    private String depositsInvestments;

    private String vehicleCondition;

    private String customerId;

    private String liveAddress;

    private String addProvinceNo;

    private String addCityNo;

    private String corAddress;

    private String corProvinceNo;

    private String corCityNo;

    private String spouseAddress;

    private String provinceNo;

    private String cityNo;

    private String unionId;

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

    private Integer custType;

    private Integer custNature;
    
    private String leStaffName;//客户经理名称

    
    public String getLeStaffName() {
		return leStaffName;
	}

	public void setLeStaffName(String leStaffName) {
		this.leStaffName = leStaffName;
	}

	public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname == null ? null : bname.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum == null ? null : idNum.trim();
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getCorName() {
        return corName;
    }

    public void setCorName(String corName) {
        this.corName = corName == null ? null : corName.trim();
    }

    public String getMarStatus() {
        return marStatus;
    }

    public void setMarStatus(String marStatus) {
        this.marStatus = marStatus == null ? null : marStatus.trim();
    }

    public String getRelationOne() {
        return relationOne;
    }

    public void setRelationOne(String relationOne) {
        this.relationOne = relationOne == null ? null : relationOne.trim();
    }

    public String getRelationNameOne() {
        return relationNameOne;
    }

    public void setRelationNameOne(String relationNameOne) {
        this.relationNameOne = relationNameOne == null ? null : relationNameOne.trim();
    }

    public String getRelationModeOne() {
        return relationModeOne;
    }

    public void setRelationModeOne(String relationModeOne) {
        this.relationModeOne = relationModeOne == null ? null : relationModeOne.trim();
    }

    public String getRelationTwo() {
        return relationTwo;
    }

    public void setRelationTwo(String relationTwo) {
        this.relationTwo = relationTwo == null ? null : relationTwo.trim();
    }

    public String getRelationNameTwo() {
        return relationNameTwo;
    }

    public void setRelationNameTwo(String relationNameTwo) {
        this.relationNameTwo = relationNameTwo == null ? null : relationNameTwo.trim();
    }

    public String getRelationModeTwo() {
        return relationModeTwo;
    }

    public void setRelationModeTwo(String relationModeTwo) {
        this.relationModeTwo = relationModeTwo == null ? null : relationModeTwo.trim();
    }

    public String getAccountNature() {
        return accountNature;
    }

    public void setAccountNature(String accountNature) {
        this.accountNature = accountNature == null ? null : accountNature.trim();
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health == null ? null : health.trim();
    }

    public String getDriving() {
        return driving;
    }

    public void setDriving(String driving) {
        this.driving = driving == null ? null : driving.trim();
    }

    public String getUnitProperty() {
        return unitProperty;
    }

    public void setUnitProperty(String unitProperty) {
        this.unitProperty = unitProperty == null ? null : unitProperty.trim();
    }

    public String getWorkYears() {
        return workYears;
    }

    public void setWorkYears(String workYears) {
        this.workYears = workYears == null ? null : workYears.trim();
    }

    public String getTechnicalTitle() {
        return technicalTitle;
    }

    public void setTechnicalTitle(String technicalTitle) {
        this.technicalTitle = technicalTitle == null ? null : technicalTitle.trim();
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome == null ? null : monthlyIncome.trim();
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox == null ? null : mailbox.trim();
    }

    public String getSpouseName() {
        return spouseName;
    }

    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName == null ? null : spouseName.trim();
    }

    public String getSpouseContact() {
        return spouseContact;
    }

    public void setSpouseContact(String spouseContact) {
        this.spouseContact = spouseContact == null ? null : spouseContact.trim();
    }

    public String getAverageIncome() {
        return averageIncome;
    }

    public void setAverageIncome(String averageIncome) {
        this.averageIncome = averageIncome == null ? null : averageIncome.trim();
    }

    public String getAveragePay() {
        return averagePay;
    }

    public void setAveragePay(String averagePay) {
        this.averagePay = averagePay == null ? null : averagePay.trim();
    }

    public String getDebtIncomeRatio() {
        return debtIncomeRatio;
    }

    public void setDebtIncomeRatio(String debtIncomeRatio) {
        this.debtIncomeRatio = debtIncomeRatio == null ? null : debtIncomeRatio.trim();
    }

    public String getSupportPersonNo() {
        return supportPersonNo;
    }

    public void setSupportPersonNo(String supportPersonNo) {
        this.supportPersonNo = supportPersonNo == null ? null : supportPersonNo.trim();
    }

    public String getHousingSituation() {
        return housingSituation;
    }

    public void setHousingSituation(String housingSituation) {
        this.housingSituation = housingSituation == null ? null : housingSituation.trim();
    }

    public String getDepositsInvestments() {
        return depositsInvestments;
    }

    public void setDepositsInvestments(String depositsInvestments) {
        this.depositsInvestments = depositsInvestments == null ? null : depositsInvestments.trim();
    }

    public String getVehicleCondition() {
        return vehicleCondition;
    }

    public void setVehicleCondition(String vehicleCondition) {
        this.vehicleCondition = vehicleCondition == null ? null : vehicleCondition.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getLiveAddress() {
        return liveAddress;
    }

    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress == null ? null : liveAddress.trim();
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

    public String getCorAddress() {
        return corAddress;
    }

    public void setCorAddress(String corAddress) {
        this.corAddress = corAddress == null ? null : corAddress.trim();
    }

    public String getCorProvinceNo() {
        return corProvinceNo;
    }

    public void setCorProvinceNo(String corProvinceNo) {
        this.corProvinceNo = corProvinceNo == null ? null : corProvinceNo.trim();
    }

    public String getCorCityNo() {
        return corCityNo;
    }

    public void setCorCityNo(String corCityNo) {
        this.corCityNo = corCityNo == null ? null : corCityNo.trim();
    }

    public String getSpouseAddress() {
        return spouseAddress;
    }

    public void setSpouseAddress(String spouseAddress) {
        this.spouseAddress = spouseAddress == null ? null : spouseAddress.trim();
    }

    public String getProvinceNo() {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo) {
        this.provinceNo = provinceNo == null ? null : provinceNo.trim();
    }

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo == null ? null : cityNo.trim();
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
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

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public Integer getCustNature() {
        return custNature;
    }

    public void setCustNature(Integer custNature) {
        this.custNature = custNature;
    }
}