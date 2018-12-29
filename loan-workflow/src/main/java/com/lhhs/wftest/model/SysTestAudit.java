/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.lhhs.wftest.model;


/**
 * 审批Entity
 * 
 * @author thinkgem
 * @version 2014-05-16
 */
public class SysTestAudit extends TestAudit {

	private static final long serialVersionUID = 1L;
	private String officeName;//部门名称
	private String loginName;//登陆人员名称
	private String name;//登陆人员任职名称
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
