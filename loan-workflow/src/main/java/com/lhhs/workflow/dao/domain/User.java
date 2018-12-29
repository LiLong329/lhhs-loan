package com.lhhs.workflow.dao.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 用户Entity
 * @author dongfei
 * @version 2016-09-05
 */
public class User implements Serializable ,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5275309842077768352L;

    
	private String userId;//用户ID
	private String userName;// 登录名
	private String password;// 密码
	private String companyId;	// 归属公司
	private String departmentId;	// 归属部门
	private String departmentParent;	// 上级部门
	private String unionId;//归属集团
	private String no;		// 工号
	private String name;	// 姓名
	private String sex;	// 姓名
	private String email;	// Email
	private String phone;	// 手機
	private Role role ;
	private String status;
	
	//任务相关的 获取当前任务或者流程实例的办理人使用
	private String taskId; // 任务编号
	private String taskName; // 任务名称
	private String taskDefKey; // 任务定义Key（任务环节标识）
	private String procDefKey; 	// 流程定义Key（流程定义标识）
	private String procInsId;//流程实例ID
	private String groupId;//任务办理组ID
	private String groupName;//任务办理组名称
	private String  userType;//用户类型 办理人和办理组"user/group"

    private String des;
    private String roleId;


    private String createUser;

    private Date createTime;

    private String lastUser;

    private Date lastModifyTime;
    private String field1;

    private String field2;

    private String field3;

    private String field4;

    private String field5;
    //岗位是否限制部门
    private String isDepFlag;
    //部门列表
    private List<String> depList;
    //系统标识
    private String serverId;
    
	public User() {
	}
	public User(String userId){
		this.userId=userId;
	}
	public User(String userId, String userName){
		this.userId=userId;
		this.userName = userName;
	}

	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getProcDefKey() {
		return procDefKey;
	}
	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}
	public String getProcInsId() {
		return procInsId;
	}
	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}
	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
	public String getField3() {
		return field3;
	}
	public void setField3(String field3) {
		this.field3 = field3;
	}
	public String getField4() {
		return field4;
	}
	public void setField4(String field4) {
		this.field4 = field4;
	}
	public String getField5() {
		return field5;
	}
	public void setField5(String field5) {
		this.field5 = field5;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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
		this.lastUser = lastUser;
	}
	public Date getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getIsDepFlag() {
		return isDepFlag;
	}
	public void setIsDepFlag(String isDepFlag) {
		this.isDepFlag = isDepFlag;
	}
	public String getDepartmentParent() {
		return departmentParent;
	}
	public void setDepartmentParent(String departmentParent) {
		this.departmentParent = departmentParent;
	}
	public List<String> getDepList() {
		return depList;
	}
	public void setDepList(List<String> depList) {
		this.depList = depList;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

}