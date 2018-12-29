package com.lhhs.loan.dao.domain;

import java.util.Date;
import java.util.List;

import com.lhhs.loan.common.shared.page.BaseObject;

@SuppressWarnings("all")
public class LoanUnionCompany extends BaseObject {
	private String companyId;

	private String companyName;
	
	private String englishName;

	private String legalRepresentative;

	private String provinceNo;

	private String provinceName;

	private String cityNo;

	private String cityName;

	private String companyAddress;

	private String leaderName;

	private String leaderMobile;

	private String leaderEmail;

	private String unionId;

	private String parentCompanyId;

	private String level;

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
	
	private List<LoanDept> loanDeptList;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId == null ? null : companyId.trim();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName == null ? null : companyName.trim();
	}

	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative == null ? null : legalRepresentative.trim();
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

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress == null ? null : companyAddress.trim();
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName == null ? null : leaderName.trim();
	}

	public String getLeaderMobile() {
		return leaderMobile;
	}

	public void setLeaderMobile(String leaderMobile) {
		this.leaderMobile = leaderMobile == null ? null : leaderMobile.trim();
	}

	public String getLeaderEmail() {
		return leaderEmail;
	}

	public void setLeaderEmail(String leaderEmail) {
		this.leaderEmail = leaderEmail == null ? null : leaderEmail.trim();
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId == null ? null : unionId.trim();
	}

	public String getParentCompanyId() {
		return parentCompanyId;
	}

	public void setParentCompanyId(String parentCompanyId) {
		this.parentCompanyId = parentCompanyId == null ? null : parentCompanyId.trim();
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level == null ? null : level.trim();
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

	public List<LoanDept> getLoanDeptList() {
		return loanDeptList;
	}

	public void setLoanDeptList(List<LoanDept> loanDeptList) {
		this.loanDeptList = loanDeptList;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	
}