package com.lhhs.loan.common.shared.enums;

public enum AuditingHandledType {

	/** 项目审批处理结果 **/
	TY("ty",1,"同意","1"),
	CXTJ("cxtj",11,"重走审批","11"),
	BJ("bj",4,"补件","4"),
	HQ("hq",5,"会签","5"),
	XG("xg",-2,"驳回修改","-2"),
	JS("js",90,"结束任务","90"),
	CD("cd",91,"撤单","91"),
	JD("jd",92,"拒贷","92"),
	FH("fh",-3,"返回驳回节点","-3"),
	BJWC("bjwc",14,"补件完成","14");
	private String name;
	private Integer index;
	private String desc;
	//转换成工作流使用
	private String pass;
	private AuditingHandledType(String name, Integer index, String desc) {
		this.name = name;
		this.index = index;
		this.desc = desc;
	}
	private AuditingHandledType(String name, Integer index, String desc,String pass) {
		this.name = name;
		this.index = index;
		this.desc = desc;
		this.pass = pass;
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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
}
