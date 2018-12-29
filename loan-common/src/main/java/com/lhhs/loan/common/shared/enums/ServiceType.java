package com.lhhs.loan.common.shared.enums;

/**
 * 
 * 资质类型枚举类
 * @ClassName: CredentialsType
 * @Description: TODO
 * @author xiangfeng
 * @date 2017年5月6日 下午4:29:18
 *
 */
public enum ServiceType {
	/**
	 * 资质模板中的资质类型枚举类
	 */
	PROVIDERINFO("providerInfo","001","同步经纪人信息"),
	EMPSEND("empSend","002","客户经理推送"),
	LOANAPPLY("loanApply","003","放款记录推送"),
	CREDENTIALSSEND("credentialsSend","004","资质信息修改推送"),
	UPDATEAPPROVAL("updateApproval","005","更新报单审批 "),
	UPDATEBASICINFO("updateBasicInfo","006","更新报单基本信息"),
	UPDATEMORTGAGEINFO("updateMortgageInfo","007","报单--抵押物信息更新"),
	BORROWERINFOSEND("borrowerInfoSend","008","下户财务放款--借款人所有信息推送"),
	GETBORROWERINFO("getBorrowerInfo","009","下户财务放款--拉取借款人信息"),
	CUSTBORROWERINFO("custBorrowerInfo","010","客户管理--推送借款人基本信息"),
	CUSTMORTGAGEINFO("custMortgageInfo","011","客户管理--推送抵押物信息");
	
	private String name;
	private String index;
	private String desc;
	
	private ServiceType(String name, String index, String desc) {
		this.name = name;
		this.index = index;
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
