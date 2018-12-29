package com.lhhs.loan.common.shared.enums;

public enum AuditingHandledResultType {

	BJWC("bjwc",4,"补件完成");
	
	private String name;
	private Integer index;
	private String desc;
	
	private AuditingHandledResultType(String name, Integer index, String desc) {
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
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
