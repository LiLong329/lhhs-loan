package com.lhhs.workflow.dao.domain;

/*
 * Created on 2006-5-9
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


import java.io.*;

/**
 * PrivilegeBean代表一个最小的权限约束组合，即同一权限号的所有信息
 * <p>
 * Title: PrivilegeBean
 * </p>
 * <p>
 * Description: Shark
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Thunis
 * </p>
 * 
 * @author Gadfly
 * @version 1.0
 */
public class PrivilegeBean implements Serializable {
	static final long serialVersionUID = 2345325243L;

	public PrivilegeBean() {
	}

	/**
	 * 权限号
	 */
	private String privilegeNO;

	/**
	 * 用户编号
	 */
	private String userId;

	/**
	 * 用户姓名
	 */
	private String userName;

	/**
	 * 员工代码
	 */
	private String employeeId;

	/**
	 * 授权人编号
	 */
	private String sentUId;

	/**
	 * 参与者编号
	 */
	private String participantId;

	/**
	 * 限制map 目前包括四种约束： 平台编号 branchcom <-->value 部门编号 department <-->value
	 * 准事业部编码 Productdpt <-->value 产品线编码 Productline <-->value
	 */
	private java.util.HashMap restrictMap;

	/**
	 * 对应限制的组织单元的管理类型 组织单元编码 <-->组织单元的管理类型
	 */
	private java.util.HashMap mngtypeMap;

	/**
	 * 对应限制的组织单元的是否向下兼容 组织单元编码 <-->向下兼容标志
	 */
	private java.util.HashMap downcoverMap;

	/**
	 * 操作功能编码
	 */
	private String[] operateIds;

	/**
	 * 操作功能名称
	 */
	private String[] operateNames;

	/**
	 * 操作功能URL
	 */
	private String[] operateURLs;

	/**
	 * 权限类型
	 */
	private String privilegeType;
	
	/**
	 * 授权编号
	 */
	private String remark1;
	
	

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getParticipantId() {
		return participantId;
	}

	public String getSentUId() {
		return sentUId;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setParticipantId(String participantId) {
		this.participantId = participantId;
	}

	public void setSentUId(String sentUId) {
		this.sentUId = sentUId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private void writeObject(ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
	}

	private void readObject(ObjectInputStream ois)
			throws ClassNotFoundException, IOException {
		ois.defaultReadObject();
	}

	public String getPrivilegeType() {
		return privilegeType;
	}

	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}

	public String[] getOperateIds() {
		return operateIds;
	}

	public void setOperateIds(String[] operateIds) {
		this.operateIds = operateIds;
	}

	public String[] getOperateNames() {
		return operateNames;
	}

	public void setOperateNames(String[] operateNames) {
		this.operateNames = operateNames;
	}

	public String[] getOperateURLs() {
		return operateURLs;
	}

	public void setOperateURLs(String[] operateURLs) {
		this.operateURLs = operateURLs;
	}

	public String getPrivilegeNO() {
		return privilegeNO;
	}

	public void setPrivilegeNO(String privilegeNO) {
		this.privilegeNO = privilegeNO;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public java.util.HashMap getRestrictMap() {
		return restrictMap;
	}

	public void setRestrictMap(java.util.HashMap restrictMap) {
		this.restrictMap = restrictMap;
	}

	public java.util.HashMap getDowncoverMap() {
		return downcoverMap;
	}

	public void setDowncoverMap(java.util.HashMap downcoverMap) {
		this.downcoverMap = downcoverMap;
	}

	public java.util.HashMap getMngtypeMap() {
		return mngtypeMap;
	}

	public void setMngtypeMap(java.util.HashMap mngtypeMap) {
		this.mngtypeMap = mngtypeMap;
	}

}