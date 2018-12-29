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
public enum CredentialsType {
	/**
	 * 资质模板中的资质类型枚举类
	 */
	
	BASE("base","0","基本资质"),
	HOUSE("house","1","房产资质"),
	CAR("car","2","车产资质"),
	CREDIT("credit","3","信用资质"),
	AGREEMENT("agreement","4","合同资质"),
	OTHER("other","5","其他资质");
	
	private String name;
	private String index;
	private String desc;
	
	private CredentialsType(String name, String index, String desc) {
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
