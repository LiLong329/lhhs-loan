package com.lhhs.loan.dao.domain;

public class CrmIntentLoanUserRecord extends CrmIntentLoanUser {

	private static final long serialVersionUID = 748167083588036624L;
	// 辅助字段
	private String managerName;// 客户经理姓名
	private String managerMobile;// 客户经理手机号
	private String managerCompanyName;// 客户经理所属公司名字
	private String managerDepName;// 客户经理所属部门=上级部门
	private String managerGroupName;// 客户经理所属组别=当前部门
	private String inViteCount;// 回访量
	private String customerCount;//客户量
	private int rowSpan = 1;//列宽

	public int getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerMobile() {
		return managerMobile;
	}

	public void setManagerMobile(String managerMobile) {
		this.managerMobile = managerMobile;
	}

	public String getManagerCompanyName() {
		return managerCompanyName;
	}

	public void setManagerCompanyName(String managerCompanyName) {
		this.managerCompanyName = managerCompanyName;
	}

	public String getManagerDepName() {
		return managerDepName;
	}

	public void setManagerDepName(String managerDepName) {
		this.managerDepName = managerDepName;
	}

	public String getManagerGroupName() {
		return managerGroupName;
	}

	public void setManagerGroupName(String managerGroupName) {
		this.managerGroupName = managerGroupName;
	}

	public String getInViteCount() {
		return inViteCount;
	}

	public void setInViteCount(String inVIteCount) {
		this.inViteCount = inVIteCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public void setCustomerCount(String customerCount) {
		this.customerCount = customerCount;
	}
	
	public String getCustomerCount() {
		return customerCount;
	}
}