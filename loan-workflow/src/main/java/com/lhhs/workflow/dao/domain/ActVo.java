package com.lhhs.workflow.dao.domain;

import java.util.Map;

import com.google.common.collect.Maps;
import com.lhhs.workflow.common.utils.StringUtils;

/**
 * 启动流程参数
 * 
 * @author dongf
 *
 */
public class ActVo  extends BaseVO {

	private static final long serialVersionUID = 2466520138339941212L;
	/** 1、流程启动和任务处理都需要的参数 **/
	/** 启动流程的人员ID登录名 必输 **/
	private String userId;
	/** 系统标识 必输 **/
	private String sys;
	/** 渠道标识 必输 **/
	private String channel;
	/** 会签人员集合，多个以“,”分割， 选输 **/
	private String assignees;
	/** 流程变量 可以为空 **/
	Map<String, Object> vars=Maps.newHashMap();
	/** 处理IP 必输 **/
	private String ip;

	/** 2、流程启动需要的参数 **/
	/** 流程定义KEY 必输 **/
	private String procDefKey;
	/** 业务编号 必输 **/
	private String businessId;
	/** 启动任务标题 必输 **/
	private String title;
	/** 业务表 可以为空 **/
	private String businessTable;
	
	/** 3、任务处理需要的参数 **/
	/** 通过类型 必输 **/
	String pass;
	/** 任务编号 必输 **/
	private String taskId;
	/** 流程实例ID  **/
	private String procInsId;
	/** 任务提交意见的内容 **/
	private String comment;

	private String taskName; // 任务名称
	private String taskDefKey; // 任务定义Key（任务环节标识）
	/**流程定义ID*/
	private String procDefId;
	
	private String flag; // 意见状态
	private String status; // 任务状态（待处理：todo/待签收：claim/已完成：finish）
	private String field1;

    private String field2;

    private String field3;

    private String field4;//流程启动时的备注
    /** 启动流程的人员ID姓名 **/
	private String userName;
	/** 流程启动构造方法 **/
	public ActVo(String procDefKey, String businessId, String userId, String title, Map<String, Object> vars, String sys, String channel, String businessTable) {
		this.procDefKey = procDefKey;
		this.businessId = businessId;
		this.userId = userId;
		this.title = title;
		this.vars = vars;
		this.sys = sys;
		this.channel = channel;
		this.businessTable = businessTable;
	}

	/** 任务处理构造方法 **/

	public ActVo(String taskId, String procInsId, String userId, String comment, String pass, Map<String, Object> vars, String channel) {
		this.taskId = taskId;
		this.procInsId = procInsId;
		this.userId = userId;
		this.comment = comment;
		this.vars = vars;
		this.pass = pass;
		this.channel = channel;
	}

	public ActVo() {

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSys() {
		return sys;
	}

	public void setSys(String sys) {
		this.sys = sys;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Map<String, Object> getVars() {
		return vars;
	}

	public void setVars(Map<String, Object> vars) {
		this.vars = vars;
	}

	public String getProcDefKey() {
		return procDefKey;
	}

	public void setProcDefKey(String procDefKey) {
		this.procDefKey = procDefKey;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBusinessTable() {
		return businessTable;
	}

	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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



	public String getAssignees() {
		return assignees;
	}

	public void setAssignees(String assignees) {
		this.assignees = assignees;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	/**
	 * 是否是已完成任务
	 * 
	 * @return
	 */
	public boolean isFinishTask() {
		return "finish".equals(status) || StringUtils.isBlank(taskId);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
